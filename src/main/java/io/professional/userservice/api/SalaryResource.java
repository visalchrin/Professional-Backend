package io.professional.userservice.api;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.professional.userservice.domain.Salary;
import io.professional.userservice.service.SalaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class SalaryResource {
    final private SalaryService salaryService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@RequestBody Map<String, String> data) {
        String jobId = data.get("jobId");
        double juniorSalary = Double.parseDouble(data.get("juniorSalary"));
        double seniorSalary = Double.parseDouble(data.get("seniorSalary"));
        double expertSalary = Double.parseDouble(data.get("expertSalary"));
        
        Salary salary = new Salary(UUID.randomUUID().toString(), jobId, juniorSalary, seniorSalary, expertSalary);
        return new ResponseEntity<>(salaryService.addNewSalary(salary), HttpStatus.OK);
    }
}
