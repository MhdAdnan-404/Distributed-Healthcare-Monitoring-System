package com.mhd.DataCollection.DTO.ConnectionInformation;

import com.mhd.DataCollection.Domain.model.ConnectionInformation;
import org.springframework.stereotype.Service;

@Service
public class ConnectionInformationMapper {


    public ConnectionInformation toConnectionInformation(ConnectionInformationDTO connectionInformationDTO) {
        return ConnectionInformation.builder()
                .protocol(connectionInformationDTO.deviceProtocol())
                .connectionUrl(connectionInformationDTO.connectionUrl())
                .topic(connectionInformationDTO.topic())
                .build();
    }
}
