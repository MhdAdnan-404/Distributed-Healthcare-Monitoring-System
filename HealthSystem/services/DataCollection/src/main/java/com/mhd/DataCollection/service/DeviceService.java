package com.mhd.DataCollection.service;

import com.mhd.DataCollection.DTO.Device.AddDeviceRequest;
import com.mhd.DataCollection.DTO.Device.BulkDeleteRequest;
import com.mhd.DataCollection.DTO.Device.DeviceMapper;
import com.mhd.DataCollection.Domain.model.Device;
import com.mhd.DataCollection.Domain.model.Enums.DeviceProtocol;
import com.mhd.DataCollection.Domain.model.Measurment.VitalSignMeasurment;
import com.mhd.DataCollection.Kafka.VitalSignProducer;
import com.mhd.DataCollection.adapter.AdapterFactory;
import com.mhd.DataCollection.adapter.IAdapterProtocol;
import com.mhd.DataCollection.exception.*;
import com.mhd.DataCollection.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private VitalSignProducer vitalSignProducer;

    @Autowired
    private AdapterFactory adapterFactory;


    public Boolean addDevice(AddDeviceRequest addDeviceRequest){
        Optional<Device> device = deviceRepository.findByUniqueIdentifier(addDeviceRequest.uniqueIdentifier());
        if (device.isPresent()) {
            throw new DeviceHasAlreadyBeenAddedException("This device has already been added");
        }

        try {
            Device newdevice = deviceMapper.toDevice(addDeviceRequest);
            deviceRepository.save(newdevice);
            return true;
        } catch (Exception e) {
            throw new DeviceAddFailedException("Failed to add Device: "+ e);
        }
    }

    public Boolean deleteDevice(String deviceUnqiueIdentifier) {
        Device device = deviceRepository.findByUniqueIdentifier(deviceUnqiueIdentifier)
                .orElseThrow(() -> new DeviceNotFoundException("Device with ID " + deviceUnqiueIdentifier + " has not been found"));
        try {
            deviceRepository.delete(device);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public List<String> deleteDeviceBulk(BulkDeleteRequest request) {
        List<String> failedToDelete = new ArrayList<>();
        for (String id : request.deviceUniqueIdentifiers()) {
            boolean isDeleted = deleteDevice(id);
            if (!isDeleted) {
                failedToDelete.add(id);
            }
        }
        return failedToDelete;
    }

    public VitalSignMeasurment getVitalSign(Integer deviceId){
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device with Id Has not been found "+ deviceId));
        DeviceProtocol protocol = device.getConnectionInformation().getProtocol();
        IAdapterProtocol adapter = adapterFactory.configureAdapter(protocol);
        VitalSignMeasurment vitalSignMeasurment = adapter.getVitalSign(device.getConnectionInformation());
        vitalSignMeasurment.setDeviceUniqueIdentifier(device.getUniqueIdentifier());
        System.out.println("Fetched vital sign: " + vitalSignMeasurment);
        return vitalSignMeasurment;
    }


    public void getAndSendVitalSignToKafka(Device device){
        vitalSignProducer.sendVitalSignToKafka(getVitalSign(device.getId()));
    }
}
