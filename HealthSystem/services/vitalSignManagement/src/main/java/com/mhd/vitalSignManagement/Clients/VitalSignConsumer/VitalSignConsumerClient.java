package com.mhd.vitalSignManagement.Clients.VitalSignConsumer;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "vital-sign-Consumer",
        url="${application.config.vitalSignConsumer-url}"
)
public interface VitalSignConsumerClient {

    @PutMapping("/deviceLimits/updateLimits/{deviceUniqueIdentifier}")
    Boolean updateDeviceLimits(@PathVariable String deviceUniqueIdentifier);
}
