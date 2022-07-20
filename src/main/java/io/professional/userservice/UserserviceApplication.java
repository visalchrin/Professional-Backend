package io.professional.userservice;

// import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
// import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.professional.userservice.service.UserService;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean 
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) 
	{
		return args -> {
			// userService.saveRole(new Role(UUID.randomUUID().toString(), "ROLE_USER", "Regular user"));
			// userService.saveRole(new Role(UUID.randomUUID().toString(), "ROLE_MANAGER", "Role Manager"));
			// userService.saveRole(new Role(UUID.randomUUID().toString(), "ROLE_ADMIN", "Role Admin"));
			// userService.saveRole(new Role(UUID.randomUUID().toString(), "ROLE_SUPER_ADMIN", "Role Super Admin"));

			// userService.saveUser(new AppUser(null, "Visal Chrin", "visal", "1234", "UI/UX", new ArrayList<>()));
			// userService.saveUser(new AppUser(null, "Dara Kok", "dara", "1234", "Web and App", new ArrayList<>()));
			// userService.saveUser(new AppUser(null, "Chenda Meng", "chenda", "124", "Digital Marketing", new ArrayList<>()));
			// userService.saveUser(new AppUser(null, "Sokha Koe", "sokha", "1234", "UI/UX", new ArrayList<>()));

			// userService.addRoleToUser("darakok", "ROLE_USER");
			// userService.addRoleToUser("visal", "ROLE_MANAGER");
			// userService.addRoleToUser("visal", "ROLE_ADMIN");

			// userService.addRoleToUser("sokha", "ROLE_SUPER_ADMIN");
			// userService.addRoleToUser("sokha", "ROLE_ADMIN");
		};
	}
	

	@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        config.setAllowedOrigins(Arrays.asList("http://localhost:8081","https://professional-cambodia.herokuapp.com", "http://localhost:4200", "http://localhost:9093"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        config.setExposedHeaders(Collections.singletonList("x-auth-token"));
        FilterRegistrationBean<?> bean = new FilterRegistrationBean(new CorsFilter(source));
        source.registerCorsConfiguration("/**", config);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
