package io.getarrays.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.userservice.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);
}
