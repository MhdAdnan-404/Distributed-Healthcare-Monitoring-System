package com.mhd.AppointmentsManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer patientId;

    private Integer doctorId;

    private LocalDateTime issuedDate;

    private Boolean filled;

    private String filledBy_PharmacistName;

    private Integer pharmacyId;

    private LocalDateTime filledAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "prescription_medications",
            joinColumns = @JoinColumn(name = "prescription_id")
    )
    private List<Medication> medicationList;



}
