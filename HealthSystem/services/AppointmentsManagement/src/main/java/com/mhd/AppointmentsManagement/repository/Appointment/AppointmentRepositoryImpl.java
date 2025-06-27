package com.mhd.AppointmentsManagement.repository.Appointment;

import com.mhd.AppointmentsManagement.model.Appointment;
import com.mhd.AppointmentsManagement.model.Enums.AppointmentStatues;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentRepositoryImpl implements AppointmentCustomRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Appointment> findConflictingAppointments(Integer doctorId, LocalDateTime requestedStart, LocalDateTime requestedEnd, Long excludedAppointmentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = cb.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("doctorId"), doctorId));
        predicates.add(cb.isFalse(root.get("isDeleted")));
        predicates.add(cb.equal(root.get("appointmentStatues"), AppointmentStatues.BOOKED));

        Predicate overlap1 = cb.and(
                cb.lessThanOrEqualTo(root.get("startTime"), requestedStart),
                cb.greaterThan(root.get("approximatedEndTime"), requestedStart)
        );

        Predicate overlap2 = cb.and(
                cb.lessThan(root.get("startTime"), requestedEnd),
                cb.greaterThanOrEqualTo(root.get("approximatedEndTime"), requestedEnd)
        );
        Predicate overlap3 = cb.and(
                cb.greaterThanOrEqualTo(root.get("startTime"), requestedStart),
                cb.lessThanOrEqualTo(root.get("approximatedEndTime"), requestedEnd)
        );

        predicates.add(cb.or(overlap1, overlap2, overlap3));

        if (excludedAppointmentId != null) {
            predicates.add(cb.notEqual(root.get("id"), excludedAppointmentId));
        }
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
