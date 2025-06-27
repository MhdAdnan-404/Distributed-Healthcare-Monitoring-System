package com.mhd.vitalSignConsumer.model.Alert;

import org.springframework.stereotype.Service;

@Service
public class VitalAlertMapper {


    public VitalAlert toVitalAlert(String deviceId, Integer patientId, String patientName, String vitalKey, Double vitalValue, String limitBreach){
        return VitalAlert
                .builder()
                .deviceId(deviceId)
                .patientId(patientId)
                .patientName(patientName)
                .vitalValue(vitalValue)
                .vitalKey(vitalKey)
                .limitBreach(limitBreach)
                .build();
    }

}
