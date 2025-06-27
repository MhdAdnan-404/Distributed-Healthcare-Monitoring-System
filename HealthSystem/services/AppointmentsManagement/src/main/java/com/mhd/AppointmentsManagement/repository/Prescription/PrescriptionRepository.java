package com.mhd.AppointmentsManagement.repository.Prescription;

import com.mhd.AppointmentsManagement.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
