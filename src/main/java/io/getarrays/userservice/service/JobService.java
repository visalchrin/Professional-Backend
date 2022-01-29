package io.getarrays.userservice.service;

import io.getarrays.userservice.domain.Job;

public interface JobService {
    Job createJob(Job job);
    Job findJobById(String jobId);
    
}
