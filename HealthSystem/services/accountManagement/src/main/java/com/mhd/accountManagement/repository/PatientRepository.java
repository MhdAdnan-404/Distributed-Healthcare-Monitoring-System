package com.mhd.accountManagement.repository;

import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Patient;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Map;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Page<Patient> findAll(Pageable pageable);

    @Query("""
    SELECT c
    FROM Patient p
    JOIN p.user u
    JOIN u.contactInfo c
    WHERE p.id = :patientId
    """)
    ContactInfo findContactsById(@Param("patientId") Integer patientId);
}
