package com.prototipo;

import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
public class PrototipoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrototipoApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner init(
			CredencialRepository credencialRepository,
			PasswordEncoder passwordEncoder) {

		return args -> {
			credencialRepository.findAll()
					.forEach(x -> {
						x.setPass(passwordEncoder.encode(x.getPass()));
						credencialRepository.save(x);
                    });
		};
	}*/

}
