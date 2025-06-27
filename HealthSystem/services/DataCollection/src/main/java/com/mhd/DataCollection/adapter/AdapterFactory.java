package com.mhd.DataCollection.adapter;

import com.mhd.DataCollection.Domain.model.Enums.DeviceProtocol;
import com.mhd.DataCollection.exception.ProtocolTypeNotAvailableException;
import org.springframework.stereotype.Component;

@Component
public class AdapterFactory {

    public IAdapterProtocol configureAdapter(DeviceProtocol protocol){
        switch (protocol){
            case HTTP:
                return new HttpAdapter();
            case MQTT:
                return new MqttAdapter();
            case COAP:
                return new CoapAdapter();
            default:
                throw new ProtocolTypeNotAvailableException("This type of protocl is not supported: "+ protocol.name());
        }
    }
}
