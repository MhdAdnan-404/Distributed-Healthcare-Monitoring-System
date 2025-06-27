package com.example.notification.service;

import com.example.notification.domain.Enums.ContactType;
import com.example.notification.domain.Enums.NotificationType;
import com.example.notification.domain.Enums.PreferredLanguage;
import com.example.notification.domain.Enums.RecipientRole;
import com.example.notification.domain.MessageTemplate;
import com.example.notification.domain.notificationContent.Contents.*;
import com.example.notification.kafka.Alert.VitalAlert;
import com.example.notification.repository.NotificationMessageTemplateRepository;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.service.Template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class NotificationService {


    @Autowired
    EmailService emailService;

    @Autowired
    private UserService userService;


    @Autowired
    private TemplateService templateService;

    @Autowired
    private CoolDownService coolDownService;



    public void processVitalAlertMessage(VitalAlert vitalAlert) {
        System.out.println("PARSED VITALSIGN");
        String email = userService.getUser(vitalAlert.getPatientId()).getContacts().get(ContactType.EMAIL);
        if (coolDownService.isInCooldown(email)){
            System.out.println("Skipping email: cooldown active");
            return;
        }
        Map<String, String> values = Map.of(
                "name", vitalAlert.getPatientName(),
                "content", vitalAlert.getContent()
        );

        String body = templateService.getProcessedBody(NotificationType.VITAL_ALERT, PreferredLanguage.valueOf(userService.getUser(vitalAlert.getPatientId()).getPreferredLanguage()), RecipientRole.BOTH, values);
        String subject = templateService.getSubject(NotificationType.VITAL_ALERT, PreferredLanguage.EN, RecipientRole.BOTH);

        emailService.sendEmail(email, subject, body);

        coolDownService.addToCooldown(email);
    }

    public void processAccountVerification(AccountVerificationContent accountVerificationContent) {
        System.out.println("ACCOUNT VERIFICATION SUCCESSFUL");

        String email = accountVerificationContent.getEmail();

        Map<String,String> values = Map.of("name",accountVerificationContent.getUsername());
        String body = templateService.getProcessedBody(
                NotificationType.ACCOUNT_VERIFIED,
                PreferredLanguage.EN,
                RecipientRole.BOTH,
                values
        );
        String subject = templateService.getSubject(
                NotificationType.ACCOUNT_VERIFIED,
                PreferredLanguage.EN,
                RecipientRole.BOTH);

        emailService.sendEmail(email, subject, body);
    }

    public void processAppointmentCreation(AppointmentCreationContent appointmentCreationContent) {
        String doctorEmail = appointmentCreationContent.getDoctorContacts().get(ContactType.EMAIL);
        String patientEmail = appointmentCreationContent.getPatientContacts().get(ContactType.EMAIL);
        String formattedTime = appointmentCreationContent.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        Map<String, String> values = Map.of(
                "doctorName", appointmentCreationContent.getDoctorName(),
                "patientName", appointmentCreationContent.getPatientName(),
                "time", formattedTime,
                "type", appointmentCreationContent.getType()
        );

        if (doctorEmail != null && !doctorEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_CREATION, PreferredLanguage.valueOf(userService.getUser(appointmentCreationContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_CREATION, PreferredLanguage.valueOf(userService.getUser(appointmentCreationContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR);
            emailService.sendEmail(doctorEmail, subject, body);
        }
        if (patientEmail != null && !patientEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_CREATION, PreferredLanguage.valueOf(userService.getUser(appointmentCreationContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_CREATION,PreferredLanguage.valueOf(userService.getUser(appointmentCreationContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT);
            emailService.sendEmail(patientEmail, subject, body);
        }
    }


    public void processAppointmentCancellation(AppointmentCancelledContent appointmentCancelledContent) {
        System.out.println("APPOINTMENT Cancelled SUCCESSFULLY");

        String doctorEmail = appointmentCancelledContent.getDoctorContacts().get(ContactType.EMAIL);
        String patientEmail = appointmentCancelledContent.getPatientContacts().get(ContactType.EMAIL);

        Map<String, String> values = Map.of(
                "doctorName", appointmentCancelledContent.getDoctorName(),
                "patientName", appointmentCancelledContent.getPatientName(),
                "appointmentId", appointmentCancelledContent.getAppointmentId().toString(),
                "status", appointmentCancelledContent.getAppointmentStatues()
        );


        if (doctorEmail != null && !doctorEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.valueOf(userService.getUser(appointmentCancelledContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.valueOf(userService.getUser(appointmentCancelledContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR);
            emailService.sendEmail(doctorEmail, subject, body);
        }

        if (patientEmail != null && !patientEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.valueOf(userService.getUser(appointmentCancelledContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.valueOf(userService.getUser(appointmentCancelledContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT);
            emailService.sendEmail(patientEmail, subject, body);
        }
    }
//should call the call management service first and get the appointment token then sent the email.
    public void processAppointmentReminder(AppointmentReminderContent appointmentReminderContent) {
        String doctorEmail = appointmentReminderContent.getDoctorContacts().get(ContactType.EMAIL);
        String patientEmail = appointmentReminderContent.getPatientContacts().get(ContactType.EMAIL);
        String doctorToken =appointmentReminderContent.getDoctorToken();
        String patientToken =appointmentReminderContent.getPatientToken();
        String time = appointmentReminderContent.getAppointmentStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        Map<String, String> values = Map.of(
                "doctorName", appointmentReminderContent.getDoctorName(),
                "patientName", appointmentReminderContent.getPatientName(),
                "time", time,
                "appointmentId", appointmentReminderContent.getAppointmentId().toString(),
                "doctorToken", appointmentReminderContent.getDoctorToken(),
                "patientToken", appointmentReminderContent.getPatientToken()
        );

        if (doctorEmail != null && !doctorEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.valueOf(userService.getUser(appointmentReminderContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.valueOf(userService.getUser(appointmentReminderContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR);
            emailService.sendEmail(doctorEmail, subject, body);
        }

        if (patientEmail != null && !patientEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.valueOf(userService.getUser(appointmentReminderContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.valueOf(userService.getUser(appointmentReminderContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT);
            emailService.sendEmail(patientEmail, subject, body);
        }
    }

    public void processAppointmentReschedule(AppointmentRescheduleContent appointmentRescheduleContent) {
        String doctorEmail = appointmentRescheduleContent.getDoctorContacts().get(ContactType.EMAIL);
        String patientEmail = appointmentRescheduleContent.getPatientContacts().get(ContactType.EMAIL);
        String time = appointmentRescheduleContent.getAppointmentStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, String> values = Map.of(
                "doctorName", appointmentRescheduleContent.getDoctorName(),
                "patientName", appointmentRescheduleContent.getPatientName(),
                "time", time,
                "appointmentId", appointmentRescheduleContent.getAppointmentId().toString()
        );

        if (doctorEmail != null && !doctorEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_RESCHEDULED, PreferredLanguage.valueOf(userService.getUser(appointmentRescheduleContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_RESCHEDULED, PreferredLanguage.valueOf(userService.getUser(appointmentRescheduleContent.getDoctorId()).getPreferredLanguage()), RecipientRole.DOCTOR);
            emailService.sendEmail(doctorEmail, subject, body);
        }

        if (patientEmail != null && !patientEmail.isBlank()) {
            String body = templateService.getProcessedBody(NotificationType.APPOINTMENT_RESCHEDULED, PreferredLanguage.valueOf(userService.getUser(appointmentRescheduleContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT, values);
            String subject = templateService.getSubject(NotificationType.APPOINTMENT_RESCHEDULED, PreferredLanguage.valueOf(userService.getUser(appointmentRescheduleContent.getPatientId()).getPreferredLanguage()), RecipientRole.PATIENT);
            emailService.sendEmail(patientEmail, subject, body);
        }
    }
}
