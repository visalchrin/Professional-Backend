package io.getarrays.userservice.api;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.userservice.domain.Role;
import io.getarrays.userservice.domain.AppUser;
import io.getarrays.userservice.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @PostMapping("/search")
    public ResponseEntity<List<AppUser>> getUsersBySkills(@RequestBody Map<String, String> data) {
        String query = data.get("query");
        return ResponseEntity.ok().body(userService.getUsersBySkill(query));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/getUserById")
    public ResponseEntity<AppUser> getUserById(@RequestParam("id") String id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/profile")
    public ResponseEntity<AppUser> getUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        user.setId(UUID.randomUUID().toString());
        user.setProfile("https://th.bing.com/th/id/R.f1dd7ad11086e85a08ee93bba4d73b8d?rik=J9lwoSzd92YC6Q&pid=ImgRaw&r=0");

        // New user need to have some rule
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/profile/edit")
    @Transactional
    public ResponseEntity<?> EditUser(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String fullname = data.get("fullname");
        String email = data.get("email");
        String skill = data.get("skill");
        String experience = data.get("experience");
        String description = data.get("description");
        String profile = data.get("profile");

        AppUser user = userService.getUser(username);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setSkill(skill);
        user.setExperience(experience);
        user.setDescription(description);
        user.setProfile(profile);

        
        Map<String, String> response = new HashMap<>();
        response.put("message", "User information was updated successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }


    @PostMapping("/role/addToUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                String acess_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", acess_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
                
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }


}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
