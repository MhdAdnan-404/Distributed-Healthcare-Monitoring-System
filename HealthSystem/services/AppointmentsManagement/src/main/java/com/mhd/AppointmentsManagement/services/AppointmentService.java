package com.mhd.AppointmentsManagement.services;

import com.mhd.AppointmentsManagement.client.CallManagementClient;
import com.mhd.AppointmentsManagement.exceptions.AppointmentConflictException;
import com.mhd.AppointmentsManagement.exceptions.AppointmentNotFoundException;
import com.mhd.AppointmentsManagement.exceptions.PrescriptionWasNotFoundException;
import com.mhd.AppointmentsManagement.kafka.KafkaProducer;
import com.mhd.AppointmentsManagement.model.Appointment;
import com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS.*;
import com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS.CreatePrescriptionRequest;
import com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS.PrescriptionFulFillmentDTO;
import com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS.PrescriptionMapper;
import com.mhd.AppointmentsManagement.model.DTO.PrescriptionKafkaDTO;
import com.mhd.AppointmentsManagement.model.Enums.AppointmentStatues;
import com.mhd.AppointmentsManagement.model.Prescription;
import com.mhd.AppointmentsManagement.repository.Appointment.AppointmentRepository;
import com.mhd.AppointmentsManagement.repository.Prescription.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
public class AppointmentService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private CallManagementClient client;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Page<Appointment> getAll(Pageable pageable){
        return appointmentRepository.findAll(pageable);
    }

    public Page<Appointment> getAppointmentsForPatient(Pageable pageable, Integer id) {
        return appointmentRepository.findAllByPatientId(id, pageable);
    }

    public Page<Appointment> getAppointmentsForDoctor(Pageable pageable, Integer id) {
        return appointmentRepository.findAllByDoctorId(id, pageable);
    }

    public Boolean createAppointment(CreateAppointmentRequest createAppointmentRequest) {
        LocalDateTime startTime = createAppointmentRequest.dateAndTime();
        LocalDateTime endTime = createAppointmentRequest.dateAndTime().plusMinutes(createAppointmentRequest.approxDurationInMinutes());

        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(createAppointmentRequest.doctorId(),startTime,endTime,null);
        if(!conflicts.isEmpty()){
            throw new AppointmentConflictException("there is an appointment Conflict", conflicts);
        }

        Appointment appointment = appointmentMapper.toAppointment(createAppointmentRequest);

        appointmentRepository.save(appointment);
        kafkaProducer.sendAppointmentCreationConfirmationToKafka(createAppointmentRequest);
        return true;

    }

    public Boolean deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("This Appointment Dosen't Exist"));

        appointment.setIsDeleted(true);
        appointmentRepository.save(appointment);
        return true;
    }

    public Boolean updateAppointmentStatus(updateAppointmentStatusRequest request) {
        Appointment appointment = appointmentRepository.findById(request.Id())
                .orElseThrow(() -> new AppointmentNotFoundException("This Appointment Dosen't Exist"));

        appointment.setAppointmentStatues(request.newStatus());
        appointmentRepository.save(appointment);
        if(request.newStatus() == AppointmentStatues.CANCELED){

            AppointmentCancellation appointmentCancellation = appointmentMapper.toAppointmentCancelation(appointment);

            kafkaProducer.sendAppointmentCancellationToKafka(appointmentCancellation);
        }
        return true;
    }

    public void sendAppointmentReminder(Appointment appointment) {

        String patientToken = getToken(appointment.getId(), appointment.getPatientName());

        String DoctorToken = getToken(appointment.getId(), appointment.getDoctorName());

        appointment.setReminderSent(true);
        appointment.setDoctorToken(DoctorToken);
        appointment.setPatientToken(patientToken);

        AppointmentReminder appointmentReminder = appointmentMapper.toAppointmentReminder(appointment);

        kafkaProducer.sendAppointmentReminderToKafka(appointmentReminder);

        appointmentRepository.save(appointment);
    }

    public String getToken(Long appointmentId, String participantName){

        Map<String, String> participantParams = Map.of(
                "roomName", appointmentId.toString(),
                "participantName", participantName
        );

        return client.createCallToken(participantParams).get("token");
    }


    public Boolean rescheduleAppointment(RescheduleAppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("This appointment is not found"));

        if (appointment.getIsDeleted() ==true){
            throw new AppointmentNotFoundException("This appointment is not found");
        }

        LocalDateTime newStart = request.newDateAndTime();
        Long newDuration = request.durationInMinutes();
        LocalDateTime newEnd = request.newDateAndTime().plusMinutes(newDuration);

        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(appointment.getDoctorId(), newStart, newEnd, appointment.getId());
        if (!conflicts.isEmpty()) {
            throw new AppointmentConflictException("The new appointment time conflicts with another one.", conflicts);
        }
        appointment.setStartTime(newStart);
        appointment.setDurationInMinutes(newDuration);
        appointment.setApproximatedEndTime(newEnd);
        appointmentRepository.save(appointment);

        AppointmentReminder appointmentReminder = appointmentMapper.toAppointmentReminder(appointment);

        kafkaProducer.sendAppointmentRescheduleToKafka(appointmentReminder);
        return true;
    }

    public Boolean addPrescriptionToAppointment(Long id, CreatePrescriptionRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("This appointment is not found"));

        Prescription prescription = prescriptionMapper.toPrescription(request);

        appointment.setPrescription(prescription);

        appointmentRepository.save(appointment);

        PrescriptionKafkaDTO prescriptionKafkaDTO = prescriptionMapper.toPrescriptionKafkaDTO(prescription);

        kafkaProducer.sendPrescriptionToKafka(prescriptionKafkaDTO);

        return true;
    }

    public void FulfillPrescription(PrescriptionFulFillmentDTO prescriptionFulfillment) {
       Prescription prescription = prescriptionRepository.findById(prescriptionFulfillment.prescriptionId())
               .orElseThrow(() -> new PrescriptionWasNotFoundException("This appointment is not found"));

       prescription.setFilledBy_PharmacistName(prescriptionFulfillment.filledByPharmacistName());
       prescription.setPatientId(prescriptionFulfillment.pharmacyId());
       prescription.setFilledAt(prescription.getFilledAt());
       prescription.setFilled(true);

       prescriptionRepository.save(prescription);
    }
}
