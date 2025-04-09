package com.tescaro.financial.config;

import com.tescaro.financial.model.User;
import com.tescaro.financial.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner initDatabase(UserRepository userRepository) {
    return args -> {
      if (userRepository.count() == 0) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("admin123"));
        admin.setEmail("admin@example.com");

        userRepository.save(admin);
        System.out.println("Usuário administrador criado: admin/admin123");
      } else {
        System.out.println("Usuário administrador já existe. Nenhuma ação necessária.");
      }
    };
  }
}
