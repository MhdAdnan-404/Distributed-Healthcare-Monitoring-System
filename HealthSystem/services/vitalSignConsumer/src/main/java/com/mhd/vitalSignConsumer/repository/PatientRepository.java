package com.mhd.vitalSignConsumer.repository;

import com.mhd.vitalSignConsumer.model.DTO.PatientRaw;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<PatientRaw, Integer> {
    @Query("{ 'devicesId.uniqueIdentifier': ?0 }")
    Optional<PatientRaw> findByDeviceUniqueIdentifier(String uniqueIdentifier);
}
