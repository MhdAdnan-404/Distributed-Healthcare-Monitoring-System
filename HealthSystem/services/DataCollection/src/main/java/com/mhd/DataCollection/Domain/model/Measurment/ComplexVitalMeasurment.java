package com.mhd.DataCollection.Domain.model.Measurment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonTypeName("complex")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexVitalMeasurment implements IVitalValue {
    private List<IVitalValue> parts = new ArrayList<>();

    public void addPart(IVitalValue value) {
        parts.add(value);
    }
    public List<IVitalValue> getParts() {
        return parts;
    }


    @Override
    @JsonIgnore
    public Object getValue() {
        return parts;
    }
}
