package com.mhd.vitalSignManagement.model;



import com.mhd.vitalSignManagement.model.vitalSignMeasurment.IVitalValue;
import com.mhd.vitalSignManagement.model.vitalSignMeasurment.VitalType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Document("vital_signs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalSignDocument {
    @Id
    private String id;
    private String deviceUniqueIdentifier;
    private VitalType vitalType;
    private IVitalValue value;
    private Instant timeStamp;

}
