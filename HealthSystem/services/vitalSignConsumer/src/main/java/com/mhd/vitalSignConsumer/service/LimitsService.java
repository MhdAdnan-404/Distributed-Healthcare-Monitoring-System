package com.mhd.vitalSignConsumer.service;

import com.mhd.vitalSignConsumer.kafka.kafkaAlertProducer.VitalAlertProducer;
import com.mhd.vitalSignConsumer.model.Alert.VitalAlert;
import com.mhd.vitalSignConsumer.model.Alert.VitalAlertMapper;
import com.mhd.vitalSignConsumer.model.Limits.LimitEntry;
import com.mhd.vitalSignConsumer.model.Patient.Patient;
import com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment.ComplexVitalMeasurment;
import com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment.IVitalValue;
import com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment.SimpleVitalMeasurment;
import com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment.VitalSignMeasurment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LimitsService {

    @Autowired
    DeviceLimitCacheService deviceLimitCacheService;

    @Autowired
    VitalAlertMapper mapper;

    @Autowired
    DeviceLimitFactory deviceLimitFactory;

    @Autowired
    VitalAlertProducer vitalAlertProducer;


    public void processMeasurment(VitalSignMeasurment measurment){
        IVitalValue value = measurment.value();

        if (value instanceof SimpleVitalMeasurment simpleVitalMeasurment){
            processSimple(measurment.deviceUniqueIdentifier(), simpleVitalMeasurment);
        }else if (value instanceof ComplexVitalMeasurment complexVitalMeasurment){
            for (IVitalValue part: complexVitalMeasurment.getParts()){
                if(part instanceof SimpleVitalMeasurment sub) {
                    processSimple(measurment.deviceUniqueIdentifier(), sub);
                }
            }
        }
    }


    private void processSimple(String deviceUniqueIdentifier, SimpleVitalMeasurment meas) {
        String key = meas.getName().toLowerCase();
        double value = ((Number) meas.getValue()).doubleValue();

        String minKey = "MIN_" + key.toUpperCase();
        String maxKey = "MAX_" + key.toUpperCase();

        Patient patient = deviceLimitCacheService.getPatient(deviceUniqueIdentifier);

        Map<String, Double> mergedLimits = patient.getDevicesId().stream()
                .filter(d -> d.uniqueIdentifier().equals(deviceUniqueIdentifier))
                .flatMap(d -> d.limits().stream())
                .collect(Collectors.toMap(
                        LimitEntry::getName,
                        LimitEntry::getValue,
                        (v1, v2) -> v1
                ));

        Double min = mergedLimits.get(minKey);
        Double max = mergedLimits.get(maxKey);

        System.out.printf("Device: %s, Key: %s, Value: %.2f, Min: %.2f, Max: %.2f%n",
                deviceUniqueIdentifier, key, value, min, max);

        if (value < min) {
            triggerAlert(deviceUniqueIdentifier, key, value,patient.getId(), patient.getName(), "LOW");
        } else if (value > max) {
            triggerAlert(deviceUniqueIdentifier, key, value,patient.getId(), patient.getName(),"HIGH");
        }
    }

    public void triggerAlert(String deviceUniqueIdentifier, String key, Double value,Integer patientId, String patientName, String limitBreach) {
        VitalAlert vitalAlert = mapper.toVitalAlert(deviceUniqueIdentifier,patientId,patientName,key,value,limitBreach);
        vitalAlertProducer.sendVitalAlertToKafka(vitalAlert);

    }

}
