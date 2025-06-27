package com.mhd.vitalSignConsumer.repository;


import com.mhd.vitalSignConsumer.model.VitalSign.VitalSignDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VitalSignReporsitory extends MongoRepository<VitalSignDocument, String> {
}
