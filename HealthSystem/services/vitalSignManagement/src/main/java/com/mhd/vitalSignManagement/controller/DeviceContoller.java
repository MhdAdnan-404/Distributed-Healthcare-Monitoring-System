package com.mhd.vitalSignManagement.controller;


import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.AddDeviceToPatientRequest;
import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.DeviceLimitUpdateRequest;
import com.mhd.vitalSignManagement.service.DeviceService;
import com.mhd.vitalSignManagement.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
public class DeviceContoller {

    @Autowired
    private PatientService service;

    @Autowired
    private DeviceService deviceService;

    @DeleteMapping("/deleteDevice")
    public ResponseEntity<Boolean> deleteDevicesFromPatient(@RequestParam Integer patientId,
                                                           @RequestParam String deviceUniqueIdentifier){
        return ResponseEntity.ok(service.DeleteDeviceFromPatient(patientId, deviceUniqueIdentifier));
    }

    @PostMapping("/addDeviceToPatient")
    public ResponseEntity<Boolean> addDeviceToPatient(@RequestBody @Valid AddDeviceToPatientRequest request){
        return ResponseEntity.ok(service.addDeviceToPatient(request));
    }

    @PutMapping("/updateDeviceLimits")
    public ResponseEntity<Boolean> updateDeviceLimits(@RequestBody @Valid DeviceLimitUpdateRequest request){
        return ResponseEntity.ok(deviceService.updateDeviceLimit(request));
    }


}
