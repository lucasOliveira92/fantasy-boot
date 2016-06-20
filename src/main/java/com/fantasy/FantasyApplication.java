package com.fantasy;

import java.util.Arrays;

import com.fantasy.Models.Player;
import com.fantasy.Models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@EnableAutoConfiguration

public class FantasyApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(FantasyApplication.class, args);

		/*
		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}*/
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository, PlayerRepository playerRepo) {
		return (args) -> {
			// save a couple of Users
			repository.save(new User("Besuntas","besuntas@mail.pt","1"));
			repository.save(new User("Quim","quim@mail.pt","1"));

			playerRepo.save(new Player("Eder", "AC",90000000, 1));

			// fetch all Users
			System.out.println("Users found with findAll():");
			System.out.println("-------------------------------");
			for (User User : repository.findAll()) {
				System.out.println(User.toString());

			}for (Player player : playerRepo.findById(3)) {
				System.out.println(player.toString());
			}
		};
	}

}
