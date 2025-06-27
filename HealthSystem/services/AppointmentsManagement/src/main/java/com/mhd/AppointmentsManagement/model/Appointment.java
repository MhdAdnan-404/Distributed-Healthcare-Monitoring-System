package com.mhd.AppointmentsManagement.model;

import com.mhd.AppointmentsManagement.model.Enums.AppointmentStatues;
import com.mhd.AppointmentsManagement.model.Enums.AppointmentType;
import com.mhd.AppointmentsManagement.model.Enums.ContactType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer doctorId;

    @Column(columnDefinition = "TEXT")
    private String doctorToken;

    private String doctorName;

    private String patientName;

    @Column(nullable = false)
    private Integer patientId;

    @Column(columnDefinition = "TEXT")
    private String patientToken;

    private LocalDateTime startTime;

    private Long durationInMinutes;

    private Integer actualDurationInMinutes;

    private LocalDateTime approximatedEndTime;

    private LocalDateTime actualEndTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatues appointmentStatues;

    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    private Prescription prescription;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Boolean isDeleted;

    private Boolean reminderSent;
}
