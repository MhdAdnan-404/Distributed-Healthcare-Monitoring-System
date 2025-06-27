package com.mhd.vitalSignManagement.Clients.DataCollection.DTO;

import com.mhd.vitalSignManagement.model.DeviceLimitTypes;
import lombok.Builder;

import java.util.Map;

@Builder
public record DeviceRef(
        String uniqueIdentifier,
        Map<DeviceLimitTypes, Double> deviceLimits
)
{}
