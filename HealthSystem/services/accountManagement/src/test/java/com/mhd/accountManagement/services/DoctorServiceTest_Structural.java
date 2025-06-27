package com.mhd.accountManagement.services;

import com.mhd.accountManagement.exception.TooManyAddressesException;
import com.mhd.accountManagement.model.Address;
import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.DoctorDTO.DoctorUpdateRequest;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.Doctor;
import com.mhd.accountManagement.model.Enums.DoctorSpecialty;
import com.mhd.accountManagement.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoctorServiceTest_Structural {
    @Autowired
    private DoctorService doctorService;

    @BeforeEach
    void setup() {
        doctorService = new DoctorService();
    }

    @Test
    void mergeEntity_shouldUpdateName_whenNameIsProvided() {
        Doctor doctor = createDoctor("Old Name", DoctorSpecialty.CARDIOLOGIST);
        DoctorUpdateRequest request = DoctorUpdateRequest.builder()
                .name("New Name")
                .addresses(List.of())
                .build();

        doctorService.mergeEntity(doctor, request);
        assertEquals("New Name", doctor.getName());
    }

    @Test
    void mergeEntity_shouldNotUpdateName_whenNameIsBlank() {
        Doctor doctor = createDoctor("Old Name", DoctorSpecialty.CARDIOLOGIST);
        DoctorUpdateRequest request = DoctorUpdateRequest.builder()
                .name("  ")
                .addresses(List.of())
                .build();

        doctorService.mergeEntity(doctor, request);
        assertEquals("Old Name", doctor.getName());
    }

    @Test
    void mergeEntity_shouldUpdateSpecialty_whenProvided() {
        Doctor doctor = createDoctor("Name", DoctorSpecialty.CARDIOLOGIST);
        DoctorUpdateRequest request = DoctorUpdateRequest.builder()
                .specialty(DoctorSpecialty.DENTIST)
                .addresses(List.of())
                .build();

        doctorService.mergeEntity(doctor, request);
        assertEquals(DoctorSpecialty.DENTIST, doctor.getSpeciality());
    }

    @Test
    void mergeEntity_shouldAddNewAddress_whenLessThanThreeExist() {
        Doctor doctor = createDoctor("Name", DoctorSpecialty.CARDIOLOGIST);
        doctor.getUser().setAddresses(new ArrayList<>());

        AddressDTO newAddress = AddressDTO.builder()
                .country("USA")
                .city("NYC")
                .streetName("Wall St")
                .streetNumber("10")
                .label("Main")
                .build();

        DoctorUpdateRequest request = DoctorUpdateRequest.builder()
                .addresses(List.of(newAddress))
                .build();

        doctorService.mergeEntity(doctor, request);
        assertEquals(1, doctor.getUser().getAddresses().size());
    }

    @Test
    void mergeEntity_shouldThrowTooManyAddressesException_whenLimitExceeded() {
        Doctor doctor = createDoctor("Name", DoctorSpecialty.CARDIOLOGIST);
        doctor.getUser().setAddresses(List.of(new Address(), new Address(), new Address()));

        AddressDTO newAddress = AddressDTO.builder().build(); // triggers add
        DoctorUpdateRequest request = DoctorUpdateRequest.builder()
                .addresses(List.of(newAddress))
                .build();

        assertThrows(TooManyAddressesException.class, () ->
                doctorService.mergeEntity(doctor, request));
    }

    private Doctor createDoctor(String name, DoctorSpecialty specialty) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setContacts(new HashMap<>());

        Users user = new Users();
        user.setContactInfo(contactInfo);
        user.setAddresses(new ArrayList<>());

        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpeciality(specialty);
        doctor.setUser(user);
        return doctor;
    }


}
