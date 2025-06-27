package com.mhd.accountManagement.services;
import com.mhd.accountManagement.exception.NoPendingDoctorException;
import com.mhd.accountManagement.exception.TooManyAddressesException;
import com.mhd.accountManagement.exception.UserDosentExistException;
import com.mhd.accountManagement.kafka.KafkaProducer;
import com.mhd.accountManagement.model.Address;
import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.DoctorDTO.*;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.Doctor;
import com.mhd.accountManagement.model.Enums.ApprovalStatues;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Enums.Role;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.DoctorRepository;
import com.mhd.accountManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class DoctorService extends AbstractService<
        Doctor,
        Integer,
        DoctorRepository,
        DoctorRegisterRequest,
        DoctorUpdateRequest,
        DoctorRegisterResponse,
        DoctorUpdateResponse>{

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    UserRepository userRepository;



    public DoctorUpdateResponse updateDoctor(DoctorUpdateRequest request) {
        var doctor = repository.findById(request.id())
                .orElseThrow(() -> new UserDosentExistException(format("Cannot update Doctor: No doctor with this ID: ", request.id())
                ));

        mergeEntity(doctor, request);
        repository.save(doctor);
        return doctorMapper.toDoctorUpdateResponse(doctor);

    }
//    add pagination
    public List<DoctorRegisterResponse> getDoctorsPendingApproval() {
        List<Doctor> doctorList = repository.findByApprovalStatues(ApprovalStatues.PENDING);

        if (doctorList.isEmpty()) {
            throw new NoPendingDoctorException("There are no pending doctors at this time.");
        }

        return doctorList.stream()
                .map(doctor -> {
                    String token = doctor.getUser().getActivationToken() != null
                            ? doctor.getUser().getActivationToken().getToken()
                            : null;
                    return doctorMapper.toDoctorRegisterResponse(doctor, token);
                })
                .collect(Collectors.toList());
    }

    public String approveDoctor(Integer doctorId) {
        Doctor doctor = repository.findById(doctorId)
                .orElseThrow(() -> new UserDosentExistException("Doctor not found"));
        doctor.setApprovalStatues(ApprovalStatues.APPROVED);
        repository.save(doctor);
        return "Doctor Has been approved";
    }

    @Override
    public Doctor deepCopyEntity(Doctor original){
        Doctor copy = new Doctor();
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
    public void mergeEntity(Doctor doctor, DoctorUpdateRequest request) {
        if (request.name() != null && !request.name().isBlank()) {
            doctor.setName(request.name());
        }

        if (request.specialty() != null) {
            doctor.setSpeciality(request.specialty());
        }

        if (request.language() != null) {
            doctor.getUser().setLanguage(request.language());
        }

        ContactInfoDTO contactInfoDTO = request.contactInfo();
        ContactInfo contactInfo = doctor.getUser().getContactInfo();

        if (contactInfoDTO != null && contactInfoDTO.contacts() != null && !contactInfoDTO.contacts().isEmpty()) {
            contactInfo.setContacts(contactInfoDTO.contacts());

        }

        List<AddressDTO> incomingAddresses = request.addresses();
        List<Address> currentAddresses = doctor.getUser().getAddresses();

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
                        .user(doctor.getUser())
                        .build();
                currentAddresses.add(newAddress);
            } else {
                throw new TooManyAddressesException("User already has 3 addresses");
            }
        }
    }



    @Override
    public DoctorUpdateResponse toUpdateResponse(Doctor doctor) {
        return doctorMapper.toDoctorUpdateResponse(doctor);
    }

    @Override
    public Doctor toEntity(DoctorRegisterRequest request) {
        return doctorMapper.toDoctor(request);
    }

    @Override
    public Integer getIdFromRequest(DoctorUpdateRequest request) {
        return request.id();
    }

    @Override
    public Users extractUserFromEntity(Doctor doctor) {
        return doctor.getUser();
    }

    @Override
    public Role getUserRole() {
        return Role.DOCTOR;
    }

    @Override
    public DoctorRegisterResponse toRegisterResponseWithToken(Doctor doctor, String token) {
        return doctorMapper.toDoctorRegisterResponse(doctor, token);
    }

    public void beforeSave(Doctor doctor) {
        doctor.setApprovalStatues(ApprovalStatues.PENDING);
    }

    @Override
    public Map<ContactType, String> getContacts(Integer id) {
        ContactInfo contactInfo = doctorRepository.findContactsById(id);
        return contactInfo.getContacts();
    }
}
