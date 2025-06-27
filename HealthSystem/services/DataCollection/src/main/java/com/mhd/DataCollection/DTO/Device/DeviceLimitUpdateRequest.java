package com.mhd.DataCollection.DTO.Device;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record DeviceLimitUpdateRequest(
        @NotNull(message="device Id can't be null")
        Integer deviceId,
        @NotNull(message = "Device limtis can't be null")
        Map<String, Double> deviceLimits
) {
}
