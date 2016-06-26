package com.fantasy.Controllers;

import com.fantasy.DAO.GameWeekDAO;
import com.fantasy.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Controller
public class HelloController {

    @Autowired
    GameWeekDAO gameWeekDAO;

    @RequestMapping("/")
    public String index() {
        return "home";
    }
    
    //@Secured("ROLE_USER")
    @RequestMapping("/home")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }
    @RequestMapping("/generate")
    public String generate(){
        GameWeek gw = gameWeekDAO.findByNumber(1);
        
        for (Game g: gw.getGames()){

            // -----------------------------  Determinar probabilidade de golo --------------------------------- //
            int chanceTeam1 = 0;
            int chanceTeam2 = 0;

            int golosEquipa1 = 0;
            int golosEquipa2 = 0;

            RealTeam team1 = g.getTeam1();
            RealTeam team2 = g.getTeam2();

            //SE OS JOGOS NAO ENVOLVEREM OS 3 GRANDES + BRAGA
            if(!team1.getName().contains("Sporting") && !team1.getName().contains("Benfica") && !team1.getName().contains("Braga") && !team1.getName().contains("Porto")){
                if(!team2.getName().contains("Sporting") && !team2.getName().contains("Benfica") && !team2.getName().contains("Braga") && !team2.getName().contains("Porto")){
                    chanceTeam1 = 25;
                    chanceTeam2 = 20;
                    System.out.println("NAO ENVOLVE GRANDE");
                }
            }

            //SE OS JOGOS SAO ENTRE AS EQUIPAS GRANDES + BRAGA
            if(team1.getName().contains("Sporting") || team1.getName().contains("Benfica")|| team1.getName().contains("Braga") || team1.getName().contains("Porto")){
                if(team2.getName().contains("Sporting") || team2.getName().contains("Benfica") || team2.getName().contains("Braga") || team2.getName().contains("Porto")){
                    chanceTeam1 = 20;
                    chanceTeam2 = 15;
                    System.out.println("JOGO ENTRE GRANDES");
                }

            }

            //SE O JOGO ENVOLVE 1 GRANDE + BRAGA A JOGAR EM CASA
            if(team1.getName().contains("Sporting") || team1.getName().contains("Benfica")|| team1.getName().contains("Braga") || team1.getName().contains("Porto")){
                chanceTeam1 = 35;
                chanceTeam2 = 10;
                System.out.println("GRNDE JOGA EM CASA");
            }
            //SE O JOGO ENVOLVE 1 GRANDE + BRAGA A JOGAR FORA
            if(team2.getName().contains("Sporting") || team2.getName().contains("Benfica")|| team2.getName().contains("Braga") || team2.getName().contains("Porto")){
                chanceTeam1 = 15;
                chanceTeam2 = 25;
                System.out.println("GRNDE JOGA FORA");
            }

            //Gerar Golos
            System.out.println(g.getTeam1().getName() + " VS " + g.getTeam2().getName());
            for (int i = 0; i<5; i++){
                Random r = new Random();
                int randomInt = r.nextInt(100) + 1;
                if(randomInt <= chanceTeam1){
                    golosEquipa1++;
                }
                randomInt = r.nextInt(100) + 1;
                if(randomInt <= chanceTeam2){
                    golosEquipa2++;
                }
            }

            //Determinar que posição marcou o golo

            List<GameEvent> eventosJogo = new ArrayList<>();
            List<Player> marcadoresTeam1 = new ArrayList<>();
            List<Player> marcadoresTeam2 = new ArrayList<>();
            List<Player> amarelos = new ArrayList<>();
            List<Player> vermelhos = new ArrayList<>();
            Set<Player> playersTeam1 = team1.getPlayers();
            Set<Player> playersTeam2 = team2.getPlayers();
            List<Player> team1DEF = new ArrayList<>();
            List<Player> team1MID = new ArrayList<>();
            List<Player> team1FOR = new ArrayList<>();

            for(Player p: playersTeam1){
                switch(p.getPosition()){
                    case "DEF": team1DEF.add(p);break;
                    case "MID": team1MID.add(p);break;
                    case "FOR": team1FOR.add(p);break;
                }
            }
            List<Player> team2DEF = new ArrayList<>();
            List<Player> team2MID = new ArrayList<>();
            List<Player> team2FOR = new ArrayList<>();
            for(Player p: playersTeam2){
                switch(p.getPosition()){
                    case "DEF": team2DEF.add(p);break;
                    case "MID": team2MID.add(p);break;
                    case "FOR": team2FOR.add(p);break;
                }
            }

            //-~------------------------------------------ GERAR GOLOS ---------------------------------- //
            while(golosEquipa1 > 0){
                Random r = new Random();
                Player marcador;
                int randomInt = r.nextInt(100) + 1;
                if(randomInt <=10){
                    int randomPlayerIndex = r.nextInt(team1DEF.size());
                    marcadoresTeam1.add(team1DEF.get(randomPlayerIndex));
                    marcador = team1DEF.get(randomPlayerIndex);
                }
                else if(randomInt > 10 && randomInt <=40){
                    int randomPlayerIndex = r.nextInt(team1MID.size());
                    marcadoresTeam1.add(team1MID.get(randomPlayerIndex));
                    marcador = team1MID.get(randomPlayerIndex);
                }
                else{
                    int randomPlayerIndex = r.nextInt(team1FOR.size());
                    marcadoresTeam1.add(team1FOR.get(randomPlayerIndex));
                    marcador = team1FOR.get(randomPlayerIndex);
                }
                golosEquipa1--;
                GameEvent gameEvent = new GameEvent("GOAL",r.nextInt(90) + 1,g,marcador);
                eventosJogo.add(gameEvent);
            }

            while(golosEquipa2 > 0){
                Random r = new Random();
                int randomInt = r.nextInt(100) + 1;
                if(randomInt <=10){
                    int randomPlayerIndex = r.nextInt(team2DEF.size());
                    marcadoresTeam2.add(team2DEF.get(randomPlayerIndex));
                }
                else if(randomInt > 10 && randomInt <=40){
                    int randomPlayerIndex = r.nextInt(team2MID.size());
                    marcadoresTeam2.add(team2MID.get(randomPlayerIndex));
                }
                else{
                    int randomPlayerIndex = r.nextInt(team2FOR.size());
                    marcadoresTeam2.add(team2FOR.get(randomPlayerIndex));
                }
                golosEquipa2--;
            }

            for(GameEvent e: eventosJogo){
                System.out.println(e.getMinute() + " - " + e.getPlayer().getName() + " - " + e.getType());
            }
            for(Player p: marcadoresTeam2){
                System.out.println(p.getName());
            }

            //-~------------------------------------------ GERAR AMARELOS ---------------------------------- //
            for (int i =0; i < 10; i++){
                Random r = new Random();
                int randomYellow = r.nextInt(100) + 1;
                if(randomYellow <= 20){
                    int randomInt = r.nextInt(100) + 1;
                    if(randomInt <=10){
                        int randomPlayerIndex = r.nextInt(team1DEF.size());
                        amarelos.add(team1DEF.get(randomPlayerIndex));
                    }
                    else if(randomInt > 10 && randomInt <=40){
                        int randomPlayerIndex = r.nextInt(team1MID.size());
                        amarelos.add(team1MID.get(randomPlayerIndex));
                    }
                    else{
                        int randomPlayerIndex = r.nextInt(team1FOR.size());
                        amarelos.add(team1FOR.get(randomPlayerIndex));
                    }
                }
            }
            for (int i =0; i < 10; i++){
                Random r = new Random();
                int randomYellow = r.nextInt(100) + 1;
                if(randomYellow <= 20){
                    int randomInt = r.nextInt(100) + 1;
                    if(randomInt <=10){
                        int randomPlayerIndex = r.nextInt(team2DEF.size());
                        amarelos.add(team2DEF.get(randomPlayerIndex));
                    }
                    else if(randomInt > 10 && randomInt <=40){
                        int randomPlayerIndex = r.nextInt(team2MID.size());
                        amarelos.add(team2MID.get(randomPlayerIndex));
                    }
                    else{
                        int randomPlayerIndex = r.nextInt(team2FOR.size());
                        amarelos.add(team2FOR.get(randomPlayerIndex));
                    }
                }
            }

            for (Player p: amarelos){
                System.out.println("AMARELO - " + p.getName());
            }


            System.out.println("----------------------------------------------------");
            System.out.println("----------------------------------------------------");

            //Gravar resultado, eventos
        }
        return "home";
    }

}