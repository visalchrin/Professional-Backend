package io.professional.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.professional.userservice.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);
}
