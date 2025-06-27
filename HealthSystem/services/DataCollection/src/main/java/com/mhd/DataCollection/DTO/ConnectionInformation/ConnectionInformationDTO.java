package com.mhd.DataCollection.DTO.ConnectionInformation;

import com.mhd.DataCollection.Domain.model.Enums.DeviceProtocol;
import jakarta.validation.constraints.NotNull;

public record ConnectionInformationDTO(
        @NotNull(message = "device Protocol Can't be null")
        DeviceProtocol deviceProtocol,
        @NotNull(message = "Connection Url Can't be null")
        String connectionUrl,
        String topic

) {
}
