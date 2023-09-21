package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Gathering reporting structure for employee with id [{}]", id);

        ReportingStructure reportingStructure = new ReportingStructure();
        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        // Set employee to the one passed in
        reportingStructure.setEmployee(employee);
        int numberOfReports = 0;
        // if employee has no reports, return it with 0 as number of reports
        if(employee.getDirectReports() == null) {
            reportingStructure.setNumberOfReports(numberOfReports);
            return reportingStructure;
        }

        List<Employee> reports = employee.getDirectReports();
        Employee currentEmployee = employee;

        /*  I also considered a recursive approach here, but chose this iterative approach for performance reasons
            This approach keeps a 'queue' or list of the reports that haven't been queried yet. then goes through that
            list to find out if any of those reports have reports of their own until it reaches the point where there are
            no longer any reports to query. then returns the number of reports that were found along the way
        */
        while(reports.size() > 0) {
            numberOfReports++;
            currentEmployee = employeeRepository.findByEmployeeId(reports.get(0).getEmployeeId());
            if(currentEmployee.getDirectReports() == null){
                reports.remove(0);
                continue;
            }
            reports.addAll(currentEmployee.getDirectReports());
            reports.remove(0);
        }

        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }
}
