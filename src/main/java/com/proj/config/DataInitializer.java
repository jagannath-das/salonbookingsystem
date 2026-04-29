package com.proj.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.proj.model.User;
import com.proj.repository.Userrepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createAdmin(
            Userrepository repo,
            PasswordEncoder passwordEncoder) {

        return (args) -> {

            // check admin exists
            if (repo.findByEmailid("admin@gmail.com") == null) {

                User admin = new User();

                admin.setFullname("Admin");
                admin.setEmailid("admin@gmail.com");
                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );
                admin.setRole(new ArrayList<>(List.of("ROLE_ADMIN")));
                admin.setPhone("9999999999");

                repo.save(admin);

                System.out.println("Default Admin Created");
            }
        };
    }
}
