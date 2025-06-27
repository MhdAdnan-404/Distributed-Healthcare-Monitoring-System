package com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@JsonTypeName("complex")
public class ComplexVitalMeasurment implements IVitalValue {

    private List<IVitalValue> parts = new ArrayList<>();

    public void addPart(IVitalValue value) {
        parts.add(value);
    }

    public List<IVitalValue> getParts() {
        return parts;
    }

    public void setParts(List<IVitalValue> parts) {
        this.parts = parts;
    }

    @Override
    public Object getValue() {
        return parts;
    }
}
