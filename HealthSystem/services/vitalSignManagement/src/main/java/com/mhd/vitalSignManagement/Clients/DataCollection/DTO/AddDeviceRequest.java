package com.mhd.vitalSignManagement.Clients.DataCollection.DTO;

import lombok.Builder;

@Builder
public record AddDeviceRequest(
        String uniqueIdentifier,
        DeviceType deviceType,
        ConnectionInformationDTO connectionInformationDTO
) {
}
