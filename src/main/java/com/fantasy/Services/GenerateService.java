package com.fantasy.Services;

import com.fantasy.DAO.GameDAO;
import com.fantasy.DAO.GameEventDAO;
import com.fantasy.DAO.GameWeekDAO;
import com.fantasy.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Lucas on 26-Jun-16.
 */
@Service
public class GenerateService {

    @Autowired
    GameWeekDAO gameWeekDAO;
    @Autowired
    GameEventDAO gameEventDAO;
    @Autowired
    GameDAO gameDAO;


    @Transactional
    public void generate(int gameWeek){
        GameWeek gw = gameWeekDAO.findByNumber(gameWeek);

        for (Game g: gw.getGames()){
            gameEventDAO.deleteByGameId(g.getId());

            // -----------------------------  Determinar probabilidade de golo --------------------------------- //
            int chanceTeam1 = 0;
            int chanceTeam2 = 0;

            int golosEquipa1 = 0;
            int golosEquipa2 = 0; 
            int golosEquipa1Fixed = 0;
            int golosEquipa2Fixed = 0;

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
                    golosEquipa1Fixed++;
                }
                randomInt = r.nextInt(100) + 1;
                if(randomInt <= chanceTeam2){
                    golosEquipa2++;
                    golosEquipa2Fixed++;
                }
            }

            //Determinar que posição marcou o golo

            List<GameEvent> eventosJogo = new ArrayList<>();
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


            //-------------------------------------------- GERAR VERMELHOS ---------------------------------- //
            for (int i =0; i < 8; i++){
                Random r = new SecureRandom();
                Player expulso;
                int randomRed = r.nextInt(100) + 1;
                if(randomRed <= 5){
                    int randomInt = r.nextInt(100) + 1;
                    if(randomInt > 40){
                        int randomPlayerIndex = r.nextInt(team1DEF.size());
                        expulso = team1DEF.get(randomPlayerIndex);
                        team1DEF.remove(randomPlayerIndex);
                    }
                    else if(randomInt > 10 && randomInt <=40){
                        int randomPlayerIndex = r.nextInt(team1MID.size());
                        expulso = team1MID.get(randomPlayerIndex);
                        team1MID.remove(randomPlayerIndex);
                    }
                    else{
                        int randomPlayerIndex = r.nextInt(team1FOR.size());
                        expulso = team1FOR.get(randomPlayerIndex);
                        team1FOR.remove(randomPlayerIndex);
                    }
                    GameEvent gameEvent = new GameEvent("RED CARD",r.nextInt(90) + 1,g,expulso);
                    eventosJogo.add(gameEvent);
                }
            }
            for (int i =0; i < 8; i++){
                Random r = new SecureRandom();
                Player expulso;
                int randomRed = r.nextInt(100) + 1;
                if(randomRed <= 5){
                    int randomInt = r.nextInt(100) + 1;
                    if(randomInt <=10){
                        int randomPlayerIndex = r.nextInt(team2DEF.size());
                        expulso = team2DEF.get(randomPlayerIndex);;
                        team2DEF.remove(randomPlayerIndex);
                    }
                    else if(randomInt > 10 && randomInt <=40){
                        int randomPlayerIndex = r.nextInt(team2MID.size());
                        expulso = team2MID.get(randomPlayerIndex);
                        team2MID.remove(randomPlayerIndex);
                    }
                    else{
                        int randomPlayerIndex = r.nextInt(team2FOR.size());
                        expulso = team2FOR.get(randomPlayerIndex);
                        team2FOR.remove(randomPlayerIndex);
                    }
                    GameEvent gameEvent = new GameEvent("RED CARD",r.nextInt(90) + 1,g,expulso);
                    eventosJogo.add(gameEvent);
                }
            }

            //-~------------------------------------------ GERAR GOLOS ---------------------------------- //
            while(golosEquipa1 > 0){
                Random r = new SecureRandom();
                Player marcador;
                int randomInt = r.nextInt(100) + 1;
                if(randomInt <=10){
                    int randomPlayerIndex = r.nextInt(team1DEF.size());
                    marcador = team1DEF.get(randomPlayerIndex);
                }
                else if(randomInt > 10 && randomInt <=40){
                    int randomPlayerIndex = r.nextInt(team1MID.size());
                    marcador = team1MID.get(randomPlayerIndex);
                }
                else{
                    int randomPlayerIndex = r.nextInt(team1FOR.size());
                    marcador = team1FOR.get(randomPlayerIndex);
                }
                golosEquipa1--;
                GameEvent gameEvent = new GameEvent("GOAL",r.nextInt(90) + 1,g,marcador);
                eventosJogo.add(gameEvent);
            }

            while(golosEquipa2 > 0){
                Random r = new SecureRandom();
                Player marcador;
                int randomInt = r.nextInt(100) + 1;
                if(randomInt <=10){
                    int randomPlayerIndex = r.nextInt(team2DEF.size());
                    marcador = team2DEF.get(randomPlayerIndex);
                }
                else if(randomInt > 10 && randomInt <=40){
                    int randomPlayerIndex = r.nextInt(team2MID.size());
                    marcador = team2MID.get(randomPlayerIndex);
                }
                else{
                    int randomPlayerIndex = r.nextInt(team2FOR.size());
                    marcador = team2FOR.get(randomPlayerIndex);
                }
                golosEquipa2--;
                GameEvent gameEvent = new GameEvent("GOAL",r.nextInt(90) + 1,g,marcador);
                eventosJogo.add(gameEvent);
            }

            //-------------------------------------------- GERAR AMARELOS ---------------------------------- //
            for (int i =0; i < 4; i++){
                Random r = new SecureRandom();
                Player amarelado;
                int randomYellow = r.nextInt(100) + 1;
                if(randomYellow <= 40){
                    int randomInt = r.nextInt(100) + 1;
                    if(randomInt > 40){
                        int randomPlayerIndex = r.nextInt(team1DEF.size());
                        amarelado = team1DEF.get(randomPlayerIndex);
                        team1DEF.remove(randomPlayerIndex);
                    }
                    else if(randomInt > 10 && randomInt <=40){
                        int randomPlayerIndex = r.nextInt(team1MID.size());
                        amarelado = team1MID.get(randomPlayerIndex);
                        team1MID.remove(randomPlayerIndex);
                    }
                    else{
                        int randomPlayerIndex = r.nextInt(team1FOR.size());
                        amarelado = team1FOR.get(randomPlayerIndex);
                        team1FOR.remove(randomPlayerIndex);
                    }
                    GameEvent gameEvent = new GameEvent("YELLOW CARD",r.nextInt(90) + 1,g,amarelado);
                    eventosJogo.add(gameEvent);
                }
            }
            for (int i =0; i < 4; i++){
                Random r = new SecureRandom();
                Player amarelado;
                int randomYellow = r.nextInt(100) + 1;
                if(randomYellow <= 40){
                    int randomInt = r.nextInt(100) + 1;
                    if(randomInt  > 40){
                        int randomPlayerIndex = r.nextInt(team2DEF.size());
                        amarelado = team2DEF.get(randomPlayerIndex);
                        team2DEF.remove(randomPlayerIndex);
                    }
                    else if(randomInt > 10 && randomInt <=40){
                        int randomPlayerIndex = r.nextInt(team2MID.size());
                        amarelado = team2MID.get(randomPlayerIndex);
                        team2MID.remove(randomPlayerIndex);
                    }
                    else{
                        int randomPlayerIndex = r.nextInt(team2FOR.size());
                        amarelado = team2FOR.get(randomPlayerIndex);
                        team2FOR.remove(randomPlayerIndex);
                    }
                    GameEvent gameEvent = new GameEvent("YELLOW CARD",r.nextInt(90) + 1,g,amarelado);
                    eventosJogo.add(gameEvent);
                }
            }

            //------------------------------------------- GERAR EVENTOS DE RESULTADO ------------------------ //

            for(Player p: playersTeam1){
                if(golosEquipa1Fixed > golosEquipa2Fixed){
                    GameEvent gameEvent = new GameEvent("WIN",-1,g,p);
                    eventosJogo.add(gameEvent);
                }
                else if(golosEquipa1Fixed < golosEquipa2Fixed){
                    GameEvent gameEvent = new GameEvent("LOSE",-1,g,p);
                    eventosJogo.add(gameEvent);
                }
                else{
                    GameEvent gameEvent = new GameEvent("DRAW",-1,g,p);
                    eventosJogo.add(gameEvent);
                }
            }

            for(Player p: playersTeam2){
                if(golosEquipa1Fixed > golosEquipa2Fixed){
                    GameEvent gameEvent = new GameEvent("LOSE",-1,g,p);
                    eventosJogo.add(gameEvent);
                }
                else if(golosEquipa1Fixed < golosEquipa2Fixed){
                    GameEvent gameEvent = new GameEvent("WIN",-1,g,p);
                    eventosJogo.add(gameEvent);
                }
                else{
                    GameEvent gameEvent = new GameEvent("DRAW",-1,g,p);
                    eventosJogo.add(gameEvent);
                }
            }




            for(GameEvent e: eventosJogo){
                System.out.println(e.getMinute()+ " - " + e.getType() + " - " + e.getPlayer().getRealTeam().getName() + " - " + e.getPlayer().getName() );
                gameEventDAO.save(e);
            }
            g.setTeam1_score(golosEquipa1Fixed);
            g.setTeam2_score(golosEquipa2Fixed);
            gameDAO.save(g);



            System.out.println("----------------------------------------------------");
            System.out.println("----------------------------------------------------");
        }
    }
}
