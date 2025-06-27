package com.mhd.vitalSignConsumer.service;

import com.mhd.vitalSignConsumer.model.VitalSign.VitalSignDocument;
import com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment.VitalSignMeasurment;
import com.mhd.vitalSignConsumer.repository.VitalSignReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VitalSignConsumerService {
    @Autowired
    private VitalSignReporsitory repo;

    @Autowired
    private LimitsService limitsService;

    public void saveVitalSignDoc(VitalSignMeasurment vitalSignMeasurment){
        VitalSignDocument doc = VitalSignDocument.builder()
                .deviceUniqueIdentifier(vitalSignMeasurment.deviceUniqueIdentifier())
                .vitalType(vitalSignMeasurment.vitalType())
                .timeStamp(vitalSignMeasurment.timeStamp())
                .value(vitalSignMeasurment.value())
                .build();

        repo.save(doc);
    }

}
