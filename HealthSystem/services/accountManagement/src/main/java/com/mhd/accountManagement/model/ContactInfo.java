package com.mhd.accountManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Enums.DoctorSpecialty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ContactInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    
    @ElementCollection
    @CollectionTable(
            name = "contact_info_contacts",
            joinColumns = @JoinColumn(name = "contact_info_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "contact_type")
    @Column(name = "contact_value")
    private Map<ContactType, String> contacts = new HashMap<>();

    @OneToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonBackReference
    private Users user;

    @Builder
    public ContactInfo(Map<ContactType, String> contacts){
        this.contacts = contacts;

    }


}
