package com.mhd.DataCollection.controller;


import com.mhd.DataCollection.DTO.Device.AddDeviceRequest;
import com.mhd.DataCollection.DTO.Device.BulkDeleteRequest;
import com.mhd.DataCollection.DTO.Device.DeviceLimitUpdateRequest;
import com.mhd.DataCollection.Domain.model.Measurment.VitalSignMeasurment;
import com.mhd.DataCollection.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController{

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/addDevice")
    public ResponseEntity<Boolean> addDevice(@RequestBody @Valid AddDeviceRequest addDeviceRequest){
        return ResponseEntity.ok(deviceService.addDevice(addDeviceRequest));
    }

    @DeleteMapping("/DeleteDevice/{deviceUniqueIdentifier}")
    public ResponseEntity<Boolean> deleteDevice(@PathVariable String deviceUniqueIdentifier){
        return ResponseEntity.ok(deviceService.deleteDevice(deviceUniqueIdentifier));
    }
    @DeleteMapping("/BulkDeleteDevices")
    public ResponseEntity<List<String>> deleteDeviceBulk(@RequestBody @Valid BulkDeleteRequest request){
        return ResponseEntity.ok(deviceService.deleteDeviceBulk(request));
    }



}
