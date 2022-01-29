package io.getarrays.userservice.service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import io.getarrays.userservice.domain.Salary;
import io.getarrays.userservice.respository.SalaryRepository;
import io.getarrays.userservice.service.SalaryService;
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
