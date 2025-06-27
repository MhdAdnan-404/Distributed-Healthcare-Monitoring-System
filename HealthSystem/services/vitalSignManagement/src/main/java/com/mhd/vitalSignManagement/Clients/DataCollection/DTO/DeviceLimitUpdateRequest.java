package com.mhd.vitalSignManagement.Clients.DataCollection.DTO;

import com.mhd.vitalSignManagement.model.DeviceLimitTypes;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record DeviceLimitUpdateRequest(
        @NotNull(message="device Id can't be null")
        String deviceUniqueIdentifier,
        @NotNull(message = "Device limtis can't be null")
        Map<DeviceLimitTypes, Double> deviceLimits
) {
}
