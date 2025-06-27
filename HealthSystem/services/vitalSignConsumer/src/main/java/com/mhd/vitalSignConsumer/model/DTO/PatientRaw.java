package com.mhd.vitalSignConsumer.model.DTO;


import com.mhd.vitalSignConsumer.model.DTO.DeviceRefRaw;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "patients")
public class PatientRaw {
    private Integer id;
    private String name;
    private List<DeviceRefRaw> devicesId;
}
