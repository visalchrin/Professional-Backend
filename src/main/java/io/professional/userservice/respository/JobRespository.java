package io.getarrays.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.userservice.domain.Job;

public interface JobRespository extends JpaRepository<Job, String> {
    
}
