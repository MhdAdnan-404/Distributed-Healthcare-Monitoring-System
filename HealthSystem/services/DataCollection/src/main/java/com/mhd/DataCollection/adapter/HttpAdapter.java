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
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

public class HttpAdapter implements IAdapterProtocol {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public VitalSignMeasurment getVitalSign(ConnectionInformation connectionInformation) {
        try {
            String json = restTemplate.getForObject(connectionInformation.getConnectionUrl(), String.class);
            System.out.println("HTTP ADAPTER RECEIVED JSON: " + json);

            JsonNode root = objectMapper.readTree(json);
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

            VitalSignMeasurment result = VitalSignMeasurment.builder()
                    .vitalType(vitalType)
                    .value(value)
                    .timeStamp(timestamp)
                    .build();

            return result;

        } catch (Exception e) {
            throw new FailedToGetReadingException("Failed to get reading from the device: " + e.getMessage());
        }
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
