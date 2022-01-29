package io.getarrays.userservice.service;

import java.util.List;

import io.getarrays.userservice.domain.Salary;

public interface SalaryService {
    
    Salary addNewSalary(Salary salary);
    List<Salary> getSalaryByJobId(String jobId);
}
