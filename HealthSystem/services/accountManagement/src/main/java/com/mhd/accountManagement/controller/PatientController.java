package com.mhd.accountManagement.controller;

import com.mhd.accountManagement.model.DTO.PatientDTO.PatientRegisterRequest;
import com.mhd.accountManagement.model.DTO.PatientDTO.PatientRegisterResponse;
import com.mhd.accountManagement.model.DTO.PatientDTO.PatientUpdateRequest;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Patient;
import com.mhd.accountManagement.repository.PatientRepository;
import com.mhd.accountManagement.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Map;


@RestController
@RequestMapping("/patient")
public class PatientController extends AbstractController<
        PatientRegisterRequest,
        PatientRegisterResponse,
        PatientUpdateRequest,
        Void,
        Patient,
        Integer> {

    @Autowired
    private PatientService patientService;


    @Override
    protected PatientRegisterResponse registerEntity(PatientRegisterRequest request) {
        return patientService.register(request);
    }

    @Override
    protected Void updateEntity(PatientUpdateRequest request) {
        patientService.update(request);
        return null;
    }

    @Override
    protected Map<ContactType, String> getContactInfoForEntity(Integer id) {
        return patientService.getContacts(id);
    }

    @Override
    protected Page<Patient> getAllEntities(Pageable pageable) {
        return patientService.getAllEntities(pageable);
    }
}