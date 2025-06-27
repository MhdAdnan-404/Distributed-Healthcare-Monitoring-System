package com.mhd.accountManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zaxxer.hikari.util.ClockSource;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class ActivationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonBackReference
    private Users user;

    private Boolean used = false;
}
