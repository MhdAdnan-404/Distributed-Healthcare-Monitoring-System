package com.mhd.vitalSignConsumer.model.Limits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimitEntry {
    private String name;
    private Double value;

}
