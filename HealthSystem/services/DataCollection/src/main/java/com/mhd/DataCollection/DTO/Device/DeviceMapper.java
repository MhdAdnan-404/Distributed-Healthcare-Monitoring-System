package com.mhd.DataCollection.DTO.Device;

import com.mhd.DataCollection.DTO.ConnectionInformation.ConnectionInformationMapper;
import com.mhd.DataCollection.Domain.model.ConnectionInformation;
import com.mhd.DataCollection.Domain.model.Device;
import com.mhd.DataCollection.Domain.model.Enums.DeviceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceMapper {

    @Autowired
    private ConnectionInformationMapper connectionInformationMapper;

    public Device toDevice(AddDeviceRequest request){
        ConnectionInformation connectionInformation = connectionInformationMapper.toConnectionInformation(request.connectionInformationDTO());
        Device device = Device.builder()
                .uniqueIdentifier(request.uniqueIdentifier())
                .type(request.deviceType())
                .deviceStatus(DeviceStatus.OFFLINE)
                .connectionInformation(connectionInformation)
                .build();
        connectionInformation.setDevice(device);

        return device;
    }
}
