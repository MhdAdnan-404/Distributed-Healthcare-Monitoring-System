package com.mhd.vitalSignManagement.repository;

import com.mhd.vitalSignManagement.model.VitalSignDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalSignRepository extends MongoRepository<VitalSignDocument, String> {
    VitalSignDocument findFirstByDeviceUniqueIdentifierOrderByTimeStampDesc(String deviceUniqueIdentifier);
}
