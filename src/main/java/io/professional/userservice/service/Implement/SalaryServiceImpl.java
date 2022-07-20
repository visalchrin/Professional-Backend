package io.professional.userservice.service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import io.professional.userservice.domain.Salary;
import io.professional.userservice.respository.SalaryRepository;
import io.professional.userservice.service.SalaryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService{

    final SalaryRepository salaryRepository;

    @Override
    public Salary addNewSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    @Override
    public List<Salary> getSalaryByJobId(String jobId) {
        return salaryRepository.findByJobId(jobId);
    }
    
}
