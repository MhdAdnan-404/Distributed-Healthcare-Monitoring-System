package com.mhd.vitalSignConsumer.model.Patient;

import com.mhd.vitalSignConsumer.model.DTO.PatientRaw;
import com.mhd.vitalSignConsumer.model.DeviceRef;
import com.mhd.vitalSignConsumer.model.Limits.LimitMapper;
import com.mhd.vitalSignConsumer.service.DeviceLimitFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientMapper {

    @Autowired
    private LimitMapper limitMapper;

    public Patient transform(PatientRaw raw){
        List<DeviceRef> devices = raw.getDevicesId().stream()
                .map(limitMapper::transformToFlyweight)
                .toList();

        return Patient.builder()
                .id(raw.getId())
                .name(raw.getName())
                .devicesId(devices)
                .build();
    }
}
