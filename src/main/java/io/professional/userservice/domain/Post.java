package io.professional.userservice.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    private String id;
    private String content;
    private String ownerId;
    private Date createdDate;
    private String ownerName;
    private String ownerUsername;
    private Integer like_count;
}
