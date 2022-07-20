package io.professional.userservice.service;

import io.professional.userservice.domain.Job;

public interface JobService {
    Job createJob(Job job);
    Job findJobById(String jobId);
    
}
