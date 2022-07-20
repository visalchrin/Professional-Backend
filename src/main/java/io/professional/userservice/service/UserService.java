package io.professional.userservice.service;

import java.util.List;

import io.professional.userservice.domain.AppUser;
import io.professional.userservice.domain.Role;

public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    AppUser getUserById(String id);
    List<AppUser> getUsers();
    List<AppUser> getUsersBySkill(String skill);
}
