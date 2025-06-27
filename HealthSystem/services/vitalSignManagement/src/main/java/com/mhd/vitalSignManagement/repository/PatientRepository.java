package com.mhd.vitalSignManagement.repository;

import com.mhd.vitalSignManagement.model.Patient.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.attribute.standard.JobKOctets;
import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, Integer> {

    @Query("{ 'devicesId.uniqueIdentifier': ?0 }")
    Optional<Patient> findByDeviceUniqueIdentifier(String uniqueIdentifier);

}
