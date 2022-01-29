package io.getarrays.userservice.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.getarrays.userservice.domain.Job;
import io.getarrays.userservice.domain.Salary;
import io.getarrays.userservice.respository.JobRespository;
import io.getarrays.userservice.service.JobService;
import io.getarrays.userservice.service.SalaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobResource {

    final private JobService jobService;
    final private JobRespository jobRespository;
    final private SalaryService salaryService;


    @GetMapping("/getAllJobs")
    public ResponseEntity<List<JobResponse>> getAllJob() {

        List<Job> joblist = jobRespository.findAll();

        List<JobResponse> responseJob = new ArrayList<>();

        joblist.forEach((e) -> {

            List<Salary> salaryList = salaryService.getSalaryByJobId(e.getId());
            int count = salaryList.size();

            double juniorSalary = salaryList.stream().mapToDouble(p -> p.getJuniorSalary()).sum();
            double seniorSalary = salaryList.stream().mapToDouble(p -> p.getSeniorSalary()).sum();
            double expertSalary = salaryList.stream().mapToDouble(p -> p.getExpertSalary()).sum();

            JobResponse jobResponse = new JobResponse(
                e.getId(), 
                e.getJobTitle(), 
                e.getIndustry(), 
                e.getJuniorImage(), 
                e.getJunior(), 
                e.getSeniorImage(), 
                e.getSenior(), 
                e.getExpertImage(), 
                e.getExpert(), 
                juniorSalary / count, 
                seniorSalary / count, 
                expertSalary / count);

                responseJob.add(jobResponse);
        });

        return new ResponseEntity<List<JobResponse>>(responseJob, HttpStatus.OK);
    }

    @PostMapping("/getJobById")
    public ResponseEntity<Job> getJobById(@RequestBody Map<String, String> data) {
        String jobId = data.get("jobId");
        return new ResponseEntity<>(jobService.findJobById(jobId), HttpStatus.OK);
    }
    
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@RequestBody Map<String, String> data) {
        String jobTitle = data.get("jobTitle");
        String industry = data.get("industry");
        String juniorImage = data.get("juniorImage");
        String junior = data.get("junior");
        String seniorImage = data.get("seniorImage");
        String senior = data.get("senior");
        String expertImage = data.get("expertImage");
        String expert = data.get("expert");

        Job job = new Job(
            UUID.randomUUID().toString(), 
            jobTitle, 
            industry, 
            juniorImage, 
            junior, 
            seniorImage, 
            senior, 
            expertImage, 
            expert);
        return new ResponseEntity<>(jobService.createJob(job), HttpStatus.OK);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class JobResponse {
    private String id;
    private String jobTitle;
    private String industry;
    private String juniorImage;
    private String junior;
    private String seniorImage;
    private String senior;
    private String expertImage;
    private String expert;
    private double juniorSalary;
    private double seniorSalary;
    private double expertSalary;
}
