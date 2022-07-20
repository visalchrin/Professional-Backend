package io.professional.userservice.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.professional.userservice.domain.Salary;

public interface SalaryRepository extends JpaRepository<Salary, String> {
    List<Salary> findByJobId(String jobId);
    
}
