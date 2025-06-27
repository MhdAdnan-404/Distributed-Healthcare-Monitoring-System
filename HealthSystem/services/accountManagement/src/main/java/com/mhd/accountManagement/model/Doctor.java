package com.mhd.accountManagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mhd.accountManagement.model.Enums.ApprovalStatues;
import com.mhd.accountManagement.model.Enums.DoctorSpecialty;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Doctor {

    @Id
    private Integer id;

    private String name;

    @Column(name = "speciality")
    @Enumerated(EnumType.STRING)
    private DoctorSpecialty speciality;

    @Column(name = "approval_status")
    @Enumerated(EnumType.STRING)
    private ApprovalStatues approvalStatues;

    @OneToOne
    @MapsId
    @JoinColumn(name="id", unique = true, nullable = false)
    @JsonManagedReference
    private Users user;

    @Builder
    public Doctor(String name, DoctorSpecialty specialty, Users user){
        this.name=name;
        this.speciality = specialty;
        this.user = user;

        user.setDoctor(this);
    }

}
