package com.mhd.AppointmentsManagement.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(
        name="CallManagement-service",
        url="${application.config.callManagement-url}"
)
public interface CallManagementClient {

    @PostMapping("/token")
    Map<String,String> createCallToken(Map<String,String> params);
}
