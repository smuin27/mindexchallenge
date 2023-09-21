package com.mindex.challenge.dao;

import com.mindex.challenge.data.CompensationDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationRepository extends MongoRepository<CompensationDetails, String> {
    CompensationDetails findByEmployeeId(String employeeId);
}