package com.mhd.DataCollection.Domain.model.Measurment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleVitalMeasurment implements IVitalValue {
    private String name;
    private Object value;
    private String unite;

    @Override
    public Object getValue() {
        return value;
    }
}
