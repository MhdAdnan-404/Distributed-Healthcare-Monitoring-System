package com.mhd.DataCollection.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhd.DataCollection.Domain.model.ConnectionInformation;
import com.mhd.DataCollection.Domain.model.Enums.VitalType;
import com.mhd.DataCollection.Domain.model.Measurment.ComplexVitalMeasurment;
import com.mhd.DataCollection.Domain.model.Measurment.IVitalValue;
import com.mhd.DataCollection.Domain.model.Measurment.SimpleVitalMeasurment;
import com.mhd.DataCollection.Domain.model.Measurment.VitalSignMeasurment;
import com.mhd.DataCollection.exception.FailedToGetReadingException;
import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

public class MqttAdapter implements IAdapterProtocol {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private volatile VitalSignMeasurment buffer = null;

    @Override
    public VitalSignMeasurment getVitalSign(ConnectionInformation connectionInformation) {
        try {
            MqttClient client = new MqttClient(connectionInformation.getConnectionUrl(), MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);

            client.connect(options);

            client.subscribe(connectionInformation.getTopic(), (topic, message) -> {
                try {
                    String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                    System.out.println("MQTT PAYLOAD: " + payload);

                    JsonNode root = objectMapper.readTree(payload);
                    VitalType vitalType = VitalType.valueOf(root.get("vitalType").asText());
                    Instant timestamp = Instant.parse(root.get("timeStamp").asText());
                    JsonNode valueNode = root.get("value");
                    String unit = root.has("unit") ? root.get("unit").asText() : null;

                    IVitalValue value;
                    switch (vitalType) {
                        case BP:
                            value = buildComplex(valueNode);
                            break;
                        default:
                            value = buildSimple(vitalType, valueNode, unit);
                            break;
                    }

                    buffer = VitalSignMeasurment.builder()
                            .vitalType(vitalType)
                            .value(value)
                            .timeStamp(timestamp)
                            .build();

                } catch (Exception ex) {
                    throw new FailedToGetReadingException("Failed to parse MQTT payload: " + ex.getMessage());
                }
            });
            Thread.sleep(2000);
            client.disconnect();
            client.close();

        } catch (Exception e) {
            throw new FailedToGetReadingException("Failed to get reading from MQTT: " + e.getMessage());
        }

        if (buffer == null) {
            throw new FailedToGetReadingException("No MQTT message received.");
        }

        return buffer;
    }

    private IVitalValue buildSimple(VitalType type, JsonNode valueNode, String unit) {
        return SimpleVitalMeasurment.builder()
                .name(type.name().toLowerCase())
                .value(objectMapper.convertValue(valueNode, Object.class))
                .unite(unit)
                .build();
    }

    private IVitalValue buildComplex(JsonNode valueNode) {
        Map<String, Object> map = objectMapper.convertValue(valueNode, Map.class);
        ComplexVitalMeasurment complex = new ComplexVitalMeasurment();

        complex.addPart(SimpleVitalMeasurment.builder()
                .name("systolic")
                .value(map.get("systolic"))
                .unite("mmHg")
                .build());

        complex.addPart(SimpleVitalMeasurment.builder()
                .name("diastolic")
                .value(map.get("diastolic"))
                .unite("mmHg")
                .build());

        return complex;
    }
}
