package io.getarrays.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"userId", "followingId"})
)

public class Following {

    @Id
    String id;
    String userId;
    String followingId;
}
