package com.mhd.AppointmentsManagement.services;

import com.mhd.AppointmentsManagement.model.Appointment;
import com.mhd.AppointmentsManagement.repository.Appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AppointmentRepository appointmentRepository;
    //add the cache to not call the database continously
    @Scheduled(fixedRate = 10000)
    public void sendUpcomingAppointmentReminder(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowEnd = now.plusMinutes(30);


        List<Appointment> upcomingAppointmnet = appointmentRepository.findByStartTimeBetweenAndReminderSentFalse(now,windowEnd);
        for(Appointment appointment: upcomingAppointmnet){
            appointmentService.sendAppointmentReminder(appointment);
        }
    }



}
