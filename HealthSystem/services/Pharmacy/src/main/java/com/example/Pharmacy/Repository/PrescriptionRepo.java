package com.example.Pharmacy.Repository;

import com.example.Pharmacy.Domain.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepo extends JpaRepository<Prescription, Long> {

    Page<Prescription> findByFilledFalse(Pageable pageable);

}
