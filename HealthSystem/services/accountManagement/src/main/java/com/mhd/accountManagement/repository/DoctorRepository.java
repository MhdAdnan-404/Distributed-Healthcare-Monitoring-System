package com.mhd.accountManagement.repository;

import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.Doctor;
import com.mhd.accountManagement.model.Enums.ApprovalStatues;
import com.mhd.accountManagement.model.Enums.ContactType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByApprovalStatues(ApprovalStatues approvalstatusenum);

    Page<Doctor> findAll(Pageable pageable);


    @Query("""
    SELECT c
    FROM Doctor d
    JOIN d.user u
    JOIN u.contactInfo c
    WHERE d.id = :doctorId
    """)
    ContactInfo findContactsById(@Param("doctorId") Integer doctorId);
}
