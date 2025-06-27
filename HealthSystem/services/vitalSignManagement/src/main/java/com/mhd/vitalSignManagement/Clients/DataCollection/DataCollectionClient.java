package com.mhd.vitalSignManagement.Clients.DataCollection;


import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.AddDeviceRequest;
import com.mhd.vitalSignManagement.Clients.DataCollection.DTO.BulkDeleteRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "data-collection-service",
        url = "${application.config.dataCollection-url}"
)
public interface DataCollectionClient {
    @PostMapping("/device/addDevice")
    Boolean addDevice(@Valid @RequestBody AddDeviceRequest addDeviceRequest);

    @DeleteMapping("/device/DeleteDevice/{deviceId}")
    Boolean deleteDevice(@PathVariable("deviceId") String deviceId);

    @DeleteMapping("/device/BulkDeleteDevices")
    List<String> deleteDeviceBulk(@RequestBody @Valid BulkDeleteRequest request);

}
