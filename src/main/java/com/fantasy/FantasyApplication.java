package com.fantasy;

import com.fantasy.Models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Set;

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
	public CommandLineRunner demo(UserRepository repository, PlayerRepository playerRepo, VirtualTeamRepository virtualRepo, RealTeamRepository realRepo, GameEventRepository gEventRepo, GameRepository gameRepo, JourneyRepository journeyRepo) {
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

			System.out.print(p1.getId());

			VirtualTeam vt = new VirtualTeam("Patos FC",repository.findByName("Quim"));
			virtualRepo.save(vt);

			vt.addPlayer(p1);
			vt.addPlayer(p2);
			virtualRepo.save(vt);

			Set<Player> allVTPlayers = virtualRepo.findByName("Patos FC").get(0).getPlayers();
			for(Player allp: allVTPlayers){
				System.out.println(allp.getName());
			}



			System.out.println(virtualRepo.findByName("Patos FC").get(0).getOwner().getName());

			System.out.println(repository.findByName("Quim").getTeam().getName());

			realRepo.save(new RealTeam("Porto", "a","b",1));
			RealTeam realt = realRepo.findByName("Porto");

			journeyRepo.save(new Journey(new Date(),1,1));
			Set<Journey> listaJ = journeyRepo.findByNumber(1);
			for(Journey j : listaJ){
				System.out.println("Jornada                 "+ j.getNumber());
				gameRepo.save(new Game(new Date(),rtBD2,realt,j));
			}
			Set<Game> jogos = gameRepo.findByTeam1(rtBD2);
			for(Game g:jogos){
				System.out.println(g.getTeam1().getName());
				gEventRepo.save(new GameEvent("amarelo",55,g,p1));
			}

			Set<GameEvent> gameE = gEventRepo.findByPlayer(p1);
			for(GameEvent g:gameE){
				System.out.println(g.getType());
			}



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
