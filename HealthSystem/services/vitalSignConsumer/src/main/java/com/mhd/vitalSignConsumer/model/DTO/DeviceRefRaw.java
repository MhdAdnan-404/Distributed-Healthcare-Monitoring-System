package com.mhd.vitalSignConsumer.model.DTO;


import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRefRaw {
    private String uniqueIdentifier;
    private Map<String, Double> deviceLimits;
}
