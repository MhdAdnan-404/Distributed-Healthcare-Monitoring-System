package com.mhd.DataCollection.repository;

import com.mhd.DataCollection.Domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    Optional<Device> findByUniqueIdentifier(String uniqueIdentifier);
}
