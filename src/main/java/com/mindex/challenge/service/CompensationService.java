package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.CompensationDetails;

public interface CompensationService {
    CompensationDetails create(CompensationDetails compensationDetails);
    Compensation read(String id);
}
