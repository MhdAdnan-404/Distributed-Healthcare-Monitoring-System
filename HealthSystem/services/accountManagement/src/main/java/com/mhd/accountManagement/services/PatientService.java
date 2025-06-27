package com.mhd.accountManagement.services;

import com.mhd.accountManagement.Clients.VitalSignManagementMS.VitalSignManagementClient;
import com.mhd.accountManagement.exception.TooManyAddressesException;
import com.mhd.accountManagement.kafka.KafkaProducer;
import com.mhd.accountManagement.model.Address;
import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.PatientDTO.*;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Enums.Role;
import com.mhd.accountManagement.model.Patient;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PatientService extends AbstractService<
        Patient,
        Integer,
        PatientRepository,
        PatientRegisterRequest,
        PatientUpdateRequest,
        PatientRegisterResponse,
        PatientUpdateResponse
        > {


    @Autowired
    PatientMapper patientMapper;

    @Autowired
    VitalSignManagementClient vitalSignManagementClient;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    PatientRepository patientRepository;


    @Override
    public Users extractUserFromEntity(Patient patient) {
        return patient.getUser();
    }

    @Override
    public Role getUserRole() {
        return Role.PATIENT;
    }

    @Override
    public PatientRegisterResponse toRegisterResponseWithToken(Patient patient, String token) {
        return patientMapper.toPatientRegisterResponse(patient, token);
    }


    @Override
    public Integer getIdFromRequest(PatientUpdateRequest request) {
        return request.id();
    }

    @Override
    public Patient toEntity(PatientRegisterRequest request) {
        return patientMapper.toPatient(request);
    }

    @Override
    public PatientUpdateResponse toUpdateResponse(Patient patient) {
        return patientMapper.toPatientUpdateResponse(patient);
    }
    @Override
    public Patient deepCopyEntity(Patient original) {
        Patient copy = new Patient();
        Users user = new Users();
        user.setLanguage(original.getUser().getLanguage());

        ContactInfo originalContactInfo = original.getUser().getContactInfo();
        if (originalContactInfo != null) {
            ContactInfo copyContactInfo = new ContactInfo();
            copyContactInfo.setContacts(Map.copyOf(originalContactInfo.getContacts()));
            user.setContactInfo(copyContactInfo);
        }

        copy.setUser(user);
        return copy;
    }

    @Override
    public void mergeEntity(Patient patient, PatientUpdateRequest request) {
        if (request.name() != null && !request.name().isBlank()) {
            patient.setName(request.name());
        }

        if (request.allergies() != null && !request.allergies().isBlank()) {
            patient.setAllergies(request.allergies());
        }

        if (request.dateOfBirth() != null) {
            patient.setDateOfBirth(request.dateOfBirth());
        }

        if (request.language() != null) {
            patient.getUser().setLanguage(request.language());
        }

        ContactInfo contactInfo = patient.getUser().getContactInfo();
        ContactInfoDTO contactInfoDTO = request.contactInfo();

        if (contactInfoDTO != null && contactInfoDTO.contacts() != null && !contactInfoDTO.contacts().isEmpty()) {
            contactInfo.setContacts(contactInfoDTO.contacts());

        }

        List<AddressDTO> incomingAddresses = request.addresses();
        List<Address> currentAddresses = patient.getUser().getAddresses();

        for (AddressDTO addressDTO : incomingAddresses) {
            if (addressDTO.id() != null) {
                Address existing = currentAddresses.stream()
                        .filter(addr -> addr.getId().equals(addressDTO.id()))
                        .findFirst()
                        .orElse(null);

                if (existing != null) {
                    if (addressDTO.country() != null && !addressDTO.country().isBlank()) {
                        existing.setCountry(addressDTO.country());
                    }
                    if (addressDTO.city() != null && !addressDTO.city().isBlank()) {
                        existing.setCity(addressDTO.city());
                    }
                    if (addressDTO.streetName() != null && !addressDTO.streetName().isBlank()) {
                        existing.setStreetName(addressDTO.streetName());
                    }
                    if (addressDTO.streetNumber() != null && !addressDTO.streetNumber().isBlank()) {
                        existing.setStreetNumber(addressDTO.streetNumber());
                    }
                    if (addressDTO.label() != null && !addressDTO.label().isBlank()) {
                        existing.setLabel(addressDTO.label());
                    }
                }
            } else if (currentAddresses.size() < 3) {
                Address newAddress = Address.builder()
                        .country(addressDTO.country())
                        .city(addressDTO.city())
                        .streetName(addressDTO.streetName())
                        .streetNumber(addressDTO.streetNumber())
                        .label(addressDTO.label())
                        .user(patient.getUser())
                        .build();
                currentAddresses.add(newAddress);
            } else {
                throw new TooManyAddressesException("User already has 3 addresses.");
            }
        }
    }



    @Override
    public void beforeSave(Patient patient) {
    }

    @Override
    public Map<ContactType, String> getContacts(Integer id) {
        ContactInfo contactInfo = patientRepository.findContactsById(id);

        return contactInfo.getContacts();
    }

    public boolean addPatientToVitalSignManagement(Users user) {
        AddPatientToVitalManagementRequest request = patientMapper.toAddPatientToVitalManagement(user);

        return vitalSignManagementClient.addPatient(request);
    }
}
