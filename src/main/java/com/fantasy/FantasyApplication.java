package com.fantasy;

import com.fantasy.DAO.*;
import com.fantasy.Models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.*;


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
	public CommandLineRunner demo(UserDAO repository, PlayerDAO playerRepo, VirtualTeamDAO virtualRepo, RealTeamDAO realTeamDAO, GameEventDAO gEventRepo, GameDAO gameRepo, GameWeekDAO journeyRepo) {
		return (args) -> {


			RealTeam tondela = new RealTeam ("Tondela","https://upload.wikimedia.org/wikipedia/commons/f/fc/Emblema_CD_Tondela.png","http://imgur.com/ejyF1OG");
			RealTeam sporting = new RealTeam ("Sporting CP","https://upload.wikimedia.org/wikipedia/en/3/3e/Sporting_Clube_de_Portugal.png","http://imgur.com/HV8arSm");
			RealTeam belenenses = new RealTeam ("Belenenses","http://upload.wikimedia.org/wikipedia/de/d/db/Belenenses_Lissabon.svg","http://imgur.com/CXwIhAI");
			RealTeam rioAve = new RealTeam ("rioAve","http://upload.wikimedia.org/wikipedia/de/6/63/Rio_Ave_FC.svg","http://imgur.com/cbMEWJy");
			RealTeam porto = new RealTeam ("FC Porto","http://upload.wikimedia.org/wikipedia/de/e/ed/FC_Porto_1922-2005.svg","http://imgur.com/165VABr");
			RealTeam guimaraes = new RealTeam ("Vitoria Guimarães","http://upload.wikimedia.org/wikipedia/de/8/81/Vitoria_Guimaraes.svg","http://imgur.com/VTgrrlz");
			RealTeam setubal = new RealTeam ("Vitoria Setúbal","http://upload.wikimedia.org/wikipedia/de/b/bd/Vit%C3%B3ria_Set%C3%BAbal.svg","http://imgur.com/m7ZcOFv");
			RealTeam boavista= new RealTeam ("Boavista","http://upload.wikimedia.org/wikipedia/en/4/40/Boavista_F.C._logo.svg","http://imgur.com/fRaXlEu");
			RealTeam uniaoMadeira = new RealTeam ("União da Madeira","https://upload.wikimedia.org/wikipedia/en/0/0f/UMadeira.png","http://imgur.com/jcmfTYS");
			RealTeam maritimo = new RealTeam ("Marítimo","http://upload.wikimedia.org/wikipedia/de/3/3b/Mar%C3%ADtimo_Funchal.svg","http://imgur.com/lKqbclk");
			RealTeam moreirense = new RealTeam ("Moreirense","https://upload.wikimedia.org/wikipedia/pt/8/8c/Logo_Moreirense.svg","http://imgur.com/WnmXD0z");
			RealTeam arouca = new RealTeam ("Arouca","http://upload.wikimedia.org/wikipedia/de/b/b4/FC_Arouca.png","http://imgur.com/CXwIhAI");
			RealTeam braga = new RealTeam ("SC Braga","http://upload.wikimedia.org/wikipedia/de/f/f3/Sporting_Clube_de_Braga.svg","http://imgur.com/QEKPmkl");
			RealTeam nacionalMadeira = new RealTeam ("Nacional da Madeira","http://upload.wikimedia.org/wikipedia/de/e/ee/Nacional_Funchal.svg","http://imgur.com/GyYni2b");
			RealTeam benfica = new RealTeam ("SL Benfica","http://upload.wikimedia.org/wikipedia/de/8/89/Logo_Benfica_Lissabon.svg","http://imgur.com/KMclTFb");
			RealTeam estoril = new RealTeam ("Estoril","http://upload.wikimedia.org/wikipedia/de/1/14/GD_Estoril_Praia.svg","http://imgur.com/ZpAOyVq");
			RealTeam pacos = new RealTeam ("Paços de Ferreira","http://upload.wikimedia.org/wikipedia/de/f/f2/FC_Pacos_de_Ferreira.svg","http://imgur.com/j7ehS55");
			RealTeam academica = new RealTeam ("Académica","http://upload.wikimedia.org/wikipedia/de/b/b8/Logo_Academica_de_Coimbra.svg","http://imgur.com/NQYzR3w");

			realTeamDAO.save(tondela);
			realTeamDAO.save(sporting);
			realTeamDAO.save(belenenses);
			realTeamDAO.save(rioAve);
			realTeamDAO.save(porto);
			realTeamDAO.save(guimaraes);
			realTeamDAO.save(setubal);
			realTeamDAO.save(boavista);
			realTeamDAO.save(uniaoMadeira);
			realTeamDAO.save(maritimo);
			realTeamDAO.save(moreirense);
			realTeamDAO.save(arouca);
			realTeamDAO.save(braga);
			realTeamDAO.save(nacionalMadeira);
			realTeamDAO.save(benfica);
			realTeamDAO.save(estoril);
			realTeamDAO.save(pacos);
			realTeamDAO.save(academica);


			/*HashSet playerList = new HashSet();
			HashSet playerList = new HashSet();
			HashSet playerList = new HashSet();
			HashSet playerList = new HashSet();
			HashSet playerList = new HashSet();
			HashSet playerList = new HashSet();*/
			List<Player> playerList = new ArrayList();

			playerList.add(new Player( "Lucas Souza", "MID",55,tondela));
			playerList.add(new Player( "Kaká", "DEF",52,tondela));
			playerList.add(new Player( "Eñaut Zubikarai", "GK",58,tondela));
			playerList.add(new Player( "Markus Berger", "DEF",53,tondela));
			playerList.add(new Player( "Wagner", "FOR",55,tondela));
			playerList.add(new Player( "Oto'o", "DEF",53,tondela));
			playerList.add(new Player( "Dolly Menga", "FOR",62,tondela));
			playerList.add(new Player( "Tikito", "DEF",56,tondela));
			playerList.add(new Player( "Luis Tinoco", "DEF",57,tondela));
			playerList.add(new Player( "Bruno Nascimento", "DEF",56,tondela));
			playerList.add(new Player( "Cláudio Ramos", "GK",55,tondela));
			playerList.add(new Player( "Miguel Batista", "GK",55,tondela));
			playerList.add(new Player( "Pica", "DEF",56,tondela));
			playerList.add(new Player( "Nuno Santos", "DEF",52,tondela));
			playerList.add(new Player( "Luís Alberto", "MID",52,tondela));
			playerList.add(new Player( "João Jaquité", "MID",55,tondela));
			playerList.add(new Player( "Bruno Monteiro", "MID",54,tondela));
			playerList.add(new Player( "Hélder Tavares", "MID",57,tondela));
			playerList.add(new Player( "Jhon Murillo", "FOR",55,tondela));
			playerList.add(new Player( "Romário Baldé", "FOR",55,tondela));
			playerList.add(new Player( "Piojo", "FOR",54,tondela));
			playerList.add(new Player( "Nathan Júnior", "FOR",55,tondela));
			playerList.add(new Player( "Karl", "MID",55,tondela));
			playerList.add(new Player( "Bilal Sebaihi", "MID",57,tondela));
			playerList.add(new Player( "Wanderson Lima", "DEF",58,tondela));
			playerList.add(new Player( "Érick Moreno", "FOR",60,tondela));
			playerList.add(new Player( "Ezequiel Schelotto", "DEF",70,sporting));
			playerList.add(new Player( "Alberto Aquilani", "MID",69,sporting));
			playerList.add(new Player( "Sebastián Coates", "DEF",65,sporting));
			playerList.add(new Player( "Matheus Pereira", "FOR",53,sporting));
			playerList.add(new Player( "Rui Patrício", "GK",92,sporting));
			playerList.add(new Player( "Paulo Oliveira", "DEF",72,sporting));
			playerList.add(new Player( "Tobias Figueiredo", "DEF",63,sporting));
			playerList.add(new Player( "Jefferson", "DEF",81,sporting));
			playerList.add(new Player( "Ricardo Esgaio", "DEF",65,sporting));
			playerList.add(new Player( "William Carvalho", "MID",99,sporting));
			playerList.add(new Player( "Adrien Silva", "MID",86,sporting));
			playerList.add(new Player( "João Mário", "MID",81,sporting));
			playerList.add(new Player( "André Martins", "FOR",65,sporting));
			playerList.add(new Player( "André Carrillo", "FOR",87,sporting));
			playerList.add(new Player( "Carlos Mané", "FOR",79,sporting));
			playerList.add(new Player( "Islam Slimani", "FOR",87,sporting));
			playerList.add(new Player( "Azbe Jug", "GK",56,sporting));
			playerList.add(new Player( "João Pereira", "DEF",64,sporting));
			playerList.add(new Player( "Ewerton", "DEF",71,sporting));
			playerList.add(new Player( "Marvin Zeegelaar", "DEF",60,sporting));
			playerList.add(new Player( "Naldo", "DEF",67,sporting));
			playerList.add(new Player( "Bruno Paulista", "MID",59,sporting));
			playerList.add(new Player( "Gelson Martins", "FOR",64,sporting));
			playerList.add(new Player( "Teófilo Gutiérrez", "FOR",62,sporting));
			playerList.add(new Player( "Rúben Semedo", "DEF",55,sporting));
			playerList.add(new Player( "Bruno César", "FOR",65,sporting));
			playerList.add(new Player( "Bryan Ruíz", "FOR",72,sporting));
			playerList.add(new Player( "Hernán Barcos", "FOR",69,sporting));
			playerList.add(new Player( "Abel Aguilar", "MID",64,belenenses));
			playerList.add(new Player( "Rafael Amorim", "DEF",62,belenenses));
			playerList.add(new Player( "Matt Jones", "GK",67,belenenses));
			playerList.add(new Player( "Hugo Ventura", "GK",60,belenenses));
			playerList.add(new Player( "Filipe Mendes", "GK",53,belenenses));
			playerList.add(new Player( "Palmeira", "DEF",61,belenenses));
			playerList.add(new Player( "Carlos Martins", "MID",57,belenenses));
			playerList.add(new Player( "Tiago Silva", "MID",61,belenenses));
			playerList.add(new Player( "Fábio Nunes", "FOR",61,belenenses));
			playerList.add(new Player( "Miguel Rosa", "FOR",66,belenenses));
			playerList.add(new Player( "Tiago Caeiro", "FOR",54,belenenses));
			playerList.add(new Player( "Marko Bakic", "MID",58,belenenses));
			playerList.add(new Player( "Ricardo Ribeiro", "GK",56,belenenses));
			playerList.add(new Player( "Gonçalo Brandão", "DEF",62,belenenses));
			playerList.add(new Player( "Gonçalo Silva", "DEF",56,belenenses));
			playerList.add(new Player( "Tonel", "DEF",52,belenenses));
			playerList.add(new Player( "Rafael Floro", "DEF",53,belenenses));
			playerList.add(new Player( "André Geraldes", "DEF",60,belenenses));
			playerList.add(new Player( "João Amorim", "DEF",57,belenenses));
			playerList.add(new Player( "Ricardo Dias", "MID",60,belenenses));
			playerList.add(new Player( "Rúben Pinto", "MID",67,belenenses));
			playerList.add(new Player( "André Sousa", "MID",61,belenenses));
			playerList.add(new Player( "Betinho", "FOR",53,belenenses));
			playerList.add(new Player( "Tiago Almeida", "DEF",57,belenenses));
			playerList.add(new Player( "Fábio Sturgeon", "FOR",64,belenenses));
			playerList.add(new Player( "Lucas Pugh", "FOR",53,belenenses));
			playerList.add(new Player( "Juanto Ortuño", "FOR",57,belenenses));
			playerList.add(new Player( "Héldon", "FOR",66,rioAve));
			playerList.add(new Player( "Pedrinho", "DEF",52,rioAve));
			playerList.add(new Player( "Hélder Postiga", "FOR",57,rioAve));
			playerList.add(new Player( "Edimar", "DEF",56,rioAve));
			playerList.add(new Player( "Aníbal", "DEF",62,rioAve));
			playerList.add(new Player( "Yazalde", "FOR",55,rioAve));
			playerList.add(new Player( "Cássio", "GK",54,rioAve));
			playerList.add(new Player( "Carlos Alves", "GK",53,rioAve));
			playerList.add(new Player( "Roderick Miranda", "DEF",60,rioAve));
			playerList.add(new Player( "Nélson Monte", "DEF",53,rioAve));
			playerList.add(new Player( "Lionn", "DEF",65,rioAve));
			playerList.add(new Player( "André Vilas Boas", "DEF",52,rioAve));
			playerList.add(new Player( "Alhassan Wakaso", "MID",67,rioAve));
			playerList.add(new Player( "Tarantini", "MID",64,rioAve));
			playerList.add(new Player( "Pedro Moreira", "MID",62,rioAve));
			playerList.add(new Player( "Renan Bressan", "MID",65,rioAve));
			playerList.add(new Player( "Ukra", "FOR",71,rioAve));
			playerList.add(new Player( "Guedes", "FOR",62,rioAve));
			playerList.add(new Player( "Marcelo", "DEF",67,rioAve));
			playerList.add(new Player( "Kuca", "FOR",56,rioAve));
			playerList.add(new Player( "Rui Vieira", "GK",55,rioAve));
			playerList.add(new Player( "Filip Krovinovic", "MID",53,rioAve));
			playerList.add(new Player( "João Novais", "MID",53,rioAve));
			playerList.add(new Player( "Zé Paulo", "MID",54,rioAve));
			playerList.add(new Player( "Kayembe", "FOR",58,rioAve));
			playerList.add(new Player( "Pedro Paulo", "FOR",54,rioAve));
			playerList.add(new Player( "Ronan", "FOR",60,rioAve));
			playerList.add(new Player( "Maicon", "DEF",80,porto));
			playerList.add(new Player( "Silvestre Varela", "FOR",69,porto));
			playerList.add(new Player( "Maxi Pereira", "DEF",79,porto));
			playerList.add(new Player( "Helton", "GK",52,porto));
			playerList.add(new Player( "Bruno Martins Indi", "DEF",85,porto));
			playerList.add(new Player( "Iván Marcano", "DEF",71,porto));
			playerList.add(new Player( "José Ángel", "DEF",67,porto));
			playerList.add(new Player( "Rúben Neves", "MID",70,porto));
			playerList.add(new Player( "Héctor Herrera", "MID",90,porto));
			playerList.add(new Player( "Evandro", "MID",72,porto));
			playerList.add(new Player( "Yacine Brahimi", "FOR",97,porto));
			playerList.add(new Player( "Kelvin", "FOR",66,porto));
			playerList.add(new Player( "Vincent Aboubakar", "FOR",82,porto));
			playerList.add(new Player( "Miguel Layún", "DEF",65,porto));
			playerList.add(new Player( "Iker Casillas", "GK",67,porto));
			playerList.add(new Player( "Alberto Bueno", "MID",70,porto));
			playerList.add(new Player( "José Sá", "GK",67,porto));
			playerList.add(new Player( "Danilo Pereira", "MID",72,porto));
			playerList.add(new Player( "Moussa Marega", "FOR",60,porto));
			playerList.add(new Player( "André André", "MID",72,porto));
			playerList.add(new Player( "Sérgio Oliveira", "MID",67,porto));
			playerList.add(new Player( "Hyun-Jun Suk", "FOR",66,porto));
			playerList.add(new Player( "Jesús Corona", "FOR",80,porto));
			playerList.add(new Player( "André Silva", "FOR",60,porto));
			playerList.add(new Player( "Walter", "FOR",65,porto));
			playerList.add(new Player( "João Pedro", "MID",57,guimaraes));
			playerList.add(new Player( "Henrique", "FOR",66,guimaraes));
			playerList.add(new Player( "Otávio", "MID",65,guimaraes));
			playerList.add(new Player( "Oriol Rosell", "MID",65,guimaraes));
			playerList.add(new Player( "Licá", "FOR",66,guimaraes));
			playerList.add(new Player( "Douglas Jesus", "GK",57,guimaraes));
			playerList.add(new Player( "Assis", "GK",56,guimaraes));
			playerList.add(new Player( "Josué Sá", "DEF",66,guimaraes));
			playerList.add(new Player( "João Afonso", "DEF",66,guimaraes));
			playerList.add(new Player( "Moreno", "DEF",52,guimaraes));
			playerList.add(new Player( "Breno", "DEF",60,guimaraes));
			playerList.add(new Player( "Bruno Gaspar", "DEF",60,guimaraes));
			playerList.add(new Player( "Pedro Correia", "DEF",57,guimaraes));
			playerList.add(new Player( "Cafú", "MID",61,guimaraes));
			playerList.add(new Player( "Bouba", "MID",62,guimaraes));
			playerList.add(new Player( "Bruno Alves", "MID",57,guimaraes));
			playerList.add(new Player( "Ricardo Valente", "FOR",60,guimaraes));
			playerList.add(new Player( "Paolo Hurtado", "FOR",65,guimaraes));
			playerList.add(new Player( "Tozé", "MID",67,guimaraes));
			playerList.add(new Player( "Pedrão", "DEF",55,guimaraes));
			playerList.add(new Player( "Luís Rocha", "DEF",59,guimaraes));
			playerList.add(new Player( "Santiago Montoya", "MID",60,guimaraes));
			playerList.add(new Player( "João Vigário", "FOR",54,guimaraes));
			playerList.add(new Player( "João Teixeira", "MID",60,guimaraes));
			playerList.add(new Player( "Victor Andrade", "FOR",60,guimaraes));
			playerList.add(new Player( "Miguel Silva", "GK",53,guimaraes));
			playerList.add(new Player( "Dalbert", "DEF",54,guimaraes));
			playerList.add(new Player( "Falaye Sacko", "MID",55,guimaraes));
			playerList.add(new Player( "Thibang Phete", "MID",54,guimaraes));
			playerList.add(new Player( "Xande Silva", "FOR",53,guimaraes));
			playerList.add(new Player( "Francis", "FOR",61,guimaraes));


			for (Player p: playerList){
				playerRepo.save(p);
			}

			// save a couple of Users
			/*repository.save(new User("Besuntas","besuntas@mail.pt","1"));
			repository.save(new User("Quim","quim@mail.pt","1"));

			RealTeam rt = new RealTeam("Benfica","a","a");
			realRepo.save(rt);

			RealTeam rtBD = realRepo.findByName("Benfica");

			Player p1 = new Player("Eder", "AC",90000000,rtBD);
			Player p2 = new Player("Ronaldo", "AC",90000000,rtBD);
			Player p3 = new Player("Andre", "AC",90000000);
			Player p4 = new Player("Pepão", "DEF",90000000);
			Player p5 = new Player("Manquinho", "MID",90000000);

			playerRepo.save(p1);
			playerRepo.save(p2);
			playerRepo.save(p3);
			playerRepo.save(p4);
			playerRepo.save(p5);

			RealTeam rtBD2 = realRepo.findByName("Benfica");
			Set<Player> players = rtBD2.getPlayers();
			for(Player p: players){
				System.out.println(p.getName());
			}
			System.out.println(p2.getRealTeam().getName());
			playerRepo.save(p1);
			playerRepo.save(p2);

			System.out.print(p1.getId());

			VirtualTeam vt = new VirtualTeam("Patos FC",repository.findByUsername("Quim"));
			virtualRepo.save(vt);

			vt.addPlayer(p1);
			vt.addPlayer(p2);
			virtualRepo.save(vt);

			Set<Player> allVTPlayers = virtualRepo.findByName("Patos FC").get(0).getPlayers();
			for(Player allp: allVTPlayers){
				System.out.println(allp.getName());
			}



			System.out.println(virtualRepo.findByName("Patos FC").get(0).getOwner().getUsername());

			System.out.println(repository.findByUsername("Quim").getTeam().getName());

			realRepo.save(new RealTeam("Porto", "a","b"));
			RealTeam realt = realRepo.findByName("Porto");

			journeyRepo.save(new GameWeek(new Date(),1));
			Set<GameWeek> listaJ = journeyRepo.findByNumber(1);
			for(GameWeek j : listaJ){
				System.out.println("GameWeek                "+ j.getNumber());
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
			for (User User : repository.findAll()) {
				System.out.println(User.toString());

			}for (Player player : playerRepo.findById(3)) {
				System.out.println(player.toString());
			}*/
		};
	}

}
