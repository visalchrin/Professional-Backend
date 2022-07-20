package io.professional.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Job {

    @Id
    private String id;
    private String jobTitle;
    private String industry;
    private String juniorImage;
    private String junior;
    private String seniorImage;
    private String senior;
    private String expertImage;
    private String expert;
    
}
