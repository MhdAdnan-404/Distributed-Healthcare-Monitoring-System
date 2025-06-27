package com.mhd.DataCollection.Domain.model.Measurment;

import com.mhd.DataCollection.Domain.model.Enums.VitalType;
import com.mhd.DataCollection.Domain.model.VitalSign;
import lombok.*;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalSignMeasurment {

    private VitalType vitalType;
    private IVitalValue value;
    private Instant timeStamp;
    private String deviceUniqueIdentifier;
}
