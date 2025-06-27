package com.mhd.DataCollection.Domain.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mhd.DataCollection.Domain.model.Enums.VitalType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class VitalSign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "Device_id", nullable = false)
    private Device device;

    @Enumerated(EnumType.STRING)
    private VitalType vitalType;

    private String vitalValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant date;
}
