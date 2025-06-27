package com.mhd.accountManagement.services;

import com.mhd.accountManagement.exception.TooManyAddressesException;
import com.mhd.accountManagement.model.Address;
import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.PatientDTO.PatientUpdateRequest;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Patient;
import com.mhd.accountManagement.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class PatientServiceTest_Structural {

    @Autowired
    private PatientService patientService;

    @BeforeEach
    void setup() {
        patientService = new PatientService();
    }

    @Test
    void mergeEntity_shouldNotUpdateName_whenNameIsNull() {
        Patient patient = createPatient("Original", "Allergy", LocalDate.of(1990, 1, 1));
        PatientUpdateRequest request = PatientUpdateRequest.builder()
                .name(null)
                .allergies(null)
                .dateOfBirth(null)
                .addresses(Collections.emptyList())
                .build();

        patientService.mergeEntity(patient, request);

        assertEquals("Original", patient.getName());
    }

    @Test
    void mergeEntity_shouldNotUpdateAllergies_whenAllergiesIsBlank() {
        Patient patient = createPatient("Name", "OriginalAllergy", LocalDate.of(1990, 1, 1));
        PatientUpdateRequest request = PatientUpdateRequest.builder()
                .allergies("  ")
                .addresses(Collections.emptyList())
                .build();

        patientService.mergeEntity(patient, request);

        assertEquals("OriginalAllergy", patient.getAllergies());
    }

    @Test
    void mergeEntity_shouldSkipContactInfo_whenContactsMapIsNull() {
        Patient patient = createPatient("Name", "Allergy", LocalDate.of(1990, 1, 1));
        Map<ContactType, String> original = new HashMap<>();
        original.put(ContactType.EMAIL, "a@b.com");
        patient.getUser().getContactInfo().setContacts(original);

        PatientUpdateRequest request = PatientUpdateRequest.builder()
                .contactInfo(new ContactInfoDTO(null, null))
                .addresses(Collections.emptyList())
                .build();

        patientService.mergeEntity(patient, request);

        assertEquals(original, patient.getUser().getContactInfo().getContacts());
    }

    @Test
    void mergeEntity_shouldUpdateExistingAddress_whenIdMatches() {
        Address address = Address.builder().country("OldCountry").build();
        address.setId(1);
        Patient patient = createPatient("Name", "Allergy", LocalDate.of(1990, 1, 1));
        patient.getUser().setAddresses(new ArrayList<>(List.of(address)));

        AddressDTO addressDTO = AddressDTO.builder()
                .id(1)
                .country("NewCountry")
                .build();

        PatientUpdateRequest request = PatientUpdateRequest.builder()
                .addresses(List.of(addressDTO))
                .build();

        patientService.mergeEntity(patient, request);

        assertEquals("NewCountry", patient.getUser().getAddresses().get(0).getCountry());
    }

    @Test
    void mergeEntity_shouldNotUpdateAddressField_whenFieldIsBlank() {
        Address address = Address.builder().country("OldCountry").build();
        address.setId(1);
        Patient patient = createPatient("Name", "Allergy", LocalDate.of(1990, 1, 1));
        patient.getUser().setAddresses(new ArrayList<>(List.of(address)));

        AddressDTO addressDTO = AddressDTO.builder()
                .id(1)
                .country(" ")
                .build();

        PatientUpdateRequest request = PatientUpdateRequest.builder()
                .addresses(List.of(addressDTO))
                .build();

        patientService.mergeEntity(patient, request);

        assertEquals("OldCountry", patient.getUser().getAddresses().get(0).getCountry());
    }


    private Patient createPatient(String name, String allergies, LocalDate dateOfBirth) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setContacts(new HashMap<>());

        Users user = new Users();
        user.setContactInfo(contactInfo);
        user.setAddresses(new ArrayList<>());

        Patient patient = new Patient();
        patient.setName(name);
        patient.setAllergies(allergies);
        patient.setDateOfBirth(dateOfBirth);
        patient.setUser(user);
        return patient;
    }



}
