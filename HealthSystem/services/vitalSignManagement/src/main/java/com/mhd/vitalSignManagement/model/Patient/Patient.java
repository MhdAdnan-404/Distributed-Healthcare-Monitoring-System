package com.mhd.vitalSignManagement.model.Patient;


import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.DeviceRef;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection  = "patients")
public class Patient {
    @Id
    private Integer id;

    private String name;

    private List<DeviceRef> devicesId = new ArrayList<>();

}
