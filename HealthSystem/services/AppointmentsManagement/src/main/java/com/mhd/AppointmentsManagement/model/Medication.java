package com.mhd.AppointmentsManagement.model;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medication {

    private String name;
    private String dosage;
    private String notes;
}
