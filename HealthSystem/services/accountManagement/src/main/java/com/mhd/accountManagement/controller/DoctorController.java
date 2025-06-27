package com.mhd.accountManagement.controller;

import com.mhd.accountManagement.model.DTO.DoctorDTO.*;
import com.mhd.accountManagement.model.Doctor;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.repository.DoctorRepository;
import com.mhd.accountManagement.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
public class DoctorController extends AbstractController<
        DoctorRegisterRequest,
        DoctorRegisterResponse,
        DoctorUpdateRequest,
        Void,
        Doctor,
        Integer> {

    @Autowired
    private DoctorService doctorService;


    @Override
    protected DoctorRegisterResponse registerEntity(DoctorRegisterRequest request) {
        return doctorService.register(request);
    }


    @Override
    protected Void updateEntity(DoctorUpdateRequest request) {
        doctorService.updateDoctor(request);
        return null;
    }

    @Override
    protected Map<ContactType, String> getContactInfoForEntity(Integer id) {
        return doctorService.getContacts(id);
    }

    @Override
    protected Page<Doctor> getAllEntities(Pageable pageable) {
        return doctorService.getAllEntities(pageable);
    }
}