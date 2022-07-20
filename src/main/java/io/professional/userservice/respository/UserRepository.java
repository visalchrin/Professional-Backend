package io.professional.userservice.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.professional.userservice.domain.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String>{
    AppUser findByUsername(String username);
    List<AppUser> findBySkillIsContaining(String skill);
    List<AppUser> findBySkillLike(String skill);
}
