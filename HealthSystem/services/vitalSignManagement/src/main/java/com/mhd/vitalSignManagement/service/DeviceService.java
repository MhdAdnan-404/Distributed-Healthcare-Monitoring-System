package com.mhd.vitalSignManagement.service;

import com.mhd.vitalSignManagement.Clients.DataCollection.DataCollectionClient;
import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.*;
import com.mhd.vitalSignManagement.Clients.VitalSignConsumer.VitalSignConsumerClient;
import com.mhd.vitalSignManagement.handler.exception.PatientDosentExistsException;
import com.mhd.vitalSignManagement.model.DeviceLimitTypes;
import com.mhd.vitalSignManagement.model.Patient.Patient;
import com.mhd.vitalSignManagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class DeviceService {

    @Autowired
    private DeviceMapper mapper;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private DataCollectionClient dataCollectionClient;

    @Autowired
    private VitalSignConsumerClient vitalSignConsumerClient;

    public boolean sendDeviceToDataCollection(AddDeviceToPatientRequest request) {
        AddDeviceRequest addDeviceRequest = mapper.toAddDeviceRequest(request);
        return dataCollectionClient.addDevice(addDeviceRequest);

    }

    public boolean deleteDeviceFromDataCollection(String deviceUniqueIdentifier){
        return dataCollectionClient.deleteDevice(deviceUniqueIdentifier);
    }

    public String deleteBulkDevicesFromDataCollection(BulkDeleteRequest request){
        List<String> failedToDelete = dataCollectionClient.deleteDeviceBulk(request);
        if(failedToDelete.isEmpty()){
            return "true";
        }else{
            String failedIds = failedToDelete.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            return "Failed to delete devices with IDS: " + failedIds;
        }

    }

    public Boolean updateDeviceLimit(DeviceLimitUpdateRequest request) {
        Patient patient = patientRepository.findByDeviceUniqueIdentifier(request.deviceUniqueIdentifier())
                .orElseThrow(() -> new PatientDosentExistsException("this patient doesn't exist"));

        for (DeviceRef device : patient.getDevicesId()) {
            if (device.uniqueIdentifier().equals(request.deviceUniqueIdentifier())) {

                Map<DeviceLimitTypes, Double> originalLimits = device.deviceLimits();

                DeviceRef updatedDevice = DeviceRef.builder()
                        .uniqueIdentifier(device.uniqueIdentifier())
                        .deviceLimits(request.deviceLimits())
                        .build();

                patient.getDevicesId().remove(device);
                patient.getDevicesId().add(updatedDevice);
                patientRepository.save(patient);

                Boolean res = vitalSignConsumerClient.updateDeviceLimits(request.deviceUniqueIdentifier());
                if (Boolean.FALSE.equals(res)) {
                    patient.getDevicesId().remove(updatedDevice);
                    patient.getDevicesId().add(
                            DeviceRef.builder()
                                    .uniqueIdentifier(device.uniqueIdentifier())
                                    .deviceLimits(originalLimits)
                                    .build()
                    );
                    patientRepository.save(patient);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

}
