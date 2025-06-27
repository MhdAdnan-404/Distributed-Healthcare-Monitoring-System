package com.mhd.DataCollection.service;

import com.mhd.DataCollection.Domain.model.Device;
import com.mhd.DataCollection.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SchedulingService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;



    @Scheduled(fixedRate = 1000)
    public void scheduleVitalSignCollection() {
        List<Device> devices = deviceRepository.findAll();
        int batchSize = 2;

        int total = devices.size();
        for (int i = 0; i < total; i += batchSize) {
            int lastInBatch = Math.min(i + batchSize, total);
            List<Device> batch = devices.subList(i, lastInBatch);


            executorService.submit(() -> getVitalsForBatch(batch));
        }
    }

    private void getVitalsForBatch(List<Device> batch) {
        for (Device device : batch) {
            deviceService.getAndSendVitalSignToKafka(device);
        }
    }
}
