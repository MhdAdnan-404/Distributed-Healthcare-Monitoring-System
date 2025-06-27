package com.example.Pharmacy.Domain;


import com.example.Pharmacy.Domain.DTO.Medication;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    private Long prescriptionId;

    private Integer patientId;
    private Integer doctorId;

    private LocalDateTime issuedDate;
    private Boolean filled;

    private String filledByPharmacistName;
    private Integer pharmacyId;
    private LocalDateTime filledAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "prescription_medications",
            joinColumns = @JoinColumn(name = "prescription_id")
    )
    private List<Medication> medicationList;
}
