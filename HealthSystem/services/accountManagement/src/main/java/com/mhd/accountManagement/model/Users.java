package com.mhd.accountManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mhd.accountManagement.model.Enums.PrefferedLanguage;
import com.mhd.accountManagement.model.Enums.Role;
import jakarta.persistence.*;

import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isDeleted=false;

    private Boolean isActivated=false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private Patient patient;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ContactInfo contactInfo;

    @Enumerated(EnumType.STRING)
    private PrefferedLanguage language;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ActivationToken activationToken;

    public Users(Integer id, String username, String password, Role role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.isActivated=false;
        this.isDeleted=false;
        this.role =role;
    }

    public Users(){}


}
