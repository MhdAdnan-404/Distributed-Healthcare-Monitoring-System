package com.example.notification.service.Template;

import com.example.notification.domain.Enums.NotificationType;
import com.example.notification.domain.Enums.PreferredLanguage;
import com.example.notification.domain.Enums.RecipientRole;
import com.example.notification.domain.MessageTemplate;
import com.example.notification.repository.NotificationMessageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
public class TemplatesInitializer implements CommandLineRunner {

    @Autowired
    NotificationMessageTemplateRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() == 0) {
            List<MessageTemplate> templates = List.of(
                    // --- APPOINTMENT_CREATION ---
                    new MessageTemplate(NotificationType.APPOINTMENT_CREATION, PreferredLanguage.EN, RecipientRole.DOCTOR,
                            "New Appointment Scheduled",
                            "Hello Dr. {{doctorName}},\n\nYou have a new appointment with {{patientName}} on {{time}}.\nType: {{type}}"),

                    new MessageTemplate(NotificationType.APPOINTMENT_CREATION, PreferredLanguage.EN, RecipientRole.PATIENT,
                            "Your Appointment Details",
                            "Hello {{patientName}},\n\nYou have a new appointment with Dr. {{doctorName}} on {{time}}.\nType: {{type}}"),

                    new MessageTemplate(NotificationType.APPOINTMENT_CREATION, PreferredLanguage.AR, RecipientRole.DOCTOR,
                            "موعد جديد مجدول",
                            "مرحباً د. {{doctorName}}،\n\nلديك موعد جديد مع المريض {{patientName}} في {{time}}.\nالنوع: {{type}}"),

                    new MessageTemplate(NotificationType.APPOINTMENT_CREATION, PreferredLanguage.AR, RecipientRole.PATIENT,
                            "تفاصيل موعدك",
                            "مرحباً {{patientName}}،\n\nلديك موعد جديد مع الدكتور {{doctorName}} في {{time}}.\nالنوع: {{type}}"),

                    // --- APPOINTMENT_REMINDER ---
                    new MessageTemplate(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.EN, RecipientRole.DOCTOR,
                            "Appointment Reminder",
                            "Hello Dr. {{doctorName}},\n\n" +
                                    "This is a reminder for your appointment with {{patientName}} on {{time}}.\n" +
                                    "Appointment ID: {{appointmentId}}\n" +
                                    "Token: {{doctorToken}}"
                    ),

                    new MessageTemplate(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.EN, RecipientRole.PATIENT,
                            "Your Upcoming Appointment",
                            "Hello {{patientName}},\n\n" +
                                    "This is a reminder for your appointment with Dr. {{doctorName}} on {{time}}.\n" +
                                    "Appointment ID: {{appointmentId}}\n" +
                                    "Token: {{patientToken}}"
                    ),

                    new MessageTemplate(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.AR, RecipientRole.DOCTOR,
                            "تذكير بالموعد",
                            "مرحباً د. {{doctorName}}،\n\n" +
                                    "هذا تذكير بموعدك مع المريض {{patientName}} في {{time}}.\n" +
                                    "معرف الموعد: {{appointmentId}}\n" +
                                    "رابط الانضمام: Token: {{doctorToken}}"
                    ),

                    new MessageTemplate(NotificationType.APPOINTMENT_REMINDER, PreferredLanguage.AR, RecipientRole.PATIENT,
                            "تذكير بموعدك القادم",
                            "مرحباً {{patientName}}،\n\n" +
                                    "هذا تذكير بموعدك مع الدكتور {{doctorName}} في {{time}}.\n" +
                                    "معرف الموعد: {{appointmentId}}\n" +
                                    "رابط الانضمام: Token: {{patientToken}}"
                    ),

                    // --- APPOINTMENT_CANCELLED ---
                    new MessageTemplate(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.EN, RecipientRole.DOCTOR,
                            "Appointment Cancellation Notice",
                            "Hello Dr. {{doctorName}},\n\nThe appointment with {{patientName}} has been cancelled.\nAppointment ID: {{appointmentId}}\nStatus: {{status}}"),

                    new MessageTemplate(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.EN, RecipientRole.PATIENT,
                            "Your Appointment Was Cancelled",
                            "Hello {{patientName}},\n\nYour appointment with Dr. {{doctorName}} has been cancelled.\nAppointment ID: {{appointmentId}}\nStatus: {{status}}"),

                    new MessageTemplate(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.AR, RecipientRole.DOCTOR,
                            "إشعار إلغاء موعد",
                            "مرحباً د. {{doctorName}}،\n\nتم إلغاء الموعد مع المريض {{patientName}}.\nمعرف الموعد: {{appointmentId}}\nالحالة: {{status}}"),

                    new MessageTemplate(NotificationType.APPOINTMENT_CANCELLED, PreferredLanguage.AR, RecipientRole.PATIENT,
                            "تم إلغاء موعدك",
                            "مرحباً {{patientName}}،\n\nتم إلغاء موعدك مع الدكتور {{doctorName}}.\nمعرف الموعد: {{appointmentId}}\nالحالة: {{status}}"),

                    // --- ACCOUNT_VERIFIED ---

                    new MessageTemplate(NotificationType.ACCOUNT_VERIFIED, PreferredLanguage.EN, RecipientRole.BOTH,
                            "Account Verification Successful",
                            "Hello {{name}},\n\nYour account has been successfully verified. Welcome aboard!"),


                    new MessageTemplate(NotificationType.ACCOUNT_VERIFIED, PreferredLanguage.AR, RecipientRole.BOTH,
                            "تم التحقق من الحساب",
                            "مرحباً {{name}}،\n\nتم التحقق من حسابك بنجاح. مرحباً بك!"),

                    // --- VITAL_ALERT ---
                    new MessageTemplate(NotificationType.VITAL_ALERT, PreferredLanguage.EN, RecipientRole.PATIENT,
                            "Vital Alert",
                            "Dear {{name}},\n\nThere is a vital sign alert: {{content}}"),

                    new MessageTemplate(NotificationType.VITAL_ALERT, PreferredLanguage.AR, RecipientRole.PATIENT,
                            "تنبيه حيوي",
                            "عزيزي {{name}}،\n\nيوجد تنبيه بشأن العلامات الحيوية: {{content}}")
            );

            repository.saveAll(templates);
            System.out.println("✅ Notification templates Added.");
        }
    }
}
