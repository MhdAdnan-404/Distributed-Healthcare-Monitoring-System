package com.mhd.DataCollection.Domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mhd.DataCollection.Domain.model.Enums.DeviceStatus;
import com.mhd.DataCollection.Domain.model.Enums.DeviceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uniqueIdentifier;

    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VitalSign> vitalSigns;

    @OneToOne(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private ConnectionInformation connectionInformation;


}
