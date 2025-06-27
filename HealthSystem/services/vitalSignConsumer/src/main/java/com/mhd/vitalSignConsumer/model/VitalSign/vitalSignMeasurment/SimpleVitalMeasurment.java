package com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment;


import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("simple")
public class SimpleVitalMeasurment implements IVitalValue {
    private String type;
    private String name;
    private Object value;
    private String unite;

    @Override
    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
    public String getUnit() {
        return unite;
    }
}
