package com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS;

import com.mhd.AppointmentsManagement.model.Appointment;
import com.mhd.AppointmentsManagement.model.Enums.AppointmentStatues;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppointmentMapper {

    public AppointmentCancellation toAppointmentCancelation(Appointment appointment){
        return AppointmentCancellation
                .builder()
                .appointmentId(appointment.getId())
                .appointmentStatues(AppointmentStatues.CANCELED)
                .patientId(appointment.getPatientId())
                .patientName(appointment.getPatientName())
                .doctorId(appointment.getDoctorId())
                .doctorName(appointment.getDoctorName())
                .build();

    }

    public Appointment toAppointment(CreateAppointmentRequest request) {
        LocalDateTime startTime = request.dateAndTime();
        LocalDateTime endTime = startTime.plusMinutes(request.approxDurationInMinutes());


        return Appointment.builder()
                .doctorId(request.doctorId())
                .doctorName(request.doctorName())
                .patientId(request.patientId())
                .patientName(request.patientName())
                .startTime(startTime)
                .durationInMinutes(request.approxDurationInMinutes())
                .approximatedEndTime(endTime)
                .appointmentStatues(AppointmentStatues.BOOKED)
                .appointmentType(request.type())
                .appointmentStatues(AppointmentStatues.BOOKED)
                .isDeleted(false)
                .reminderSent(false)
                .build();
    }

    public AppointmentReminder toAppointmentReminder(Appointment appointment){
        return AppointmentReminder
                .builder()
                .appointmentId(appointment.getId())
                .patientId(appointment.getPatientId())
                .patientName(appointment.getPatientName())
                .patientToken(appointment.getPatientToken())
                .doctorId(appointment.getDoctorId())
                .doctorName(appointment.getDoctorName())
                .doctorToken(appointment.getDoctorToken())
                .appointmentStartTime(appointment.getStartTime())
                .build();
    }
}
