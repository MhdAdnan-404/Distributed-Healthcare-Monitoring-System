package com.mhd.vitalSignManagement.Clients.DataCollection.DTO;

import org.springframework.stereotype.Service;

@Service
public class DeviceMapper {
    public DeviceRef toDeviceRef(AddDeviceToPatientRequest request){
        return DeviceRef
                .builder()
                .uniqueIdentifier(request.uniqueIdentifier())
                .deviceLimits(request.deviceLimit())
                .build();
    }

    public AddDeviceRequest toAddDeviceRequest(AddDeviceToPatientRequest request){
        return AddDeviceRequest
                .builder()
                .uniqueIdentifier(request.uniqueIdentifier())
                .deviceType(request.deviceType())
                .connectionInformationDTO(request.connectionInformationDTO())
                .build();
    }
}
