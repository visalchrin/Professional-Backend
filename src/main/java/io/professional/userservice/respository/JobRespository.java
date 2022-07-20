package io.professional.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.professional.userservice.domain.Job;

public interface JobRespository extends JpaRepository<Job, String> {
    
}
