package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.CompensationDetails;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    /*
        Since we already have the Employee info stored, we don't need to be storing it again. So instead I decided
        only to store the employee Id, salary, and effective date and combine it with existing employee data on the read
    */

    // Create compensation for user from provided employee id, salary, and effective date
    @Override
    public CompensationDetails create(CompensationDetails compensationDetails) {
        LOG.debug("Creating compensation [{}]", compensationDetails);

        compensationRepository.insert(compensationDetails);

        return compensationDetails;
    }

    /*
        Takes the employee id and gets employee info from employee repository and compensation details from compensation repository
        then combines them into the desired Compensation format to return.
    */
    @Override
    public Compensation read(String id) {
        LOG.debug("Gathering compensation for id [{}]", id);

        Compensation compensation = new Compensation();
        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        CompensationDetails compensationDetails = compensationRepository.findByEmployeeId(id);

        if (compensationDetails == null) {
            throw new RuntimeException("No compensation for employee: " + id);
        }

        compensation.setEmployee(employee);
        compensation.setSalary(compensationDetails.getSalary());
        compensation.setEffectiveDate(compensationDetails.getEffectiveDate());

        return compensation;
    }

}
