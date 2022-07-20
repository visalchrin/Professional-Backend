package io.professional.userservice.service.Implement;

import org.springframework.stereotype.Service;

import io.professional.userservice.domain.Job;
import io.professional.userservice.respository.JobRespository;
import io.professional.userservice.service.JobService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{

    final private JobRespository jobRespository;

    @Override
    public Job createJob(Job job) {
        return jobRespository.save(job);
    }

    @Override
    public Job findJobById(String jobId) {
        return jobRespository.getById(jobId);
    }
    
}
