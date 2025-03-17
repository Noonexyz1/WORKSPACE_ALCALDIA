package com.prototipo;

import com.prototipo.infrastructure.message.telegram.MyAmazingBot;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

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

	@Bean
	public CommandLineRunner init(MyAmazingBot myAmazingBot){
		return args -> {
			String botToken = "7903334844:AAEGWODZKlRWz4arXig9njLupzNqiB8MA4A";
			try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
				botsApplication.registerBot(botToken, myAmazingBot);
				System.out.println("MyAmazingBot successfully started!");
				Thread.currentThread().join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

}
