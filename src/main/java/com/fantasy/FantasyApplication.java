package com.fantasy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fantasy.Models.Player;
import com.fantasy.Models.RealTeam;
import com.fantasy.Models.Utilizador;
import com.fantasy.Models.VirtualTeam;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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
	public CommandLineRunner demo(UserRepository repository, PlayerRepository playerRepo, VirtualTeamRepository virtualRepo, RealTeamRepository realRepo) {
		return (args) -> {
			// save a couple of Users
			repository.save(new Utilizador("Besuntas","besuntas@mail.pt","1"));
			repository.save(new Utilizador("Quim","quim@mail.pt","1"));

			RealTeam rt = new RealTeam("Benfica","a","a",1);
			realRepo.save(rt);

			RealTeam rtBD = realRepo.findByName("Benfica");

			Player p1 = new Player("Eder", "AC",90000000,rtBD);
			Player p2 = new Player("Ronaldo", "AC",90000000,rtBD);

			playerRepo.save(p1);
			playerRepo.save(p2);

			RealTeam rtBD2 = realRepo.findByName("Benfica");
			Set<Player> players = rtBD2.getPlayers();
			for(Player p: players){
				System.out.println(p.getName());
			}
			System.out.println(p2.getRealTeam().getName());
			playerRepo.save(p1);
			playerRepo.save(p2);


			VirtualTeam vt = new VirtualTeam("Patos FC",repository.findByName("Quim").get(0));
			virtualRepo.save(vt);
			
			vt.addPlayer(p1);
			vt.addPlayer(p2);
			virtualRepo.save(vt);

			Set<Player> allVTPlayers = virtualRepo.findByName("Patos FC").get(0).getPlayers();
			for(Player allp: allVTPlayers){
				System.out.println(allp.getName());
			}

			System.out.println(virtualRepo.findByName("Patos FC").get(0).getOwner().getName());

			System.out.println(repository.findByName("Quim").get(0).getTeam().getName());





			// fetch all Users
			/*
			System.out.println("Users found with findAll():");
			System.out.println("-------------------------------");
			for (Utilizador User : repository.findAll()) {
				System.out.println(User.toString());

			}for (Player player : playerRepo.findById(3)) {
				System.out.println(player.toString());
			}*/
		};
	}

}
