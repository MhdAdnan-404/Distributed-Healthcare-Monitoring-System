package com.mhd.DataCollection.DTO.Device;

import com.mhd.DataCollection.DTO.ConnectionInformation.ConnectionInformationDTO;
import com.mhd.DataCollection.Domain.model.Enums.DeviceType;
import com.mhd.DataCollection.Domain.model.VitalSign;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record AddDeviceRequest(

        @NotNull(message = "the uniqueIdentifier can't be null")
        String uniqueIdentifier,

        @NotNull(message ="Device Type can't be null")
        DeviceType deviceType,

        @NotNull(message = "Connection Url Can't be null")
        ConnectionInformationDTO connectionInformationDTO

) {
}
