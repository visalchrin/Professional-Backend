package io.professional.userservice.service;

import java.util.List;

import io.professional.userservice.domain.Salary;

public interface SalaryService {
    
    Salary addNewSalary(Salary salary);
    List<Salary> getSalaryByJobId(String jobId);
}
