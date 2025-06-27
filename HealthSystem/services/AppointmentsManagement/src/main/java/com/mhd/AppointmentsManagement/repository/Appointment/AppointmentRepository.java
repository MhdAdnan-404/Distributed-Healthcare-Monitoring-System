package com.mhd.AppointmentsManagement.repository.Appointment;

import com.mhd.AppointmentsManagement.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentCustomRepository {

    Page<Appointment> findAllByPatientId(Integer patientId, Pageable pageable);

    Page<Appointment> findAllByDoctorId(Integer id, Pageable pageable);

    List<Appointment> findByStartTimeBetweenAndReminderSentFalse(LocalDateTime from, LocalDateTime to);



}
