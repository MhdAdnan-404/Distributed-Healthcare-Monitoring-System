package com.mhd.vitalSignConsumer.controller;

import com.mhd.vitalSignConsumer.service.DeviceLimitCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deviceLimits")
public class limitsController {

    @Autowired
    private DeviceLimitCacheService deviceLimitCacheService;


    @PutMapping("/updateLimits/{deviceUniqueIdentifier}")
    public Boolean updateLimits(@PathVariable String deviceUniqueIdentifier){
        return deviceLimitCacheService.refreshPatientLimitInCache(deviceUniqueIdentifier);
    }

}
