package org.launchcode.codingevents;

import org.launchcode.codingevents.data.RoleRepository;
import org.launchcode.codingevents.data.UserRepository;
import org.launchcode.codingevents.models.Role;
import org.launchcode.codingevents.models.RoleName;
import org.launchcode.codingevents.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CodingEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingEventsApplication.class, args);
    }
    @Bean
    CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
// Ensure roles are created
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(null, RoleName.ADMIN)));
            Role userRole = roleRepository.findByName(RoleName.USER)
                    .orElseGet(() -> roleRepository.save(new Role(null, RoleName.USER)));

            // Create a default admin user
            userRepository.findByUsername("admin").orElseGet(() -> {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                adminUser.setRoles(adminRoles);
                return userRepository.save(adminUser);
            });

            // create a default user
            userRepository.findByUsername("user").orElseGet(() -> {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user"));
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(userRole);
                user.setRoles(userRoles);
                return userRepository.save(user);
            });
        };
    }

}