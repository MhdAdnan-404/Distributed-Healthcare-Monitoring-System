package com.mhd.accountManagement.model.DTO.PatientDTO;
import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.UserMapper;
import com.mhd.accountManagement.model.Patient;
import com.mhd.accountManagement.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientMapper {

    @Autowired
    UserMapper userMapper;
    public PatientUpdateResponse toPatientUpdateResponse(Patient patient) {
        ContactInfo contactInfo = patient.getUser().getContactInfo();


        List<AddressDTO> addressDTOs = patient.getUser().getAddresses().stream()
                .map(address -> new AddressDTO(
                        address.getId(),
                        address.getCountry(),
                        address.getCity(),
                        address.getStreetName(),
                        address.getStreetNumber(),
                        address.getLabel()
                ))
                .toList();


        ContactInfoDTO contactInfoDTO = userMapper.toContactInfoDTO(contactInfo);

        return new PatientUpdateResponse(
                patient.getId(),
                patient.getName(),
                patient.getDateOfBirth(),
                contactInfoDTO,
                addressDTOs,
                patient.getAllergies()
        );
    }


    public AddPatientToVitalManagementRequest toAddPatientToVitalManagement(Users user){
        return AddPatientToVitalManagementRequest
                .builder()
                .id(user.getPatient().getId())
                .name(user.getPatient().getName())
                .build();
    }


    public Patient toPatient(PatientRegisterRequest request) {
        Users user = userMapper.toUserEntity(request.user());

        Patient patient = Patient.builder()
                .name(request.name())
                .dateOfBirth(request.dateOfBirth())
                .allergies(request.allergies())
                .user(user)
                .build();

        return patient;

    }

    public PatientRegisterResponse toPatientRegisterResponse(Patient patient, String token) {
        return new PatientRegisterResponse(
                patient.getId(),
                token
        );
    }
}
