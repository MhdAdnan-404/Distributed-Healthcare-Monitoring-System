package com.mhd.accountManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Patient {

    @Id
    private Integer id;

    private String name;

    private LocalDate dateOfBirth;

    private String allergies;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", unique = true, nullable = false)
    private Users user;
}
