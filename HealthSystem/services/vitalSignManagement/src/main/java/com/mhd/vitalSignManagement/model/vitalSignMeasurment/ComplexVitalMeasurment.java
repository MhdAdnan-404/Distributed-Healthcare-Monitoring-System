package com.mhd.vitalSignManagement.model.vitalSignMeasurment;

import com.fasterxml.jackson.annotation.JsonTypeName;

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
