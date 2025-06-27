package com.mhd.vitalSignManagement.service;

import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.AddDeviceToPatientRequest;
import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.BulkDeleteRequest;
import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.DeviceMapper;
import com.mhd.vitalSignManagement.handler.exception.*;
import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.DeviceRef;
import com.mhd.vitalSignManagement.model.Patient.Patient;
import com.mhd.vitalSignManagement.model.Patient.AddPatientRequest;
import com.mhd.vitalSignManagement.model.Patient.PatientMapper;
import com.mhd.vitalSignManagement.model.VitalSignDocument;
import com.mhd.vitalSignManagement.repository.PatientRepository;
import com.mhd.vitalSignManagement.repository.VitalSignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private VitalSignRepository vitalSignRepository;

    @Autowired
    private PatientMapper mapper;

    public Boolean addPatient(AddPatientRequest request) {
        if (patientRepository.existsById(request.id())) {
            throw new PatientAlreadyExistsException("Patient already exists with ID: " + request.id());
        }
        Patient saved = patientRepository.save(mapper.toPatient(request));
        return saved != null;

    }

    public Boolean addDeviceToPatient(AddDeviceToPatientRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new PatientDosentExistsException("there is no patient with this ID: " + request.patientId()));

        boolean alreadyAssigned = patient.getDevicesId().stream()
                .anyMatch(device -> device.uniqueIdentifier().equals(request.uniqueIdentifier()));
        if (alreadyAssigned) {
            throw new DeviceHasAlreadyBeenAddedToThisPatientException("this device has already been added to this patient");
        }

        DeviceRef deviceRef = deviceMapper.toDeviceRef(request);
        patient.getDevicesId().add(deviceRef);
        Patient savedPatient = patientRepository.save(patient);

         try {
            Boolean success = deviceService.sendDeviceToDataCollection(request);
            if (!success) {

                savedPatient.getDevicesId().removeIf(d -> d.uniqueIdentifier().equals(request.uniqueIdentifier()));
                patientRepository.save(savedPatient);
                throw new FailedtoAddDevice("Failed to add device to data collection. Rolled back patient update.");
            }
        } catch (Exception e) {

            savedPatient.getDevicesId().removeIf(d -> d.uniqueIdentifier().equals(request.uniqueIdentifier()));
            patientRepository.save(savedPatient);
            throw new FailedtoAddDevice("Device addition failed: " + e.getMessage());
        }
        return true;
    }

    public Boolean DeleteDeviceFromPatient(Integer patientId, String deviceUniqueIdentifier) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientDosentExistsException("Patient not found"));

        Optional<DeviceRef> refToDelete = patient.getDevicesId().stream()
                .filter(d -> d.uniqueIdentifier().equals(deviceUniqueIdentifier))
                .findFirst();

        if (refToDelete.isEmpty()) {
            throw new DeviceNotFoundException("Device not assigned to this patient");
        }
        patient.getDevicesId().remove(refToDelete.get());
        patientRepository.save(patient);

        boolean deleted = deviceService.deleteDeviceFromDataCollection(deviceUniqueIdentifier);
        if (!deleted) {
            patient.getDevicesId().add(refToDelete.get());
            patientRepository.save(patient);
            throw new FailedtoDeleteDeviceException("Device Service deletion failed, rollback applied");
        }
        return true;
    }

    public String deletePatient(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientDosentExistsException("Patient not found"));

        BulkDeleteRequest bulkDeleteRequest = BulkDeleteRequest
                .builder()
                .deviceUniqueIdentifiers(patient.getDevicesId().stream()
                        .map(deviceRef -> deviceRef.uniqueIdentifier())
                        .toList())
                .build();
        String deleteResult = deviceService.deleteBulkDevicesFromDataCollection(bulkDeleteRequest);
        if(!deleteResult.equals("true")){
            return deleteResult;
        }
        patientRepository.delete(patient);
        return "true";
    }

    public List<VitalSignDocument> getTheCurrReadgins(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientDosentExistsException("this patient Dosen't Exists"));

        List<VitalSignDocument> readings = new ArrayList<>();

        List<String> deviceIdentifiers = patient.getDevicesId().stream()
                .map(deviceRef -> deviceRef.uniqueIdentifier())
                .toList();


        for (String deviceIdentifier: deviceIdentifiers){
            VitalSignDocument latest = vitalSignRepository.findFirstByDeviceUniqueIdentifierOrderByTimeStampDesc(deviceIdentifier);
            readings.add(latest);
        }
        return readings;
    }
}
