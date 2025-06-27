package com.mhd.vitalSignManagement.Clients.DataCollection.DTO;

import jakarta.validation.constraints.NotNull;

public record ConnectionInformationDTO(
        @NotNull(message = "device Protocol Can't be null")
        DeviceProtocol deviceProtocol,
        @NotNull(message = "Connection Url Can't be null")
        String connectionUrl,
        String topic
) {

}
