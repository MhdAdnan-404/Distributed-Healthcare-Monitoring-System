package com.mhd.vitalSignManagement.Clients.DataCollection.DTO;

import com.mhd.vitalSignManagement.model.DeviceLimitTypes;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record AddDeviceToPatientRequest(
        @NotNull(message = "the uniqueIdentifier can't be null")
        String uniqueIdentifier,  //mac address or imei to make sure that the same device is not added twice by the user
        @NotNull(message = "PatientId Can't be null")
        @Min(value = 1, message = "Patient ID must be a positive number")
        Integer patientId,

        @NotNull(message ="Device Type can't be null")
        DeviceType deviceType,

        @NotNull(message = "Connection Url Can't be null")
        ConnectionInformationDTO connectionInformationDTO,

        @NotNull(message = "device Limit can't be empty")
        Map<DeviceLimitTypes, Double> deviceLimit

) {
}
