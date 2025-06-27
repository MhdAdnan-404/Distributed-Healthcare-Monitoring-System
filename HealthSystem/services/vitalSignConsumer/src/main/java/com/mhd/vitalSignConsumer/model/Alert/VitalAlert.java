package com.mhd.vitalSignConsumer.model.Alert;

import lombok.Builder;

@Builder
public record VitalAlert(
        String deviceId,
        Integer patientId,
        String patientName,
        String vitalKey,
        Double vitalValue,
        String limitBreach
) {
}
