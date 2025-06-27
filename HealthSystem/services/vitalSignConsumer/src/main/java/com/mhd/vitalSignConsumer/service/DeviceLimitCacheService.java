package com.mhd.vitalSignConsumer.service;


import com.mhd.vitalSignConsumer.Exceptions.DeviceDosentExsistException;
import com.mhd.vitalSignConsumer.model.DTO.PatientRaw;
import com.mhd.vitalSignConsumer.model.Patient.Patient;
import com.mhd.vitalSignConsumer.model.Patient.PatientMapper;
import com.mhd.vitalSignConsumer.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class DeviceLimitCacheService {
    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Cacheable(value = "devicePatientCache", key = "#deviceUniqueIdentifier")
    public Patient getPatient(String deviceUniqueIdentifier){
        PatientRaw raw = patientRepository.findByDeviceUniqueIdentifier(deviceUniqueIdentifier)
                .orElseThrow(() -> new DeviceDosentExsistException("This device Dosen't exsist"));
        return patientMapper.transform(raw);

    }

    @CachePut(value="devicePatientCache", key ="#deviceUniqueIdentifier")
    public Boolean refreshPatientLimitInCache(String deviceUniqueIdentifier){
        PatientRaw raw = patientRepository.findByDeviceUniqueIdentifier(deviceUniqueIdentifier)
                .orElseThrow(() -> new DeviceDosentExsistException("This device doesn't exist"));

        Patient transformed = patientMapper.transform(raw);
        redisTemplate.opsForValue().set("devicePatientCache::" + deviceUniqueIdentifier, transformed);
        return true;
    }

}
