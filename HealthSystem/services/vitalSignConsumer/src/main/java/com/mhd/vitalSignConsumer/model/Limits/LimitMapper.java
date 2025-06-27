package com.mhd.vitalSignConsumer.model.Limits;

import com.mhd.vitalSignConsumer.model.DTO.DeviceRefRaw;
import com.mhd.vitalSignConsumer.model.DeviceRef;
import com.mhd.vitalSignConsumer.service.DeviceLimitFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LimitMapper {

    @Autowired
    DeviceLimitFactory deviceLimitFactory;

    public DeviceRef transformToFlyweight(DeviceRefRaw raw){
        List<LimitEntry> sharedEntires = raw.getDeviceLimits().entrySet().stream()
                .map(entry -> {
                    String name = entry.getKey();
                    Double value = entry.getValue();
                    return deviceLimitFactory.getOrCreate(name, value);
                })
                .toList();

        return DeviceRef
                .builder()
                .uniqueIdentifier(raw.getUniqueIdentifier())
                .limits(sharedEntires)
                .build();
    }
}
