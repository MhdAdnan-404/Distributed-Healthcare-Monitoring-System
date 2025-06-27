package com.mhd.vitalSignManagement.model.vitalSignMeasurment;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleVitalMeasurment.class, name = "simple"),
        @JsonSubTypes.Type(value = ComplexVitalMeasurment.class, name = "complex")
})
public interface IVitalValue {
    Object getValue();
}
