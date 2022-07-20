package io.getarrays.userservice.service.Implement;

import org.springframework.stereotype.Service;

import io.getarrays.userservice.domain.Job;
import io.getarrays.userservice.respository.JobRespository;
import io.getarrays.userservice.service.JobService;
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
