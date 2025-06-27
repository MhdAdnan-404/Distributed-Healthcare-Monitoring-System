package com.mhd.vitalSignManagement.model.vitalSignMeasurment;

import java.time.Instant;

public record VitalSignMeasurment(
        VitalType vitalType,
        String name,
        IVitalValue value,
        Instant timeStamp,
        String deviceUniqueIdentifier
) {
}
