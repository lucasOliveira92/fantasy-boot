package com.fantasy.Services;

import com.fantasy.DAO.*;
import com.fantasy.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class GenerateService {

    @Autowired
    GameWeekDAO gameWeekDAO;
    @Autowired
    GameEventDAO gameEventDAO;
    @Autowired
    GameDAO gameDAO;
    @Autowired
    RealTeamDAO realTeamDAO;
    @Autowired
    PlayerDAO playerRepo;
    @Autowired
    UserDAO userDAO;
    @Autowired
    VirtualTeamDAO virtualTeamDAO;
    @Autowired
    GameWeekSnapshotDAO snapDAO;


    @Transactional
    public void generate(int gameWeek){
        GameWeek gw = gameWeekDAO.findByNumber(gameWeek);
        HashMap<String,GameWeekSnapshot> snaps = genererateRandomSnapshots(gw);

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

    @Transactional
    public void populateRealTeamsPlayers() throws Exception{


        RealTeam tondela = new RealTeam ("Tondela","https://upload.wikimedia.org/wikipedia/commons/f/fc/Emblema_CD_Tondela.png","http://imgur.com/ejyF1OG");
        RealTeam sporting = new RealTeam ("Sporting CP","https://upload.wikimedia.org/wikipedia/en/3/3e/Sporting_Clube_de_Portugal.png","http://imgur.com/HV8arSm");
        RealTeam belenenses = new RealTeam ("Belenenses","http://upload.wikimedia.org/wikipedia/de/d/db/Belenenses_Lissabon.svg","http://imgur.com/CXwIhAI");
        RealTeam rioAve = new RealTeam ("rioAve","http://upload.wikimedia.org/wikipedia/de/6/63/Rio_Ave_FC.svg","http://imgur.com/cbMEWJy");
        RealTeam porto = new RealTeam ("FC Porto","http://upload.wikimedia.org/wikipedia/de/e/ed/FC_Porto_1922-2005.svg","http://imgur.com/165VABr");
        RealTeam guimaraes = new RealTeam ("Vitoria Guimarães","http://upload.wikimedia.org/wikipedia/de/8/81/Vitoria_Guimaraes.svg","http://imgur.com/VTgrrlz");
        RealTeam setubal = new RealTeam ("Vitoria Setúbal","http://upload.wikimedia.org/wikipedia/de/b/bd/Vit%C3%B3ria_Set%C3%BAbal.svg","http://imgur.com/m7ZcOFv");
        RealTeam boavista= new RealTeam ("Boavista","http://upload.wikimedia.org/wikipedia/en/4/40/Boavista_F.C._logo.svg","http://imgur.com/fRaXlEu");
        RealTeam uniaoMadeira = new RealTeam ("União da Madeira","https://upload.wikimedia.org/wikipedia/en/0/0f/UMadeira.png","http://imgur.com/jcmfTYS");
        RealTeam maritimo = new RealTeam ("Marítimo","http://upload.wikimedia.org/wikipedia/de/3/3b/Mar%C3%ADtimo_nacionalMadeira.svg","http://imgur.com/lKqbclk");
        RealTeam moreirense = new RealTeam ("Moreirense","https://upload.wikimedia.org/wikipedia/pt/8/8c/Logo_Moreirense.svg","http://imgur.com/WnmXD0z");
        RealTeam arouca = new RealTeam ("Arouca","http://upload.wikimedia.org/wikipedia/de/b/b4/FC_Arouca.png","http://imgur.com/CXwIhAI");
        RealTeam braga = new RealTeam ("SC Braga","http://upload.wikimedia.org/wikipedia/de/f/f3/Sporting_Clube_de_Braga.svg","http://imgur.com/QEKPmkl");
        RealTeam nacionalMadeira = new RealTeam ("Nacional da Madeira","http://upload.wikimedia.org/wikipedia/de/e/ee/Nacional_nacionalMadeira.svg","http://imgur.com/GyYni2b");
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

        List<Player> playerList = new ArrayList();
        List<Player> playerList2 = new ArrayList();
        List<Player> playerList3 = new ArrayList();


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


        playerList2.add(new Player( "Ricardo Nunes", "GK",52,setubal));
        playerList2.add(new Player( "Diego", "GK",52,setubal));
        playerList2.add(new Player( "Salim Cissé", "FOR",62,setubal));
        playerList2.add(new Player( "Lukas Raeder", "GK",54,setubal));
        playerList2.add(new Player( "Miguel Lázaro", "GK",53,setubal));
        playerList2.add(new Player( "Frederico Venâncio", "DEF",60,setubal));
        playerList2.add(new Player( "Miguel Lourenco", "DEF",56,setubal));
        playerList2.add(new Player( "Tiago Terroso", "MID",56,setubal));
        playerList2.add(new Player( "Dani", "MID",54,setubal));
        playerList2.add(new Player( "André Horta", "MID",54,setubal));
        playerList2.add(new Player( "Paulo Tavares", "MID",62,setubal));
        playerList2.add(new Player( "Tiago Valente", "DEF",53,setubal));
        playerList2.add(new Player( "William Alves", "DEF",55,setubal));
        playerList2.add(new Player( "Cléber", "DEF",55,setubal));
        playerList2.add(new Player( "Nuno Pinto", "DEF",55,setubal));
        playerList2.add(new Player( "Toni Gorupec", "DEF",55,setubal));
        playerList2.add(new Player( "Hamdou Mohamed Elnouni", "MID",53,setubal));
        playerList2.add(new Player( "Mohamed El Gadi", "MID",55,setubal));
        playerList2.add(new Player( "Cristian Tissone", "DEF",57,setubal));
        playerList2.add(new Player( "Fábio Pacheco", "MID",56,setubal));
        playerList2.add(new Player( "Alexandre Cardoso", "MID",57,setubal));
        playerList2.add(new Player( "Costinha", "FOR",57,setubal));
        playerList2.add(new Player( "Ruca", "DEF",55,setubal));
        playerList2.add(new Player( "André Claro", "FOR",61,setubal));
        playerList2.add(new Player( "Vasco Costa", "FOR",56,setubal));
        playerList2.add(new Player( "Arnold", "FOR",55,setubal));
        playerList2.add(new Player( "Abdulaziz Al-Mansour", "DEF",61,setubal));
        playerList2.add(new Player( "Maciej Makuszewski", "FOR",60,setubal));
        playerList2.add(new Player( "Meyong", "FOR",54,setubal));
        playerList2.add(new Player( "Mohcine Hassan", "FOR",54,setubal));
        playerList2.add(new Player( "Luisinho", "FOR",62,boavista));
        playerList2.add(new Player( "Mika", "GK",61,boavista));
        playerList2.add(new Player( "Mamadou Ba", "GK",52,boavista));
        playerList2.add(new Player( "Fábio Ervões", "DEF",55,boavista));
        playerList2.add(new Player( "Carlos Santos", "DEF",60,boavista));
        playerList2.add(new Player( "Afonso", "DEF",57,boavista));
        playerList2.add(new Player( "Anderson Correia", "DEF",56,boavista));
        playerList2.add(new Player( "Reuben Gabriel", "MID",56,boavista));
        playerList2.add(new Player( "Idris Mandiang", "MID",54,boavista));
        playerList2.add(new Player( "Tengarrinha", "MID",62,boavista));
        playerList2.add(new Player( "Samu", "MID",53,boavista));
        playerList2.add(new Player( "Anderson Carvalho", "MID",55,boavista));
        playerList2.add(new Player( "Zé Manuel", "FOR",61,boavista));
        playerList2.add(new Player( "Michael Uchebo", "FOR",60,boavista));
        playerList2.add(new Player( "Gideão", "GK",57,boavista));
        playerList2.add(new Player( "Paulo Vinícius", "DEF",64,boavista));
        playerList2.add(new Player( "Philipe Sampaio", "DEF",58,boavista));
        playerList2.add(new Player( "Nuno Henrique", "DEF",56,boavista));
        playerList2.add(new Player( "Emmanuel Hackman", "DEF",54,boavista));
        playerList2.add(new Player( "Tiago Mesquita", "DEF",57,boavista));
        playerList2.add(new Player( "Ancelmo Júnior", "MID",57,boavista));
        playerList2.add(new Player( "Fábio Lopes", "MID",56,boavista));
        playerList2.add(new Player( "André Bukia", "MID",53,boavista));
        playerList2.add(new Player( "Miguel Pereira", "FOR",54,boavista));
        playerList2.add(new Player( "Uche Nwofor", "FOR",57,boavista));
        playerList2.add(new Player( "Ian Pereira", "DEF",59,boavista));
        playerList2.add(new Player( "Aymen Tahar", "MID",55,boavista));
        playerList2.add(new Player( "Mario Martínez", "MID",52,boavista));
        playerList2.add(new Player( "Rúben Ribeiro", "MID",57,boavista));
        playerList2.add(new Player( "Renato Santos", "FOR",56,boavista));
        playerList2.add(new Player( "Yoro Ly", "FOR",57,boavista));
        playerList2.add(new Player( "Ibrahima", "FOR",55,boavista));
        playerList2.add(new Player( "Cristian Cangá", "FOR",61,boavista));
        playerList2.add(new Player( "Douglas Abner", "FOR",59,boavista));
        playerList2.add(new Player( "Imanol Iriberri", "FOR",60,boavista));
        playerList2.add(new Player( "André Moreira", "GK",54,uniaoMadeira));
        playerList2.add(new Player( "Rafael Alves", "GK",54,uniaoMadeira));
        playerList2.add(new Player( "Ricardo Campos", "GK",54,uniaoMadeira));
        playerList2.add(new Player( "Diego Galo", "DEF",54,uniaoMadeira));
        playerList2.add(new Player( "Tiago Ferreira", "DEF",54,uniaoMadeira));
        playerList2.add(new Player( "Edson Almeida", "DEF",53,uniaoMadeira));
        playerList2.add(new Player( "Paulo Monteiro", "DEF",53,uniaoMadeira));
        playerList2.add(new Player( "Joãozinho", "DEF",55,uniaoMadeira));
        playerList2.add(new Player( "Nilson", "DEF",55,uniaoMadeira));
        playerList2.add(new Player( "Carlos Manuel", "DEF",57,uniaoMadeira));
        playerList2.add(new Player( "William Soares", "MID",56,uniaoMadeira));
        playerList2.add(new Player( "Wanderson Miranda", "MID",56,uniaoMadeira));
        playerList2.add(new Player( "Rúben Andrade", "MID",54,uniaoMadeira));
        playerList2.add(new Player( "Abdullahi Shehu", "MID",55,uniaoMadeira));
        playerList2.add(new Player( "Gian Martins", "MID",55,uniaoMadeira));
        playerList2.add(new Player( "Breitner", "MID",56,uniaoMadeira));
        playerList2.add(new Player( "Danilo Dias", "FOR",57,uniaoMadeira));
        playerList2.add(new Player( "Élio Martins", "FOR",53,uniaoMadeira));
        playerList2.add(new Player( "Kisley", "FOR",55,uniaoMadeira));
        playerList2.add(new Player( "Amilton", "FOR",56,uniaoMadeira));
        playerList2.add(new Player( "Miguel Fidalgo", "FOR",54,uniaoMadeira));
        playerList2.add(new Player( "Jhonder Cádiz", "FOR",54,uniaoMadeira));
        playerList2.add(new Player( "Edder Farías", "FOR",57,uniaoMadeira));
        playerList2.add(new Player( "Genséric Kusunga", "DEF",55,uniaoMadeira));
        playerList2.add(new Player( "Rúben Lima", "DEF",57,uniaoMadeira));
        playerList2.add(new Player( "Marco Túlio", "MID",60,uniaoMadeira));
        playerList2.add(new Player( "Miguel Cardoso", "MID",58,uniaoMadeira));
        playerList2.add(new Player( "Toni Silva", "FOR",60,uniaoMadeira));
        playerList2.add(new Player( "Gevorg Ghazaryan", "FOR",62,maritimo));
        playerList2.add(new Player( "Babá", "FOR",61,maritimo));
        playerList2.add(new Player( "Romain Salin", "GK",64,maritimo));
        playerList2.add(new Player( "Rúben", "DEF",66,maritimo));
        playerList2.add(new Player( "João Diogo", "DEF",61,maritimo));
        playerList2.add(new Player( "Briguel", "DEF",54,maritimo));
        playerList2.add(new Player( "Fransérgio", "MID",62,maritimo));
        playerList2.add(new Player( "Éber Bessa", "MID",55,maritimo));
        playerList2.add(new Player( "Fernando Ferreira", "MID",61,maritimo));
        playerList2.add(new Player( "Alex Soares", "MID",60,maritimo));
        playerList2.add(new Player( "António Xavier", "FOR",62,maritimo));
        playerList2.add(new Player( "Edgar Costa", "FOR",62,maritimo));
        playerList2.add(new Player( "Dyego Sousa", "FOR",57,maritimo));
        playerList2.add(new Player( "Ulysse Diallo", "FOR",55,maritimo));
        playerList2.add(new Player( "Tiago Rodrigues", "MID",65,maritimo));
        playerList2.add(new Player( "Alireza Haghighi", "GK",56,maritimo));
        playerList2.add(new Player( "Wellington", "GK",57,maritimo));
        playerList2.add(new Player( "Deyvison", "DEF",56,maritimo));
        playerList2.add(new Player( "Dirceu", "DEF",55,maritimo));
        playerList2.add(new Player( "Diney Borges", "DEF",53,maritimo));
        playerList2.add(new Player( "Patrick", "DEF",55,maritimo));
        playerList2.add(new Player( "Barata", "MID",53,maritimo));
        playerList2.add(new Player( "Pana", "MID",57,maritimo));
        playerList2.add(new Player( "Lynneeker", "FOR",55,maritimo));
        playerList2.add(new Player( "Damien Plessis", "MID",57,maritimo));
        playerList2.add(new Player( "Gevaro Nepomuceno", "FOR",56,maritimo));
        playerList2.add(new Player( "Donald Djousse", "FOR",56,maritimo));
        playerList2.add(new Player( "Rafael Martins", "FOR",60,moreirense));
        playerList2.add(new Player( "Marcelo Oliveira M. Oliveira", "DEF",53,moreirense));
        playerList2.add(new Player( "Danielson Danielson", "DEF",53,moreirense));
        playerList2.add(new Player( "João Sousa João Sousa", "DEF",55,moreirense));
        playerList2.add(new Player( "André Marques A. Marques", "DEF",57,moreirense));
        playerList2.add(new Player( "Coronas Coronas", "DEF",57,moreirense));
        playerList2.add(new Player( "Patrick Andrade Patrick Andrade", "MID",56,moreirense));
        playerList2.add(new Player( "Caleb Gomina C. Gomina", "FOR",53,moreirense));
        playerList2.add(new Player( "Ença Fati E. Fati", "FOR",55,moreirense));
        playerList2.add(new Player( "Vítor Gomes", "MID",59,moreirense));
        playerList2.add(new Player( "Emmanuel Boateng", "FOR",59,moreirense));
        playerList2.add(new Player( "Iuri Medeiros", "FOR",60,moreirense));
        playerList2.add(new Player( "Igor Stefanovic I. Stefanovic", "GK",57,moreirense));
        playerList2.add(new Player( "Nilson Nilson", "GK",53,moreirense));
        playerList2.add(new Player( "Victor Braga Victor Braga", "GK",56,moreirense));
        playerList2.add(new Player( "André Micael André Micael", "DEF",55,moreirense));
        playerList2.add(new Player( "Evaldo Evaldo", "DEF",52,moreirense));
        playerList2.add(new Player( "Pierre Sagna P. Sagna", "DEF",56,moreirense));
        playerList2.add(new Player( "Filipe Gonçalves F. Gonçalves", "MID",58,moreirense));
        playerList2.add(new Player( "Rafa Sousa Rafa Sousa", "MID",57,moreirense));
        playerList2.add(new Player( "Alan Schons A. Schons", "MID",55,moreirense));
        playerList2.add(new Player( "João Palhinha J. Palhinha", "MID",55,moreirense));
        playerList2.add(new Player( "Vítor Gomes V. Gomes", "MID",57,moreirense));
        playerList2.add(new Player( "Ernest Ernest", "FOR",53,moreirense));
        playerList2.add(new Player( "Iuri Medeiros I. Medeiros", "FOR",64,moreirense));
        playerList2.add(new Player( "Rafa Rafa", "FOR",57,moreirense));
        playerList2.add(new Player( "Rafael Martins Rafael Martins", "FOR",60,moreirense));
        playerList2.add(new Player( "Emmanuel Boateng E. Boateng", "FOR",60,moreirense));
        playerList2.add(new Player( "Ramón Cardozo R. Cardozo", "FOR",62,moreirense));
        playerList2.add(new Player( "Ricardo Almeida R. Almeida", "FOR",53,moreirense));
        playerList2.add(new Player( "Fábio Espinho Fábio Espinho", "MID",61,moreirense));
        playerList2.add(new Player( "Nildo Petrolina Nildo Petrolina", "FOR",55,moreirense));
        playerList2.add(new Player( "Ença Fati", "FOR",57,moreirense));
        playerList2.add(new Player( "Alex", "MID",58,arouca));
        playerList2.add(new Player( "Roberto", "FOR",62,arouca));
        playerList2.add(new Player( "Gegé", "DEF",61,arouca));
        playerList2.add(new Player( "Mateus", "FOR",64,arouca));
        playerList2.add(new Player( "Ivo Rodrigues", "FOR",60,arouca));
        playerList2.add(new Player( "Zequinha", "FOR",60,arouca));
        playerList2.add(new Player( "Rui Sacramento", "GK",54,arouca));
        playerList2.add(new Player( "Igor Rocha", "GK",57,arouca));
        playerList2.add(new Player( "Hugo Basto", "DEF",57,arouca));
        playerList2.add(new Player( "Nelsinho", "DEF",55,arouca));
        playerList2.add(new Player( "Tomás Dabó", "DEF",54,arouca));
        playerList2.add(new Player( "Nuno Coelho", "MID",61,arouca));
        playerList2.add(new Player( "David Simão", "MID",65,arouca));
        playerList2.add(new Player( "Pintassilgo", "FOR",61,arouca));
        playerList2.add(new Player( "Artur", "MID",54,arouca));
        playerList2.add(new Player( "Rafael Bracalli", "GK",52,arouca));
        playerList2.add(new Player( "José Manuel Velázquez", "DEF",57,arouca));
        playerList2.add(new Player( "Idrissa Coulibaly", "DEF",57,arouca));
        playerList2.add(new Player( "Lucas Lima", "DEF",55,arouca));
        playerList2.add(new Player( "Adilson Goiano", "MID",57,arouca));
        playerList2.add(new Player( "Nuno Valente", "MID",56,arouca));
        playerList2.add(new Player( "Hugo Monteiro", "FOR",53,arouca));
        playerList2.add(new Player( "Maurides", "FOR",55,arouca));
        playerList2.add(new Player( "Jubal", "DEF",55,arouca));
        playerList2.add(new Player( "Jaílson Araújo", "DEF",61,arouca));
        playerList2.add(new Player( "Emiliano Albín", "DEF",55,arouca));
        playerList2.add(new Player( "Rodney Wallace", "MID",57,arouca));
        playerList2.add(new Player( "Walter González", "FOR",59,arouca));


        for (Player p: playerList2){
            playerRepo.save(p);
        }

        playerList3.add(new Player( "Aarón Ñíguez", "FOR",61,braga));
        playerList3.add(new Player( "Filipe Augusto", "MID",63,braga));
        playerList3.add(new Player( "Arghus", "DEF",62,braga));
        playerList3.add(new Player( "Ricardo ", "DEF",57,braga));
        playerList3.add(new Player( "Ahmed Hassan", "FOR",71,braga));
        playerList3.add(new Player( "Matheus", "GK",66,braga));
        playerList3.add(new Player( "André Pinto", "DEF",67,braga));
        playerList3.add(new Player( "Willy Boly", "DEF",62,braga));
        playerList3.add(new Player( "Djavan", "DEF",66,braga));
        playerList3.add(new Player( "Baiano", "DEF",72,braga));
        playerList3.add(new Player( "Marcelo Goiano", "DEF",57,braga));
        playerList3.add(new Player( "Mauro", "MID",60,braga));
        playerList3.add(new Player( "Luíz Carlos", "MID",64,braga));
        playerList3.add(new Player( "Nikola Vukcevic", "MID",65,braga));
        playerList3.add(new Player( "Pedro Santos", "FOR",66,braga));
        playerList3.add(new Player( "Rafa Silva", "FOR",82,braga));
        playerList3.add(new Player( "Alan", "FOR",52,braga));
        playerList3.add(new Player( "Rui Fonte", "FOR",66,braga));
        playerList3.add(new Player( "Alef", "MID",55,braga));
        playerList3.add(new Player( "William Pottker", "FOR",54,braga));
        playerList3.add(new Player( "Wilson Eduardo", "FOR",65,braga));
        playerList3.add(new Player( "Nikola Stojiljkovic", "FOR",61,braga));
        playerList3.add(new Player( "Crislan", "FOR",55,braga));
        playerList3.add(new Player( "Marafona", "GK",60,braga));
        playerList3.add(new Player( "Stian Ringstad", "DEF",55,braga));
        playerList3.add(new Player( "Josué", "MID",70,braga));
        playerList3.add(new Player( "Paulinho", "MID",55,nacionalMadeira));
        playerList3.add(new Player( "Gottardi", "GK",66,nacionalMadeira));
        playerList3.add(new Player( "Rui Silva", "GK",53,nacionalMadeira));
        playerList3.add(new Player( "Kevin Sousa", "GK",53,nacionalMadeira));
        playerList3.add(new Player( "Miguel Rodrigues", "DEF",61,nacionalMadeira));
        playerList3.add(new Player( "Zainadine Júnior", "DEF",67,nacionalMadeira));
        playerList3.add(new Player( "Rui Correia", "DEF",62,nacionalMadeira));
        playerList3.add(new Player( "João Aurélio", "DEF",67,nacionalMadeira));
        playerList3.add(new Player( "Nuno Campos", "DEF",56,nacionalMadeira));
        playerList3.add(new Player( "Ali Ghazal", "MID",67,nacionalMadeira));
        playerList3.add(new Player( "Boubacar Fofana", "MID",57,nacionalMadeira));
        playerList3.add(new Player( "Luís Aurélio", "MID",55,nacionalMadeira));
        playerList3.add(new Player( "Tiquinho Soares", "FOR",55,nacionalMadeira));
        playerList3.add(new Player( "Willyan", "MID",55,nacionalMadeira));
        playerList3.add(new Player( "Witi", "FOR",54,nacionalMadeira));
        playerList3.add(new Player( "Salvador Agra", "FOR",66,nacionalMadeira));
        playerList3.add(new Player( "Filipe Ferreira", "GK",58,nacionalMadeira));
        playerList3.add(new Player( "Ricardo Gomes", "FOR",55,nacionalMadeira));
        playerList3.add(new Player( "Joan Román", "FOR",60,nacionalMadeira));
        playerList3.add(new Player( "Rodrigo Pinho", "FOR",57,nacionalMadeira));
        playerList3.add(new Player( "Alan Henrique", "DEF",55,nacionalMadeira));
        playerList3.add(new Player( "Nuno Sequeira", "DEF",56,nacionalMadeira));
        playerList3.add(new Player( "Mauro Cerqueira", "DEF",56,nacionalMadeira));
        playerList3.add(new Player( "Jota", "MID",57,nacionalMadeira));
        playerList3.add(new Player( "Nenê Bonilha", "MID",56,nacionalMadeira));
        playerList3.add(new Player( "Washington Santana", "MID",55,nacionalMadeira));
        playerList3.add(new Player( "Gustavo", "FOR",53,nacionalMadeira));
        playerList3.add(new Player( "Hicham Belkaroui", "DEF",56,nacionalMadeira));
        playerList3.add(new Player( "Danísio Barbosa", "MID",53,nacionalMadeira));
        playerList3.add(new Player( "Ederson", "GK",58,benfica));
        playerList3.add(new Player( "Konstantinos Mitroglou", "FOR",82,benfica));
        playerList3.add(new Player( "Júlio César", "GK",58,benfica));
        playerList3.add(new Player( "Paulo Lopes", "GK",53,benfica));
        playerList3.add(new Player( "Lisandro López", "DEF",72,benfica));
        playerList3.add(new Player( "Luisão", "DEF",64,benfica));
        playerList3.add(new Player( "Jardel", "DEF",70,benfica));
        playerList3.add(new Player( "Eliseu", "DEF",64,benfica));
        playerList3.add(new Player( "André Almeida", "DEF",72,benfica));
        playerList3.add(new Player( "Sílvio", "DEF",67,benfica));
        playerList3.add(new Player( "Andreas Samaris", "MID",80,benfica));
        playerList3.add(new Player( "Ljubomir Fejsa", "MID",70,benfica));
        playerList3.add(new Player( "Pizzi", "FOR",71,benfica));
        playerList3.add(new Player( "Jonas", "FOR",79,benfica));
        playerList3.add(new Player( "Talisca", "MID",80,benfica));
        playerList3.add(new Player( "Raúl Jiménez", "FOR",80,benfica));
        playerList3.add(new Player( "Raphael Guzzo", "MID",59,benfica));
        playerList3.add(new Player( "Victor Lindelöf", "DEF",58,benfica));
        playerList3.add(new Player( "Nélson Semedo", "DEF",70,benfica));
        playerList3.add(new Player( "Toto Salvio", "FOR",95,benfica));
        playerList3.add(new Player( "Mehdi Carcela-González", "FOR",65,benfica));
        playerList3.add(new Player( "Gonçalo Guedes", "FOR",70,benfica));
        playerList3.add(new Player( "Álex Grimaldo", "DEF",59,benfica));
        playerList3.add(new Player( "Renato Sanches", "MID",59,benfica));
        playerList3.add(new Player( "Nico Gaitán", "FOR",99,benfica));
        playerList3.add(new Player( "Luka Jovic", "FOR",65,benfica));
        playerList3.add(new Player( "Gerso", "FOR",60,estoril));
        playerList3.add(new Player( "Pawel Kieszek", "GK",62,estoril));
        playerList3.add(new Player( "Rúben Dionísio", "GK",55,estoril));
        playerList3.add(new Player( "Yohan Tavares", "DEF",67,estoril));
        playerList3.add(new Player( "Bruno Miguel", "DEF",52,estoril));
        playerList3.add(new Player( "Babanco", "MID",62,estoril));
        playerList3.add(new Player( "Ânderson Luís", "DEF",61,estoril));
        playerList3.add(new Player( "Mano", "DEF",62,estoril));
        playerList3.add(new Player( "Diogo Amado", "MID",67,estoril));
        playerList3.add(new Player( "Afonso Taira", "MID",57,estoril));
        playerList3.add(new Player( "Mattheus", "MID",55,estoril));
        playerList3.add(new Player( "Tijane", "FOR",56,estoril));
        playerList3.add(new Player( "Léo Bonatini", "FOR",58,estoril));
        playerList3.add(new Player( "Georgemy", "GK",54,estoril));
        playerList3.add(new Player( "Oumar Diakhite", "DEF",55,estoril));
        playerList3.add(new Player( "Diogo Baltazar", "MID",53,estoril));
        playerList3.add(new Player( "Diego Carlos", "DEF",57,estoril));
        playerList3.add(new Player( "Anderson Esiti", "MID",58,estoril));
        playerList3.add(new Player( "Gladestony", "MID",55,estoril));
        playerList3.add(new Player( "Matheuzinho", "MID",55,estoril));
        playerList3.add(new Player( "Leandro Chaparro", "MID",57,estoril));
        playerList3.add(new Player( "Dieguinho", "FOR",55,estoril));
        playerList3.add(new Player( "Frédéric Mendy", "FOR",62,estoril));
        playerList3.add(new Player( "Luiz Phellype", "FOR",53,estoril));
        playerList3.add(new Player( "Dankler", "DEF",57,estoril));
        playerList3.add(new Player( "Pedro Botelho", "DEF",56,estoril));
        playerList3.add(new Player( "Lucas Farias", "DEF",55,estoril));
        playerList3.add(new Player( "Marion", "FOR",61,estoril));
        playerList3.add(new Player( "Michael", "FOR",62,estoril));
        playerList3.add(new Player( "Felipe Augusto", "FOR",60,estoril));
        playerList3.add(new Player( "Pelé", "MID",66,pacos));
        playerList3.add(new Player( "Ricardo", "DEF",53,pacos));
        playerList3.add(new Player( "Rafael Defendi", "GK",58,pacos));
        playerList3.add(new Player( "Paulo Henrique", "DEF",54,pacos));
        playerList3.add(new Player( "Fábio Cardoso", "DEF",58,pacos));
        playerList3.add(new Player( "Hélder Lopes", "DEF",60,pacos));
        playerList3.add(new Player( "Romeu Rocha", "MID",56,pacos));
        playerList3.add(new Player( "Diogo Jota", "FOR",53,pacos));
        playerList3.add(new Player( "Manuel José", "FOR",53,pacos));
        playerList3.add(new Player( "Paraiba", "FOR",60,pacos));
        playerList3.add(new Player( "Minhoca", "FOR",60,pacos));
        playerList3.add(new Player( "Cícero", "FOR",57,pacos));
        playerList3.add(new Player( "Bruno Moreira", "FOR",67,pacos));
        playerList3.add(new Player( "Christian", "MID",57,pacos));
        playerList3.add(new Player( "Fábio Martins", "FOR",59,pacos));
        playerList3.add(new Player( "Miguel Vieira", "DEF",57,pacos));
        playerList3.add(new Player( "Marco Baixinho", "DEF",56,pacos));
        playerList3.add(new Player( "João Góis", "DEF",57,pacos));
        playerList3.add(new Player( "Bruno Santos", "DEF",56,pacos));
        playerList3.add(new Player( "Rodrigo Antônio", "MID",62,pacos));
        playerList3.add(new Player( "Andrezinho", "MID",53,pacos));
        playerList3.add(new Player( "Roniel", "FOR",55,pacos));
        playerList3.add(new Player( "Barnes Osei", "FOR",54,pacos));
        playerList3.add(new Player( "Mário Felgueiras", "GK",62,pacos));
        playerList3.add(new Player( "Marco", "GK",55,pacos));
        playerList3.add(new Player( "Ponck", "MID",54,pacos));
        playerList3.add(new Player( "Lee", "GK",56,academica));
        playerList3.add(new Player( "Ricardo Nascimento", "DEF",62,academica));
        playerList3.add(new Player( "Iago Santos", "DEF",60,academica));
        playerList3.add(new Player( "João Real", "DEF",53,academica));
        playerList3.add(new Player( "Richard Ofori", "DEF",57,academica));
        playerList3.add(new Player( "Tripy Makonda", "DEF",55,academica));
        playerList3.add(new Player( "Aderlan", "DEF",56,academica));
        playerList3.add(new Player( "Christopher Oualembo", "DEF",57,academica));
        playerList3.add(new Player( "Fernando Alexandre", "MID",62,academica));
        playerList3.add(new Player( "Nwankwo Obiora", "MID",62,academica));
        playerList3.add(new Player( "Nuno Piloto", "MID",52,academica));
        playerList3.add(new Player( "Rui Pedro", "MID",61,academica));
        playerList3.add(new Player( "Pedro Nuno", "MID",53,academica));
        playerList3.add(new Player( "Ivanildo", "FOR",55,academica));
        playerList3.add(new Player( "Marinho", "FOR",54,academica));
        playerList3.add(new Player( "Hugo Seco", "FOR",55,academica));
        playerList3.add(new Player( "Nii Plange", "FOR",62,academica));
        playerList3.add(new Player( "Inters Gui", "FOR",54,academica));
        playerList3.add(new Player( "Rabiola", "FOR",61,academica));
        playerList3.add(new Player( "Emídio Rafael", "DEF",62,academica));
        playerList3.add(new Player( "Pedro Trigueira", "GK",55,academica));
        playerList3.add(new Player( "João Gomes", "GK",55,academica));
        playerList3.add(new Player( "William Gustavo", "DEF",57,academica));
        playerList3.add(new Player( "Leandro Silva", "MID",55,academica));
        playerList3.add(new Player( "Selim Bouadla", "MID",55,academica));
        playerList3.add(new Player( "Artur Taborda", "MID",55,academica));
        playerList3.add(new Player( "Rafael Lopes", "FOR",60,academica));
        playerList3.add(new Player( "Gonçalo Paciência", "FOR",60,academica));
        playerList3.add(new Player( "Rafa Soares", "DEF",60,academica));
        playerList3.add(new Player( "Ki", "MID",53,academica));

        for (Player p: playerList3){
            playerRepo.save(p);
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);

        GameWeek gw1 = new GameWeek(format.parse("2015-08-17 19:00:00"),1);
        GameWeek gw2 = new GameWeek(format.parse("2015-08-24 19:00:00"),2);
        GameWeek gw3 = new GameWeek(format.parse("2015-08-31 20:00:00"),3);
        GameWeek gw4 = new GameWeek(format.parse("2015-09-14 19:00:00"),4);
        GameWeek gw5 = new GameWeek(format.parse("2015-09-21 20:00:00"),5);
        GameWeek gw6 = new GameWeek(format.parse("2015-09-28 19:00:00"),6);
        GameWeek gw7 = new GameWeek(format.parse("2015-12-23 20:00:00"),7);
        GameWeek gw8 = new GameWeek(format.parse("2015-10-26 20:00:00"),8);
        GameWeek gw9 = new GameWeek(format.parse("2015-12-02 20:00:00"),9);
        GameWeek gw10 = new GameWeek(format.parse("2015-11-09 21:00:00"),10);
        GameWeek gw11 = new GameWeek(format.parse("2015-11-30 21:00:00"),11);
        GameWeek gw12 = new GameWeek(format.parse("2015-12-07 20:00:00"),12);
        GameWeek gw13 = new GameWeek(format.parse("2015-12-14 20:00:00"),13);
        GameWeek gw14 = new GameWeek(format.parse("2015-12-21 21:00:00"),14);
        GameWeek gw15 = new GameWeek(format.parse("2016-01-03 19:15:00"),15);
        GameWeek gw16 = new GameWeek(format.parse("2016-01-06 21:15:00"),16);
        GameWeek gw17 = new GameWeek(format.parse("2016-01-11 20:00:00"),17);
        GameWeek gw18 = new GameWeek(format.parse("2016-01-18 20:00:00"),18);
        GameWeek gw19 = new GameWeek(format.parse("2016-01-25 20:00:00"),19);
        GameWeek gw20 = new GameWeek(format.parse("2016-02-01 20:00:00"),20);
        GameWeek gw21 = new GameWeek(format.parse("2016-02-08 21:00:00"),21);
        GameWeek gw22 = new GameWeek(format.parse("2016-02-15 20:00:00"),22);
        GameWeek gw23 = new GameWeek(format.parse("2016-02-22 20:00:00"),23);
        GameWeek gw24 = new GameWeek(format.parse("2016-02-29 20:00:00"),24);
        GameWeek gw25 = new GameWeek(format.parse("2016-03-07 20:00:00"),25);
        GameWeek gw26 = new GameWeek(format.parse("2016-03-14 20:00:00"),26);
        GameWeek gw27 = new GameWeek(format.parse("2016-03-20 20:30:00"),27);
        GameWeek gw28 = new GameWeek(format.parse("2016-04-04 20:00:00"),28);
        GameWeek gw29 = new GameWeek(format.parse("2016-04-11 19:00:00"),29);
        GameWeek gw30 = new GameWeek(format.parse("2016-04-18 20:00:00"),30);
        GameWeek gw31 = new GameWeek(format.parse("2016-04-24 19:30:00"),31);
        GameWeek gw32 = new GameWeek(format.parse("2016-05-01 19:30:00"),32);
        GameWeek gw33 = new GameWeek(format.parse("2016-05-09 19:00:00"),33);
        GameWeek gw34 = new GameWeek(format.parse("2016-05-15 16:00:00"),34);
        gameWeekDAO.save(gw1);
        gameWeekDAO.save(gw2);
        gameWeekDAO.save(gw3);
        gameWeekDAO.save(gw4);
        gameWeekDAO.save(gw5);
        gameWeekDAO.save(gw6);
        gameWeekDAO.save(gw7);
        gameWeekDAO.save(gw8);
        gameWeekDAO.save(gw9);
        gameWeekDAO.save(gw10);
        gameWeekDAO.save(gw11);
        gameWeekDAO.save(gw12);
        gameWeekDAO.save(gw13);
        gameWeekDAO.save(gw14);
        gameWeekDAO.save(gw15);
        gameWeekDAO.save(gw16);
        gameWeekDAO.save(gw17);
        gameWeekDAO.save(gw18);
        gameWeekDAO.save(gw19);
        gameWeekDAO.save(gw20);
        gameWeekDAO.save(gw21);
        gameWeekDAO.save(gw22);
        gameWeekDAO.save(gw23);
        gameWeekDAO.save(gw24);
        gameWeekDAO.save(gw25);
        gameWeekDAO.save(gw26);
        gameWeekDAO.save(gw27);
        gameWeekDAO.save(gw28);
        gameWeekDAO.save(gw29);
        gameWeekDAO.save(gw30);
        gameWeekDAO.save(gw31);
        gameWeekDAO.save(gw32);
        gameWeekDAO.save(gw33);
        gameWeekDAO.save(gw34);

        ArrayList<Game> gameList = new ArrayList<>();

        gameList.add(new Game(format.parse("2015-08-14 19:30:00"),tondela,sporting,gw1));
        gameList.add(new Game(format.parse("2015-08-15 17:30:00"),belenenses,rioAve,gw1));
        gameList.add(new Game(format.parse("2015-08-15 19:45:00"),porto,guimaraes,gw1));
        gameList.add(new Game(format.parse("2015-08-16 15:00:00"),setubal,boavista,gw1));
        gameList.add(new Game(format.parse("2015-08-16 15:00:00"),uniaoMadeira,maritimo,gw1));
        gameList.add(new Game(format.parse("2015-08-16 16:00:00"),moreirense,arouca,gw1));
        gameList.add(new Game(format.parse("2015-08-16 17:15:00"),braga,nacionalMadeira,gw1));
        gameList.add(new Game(format.parse("2015-08-16 19:30:00"),benfica,estoril,gw1));
        gameList.add(new Game(format.parse("2015-08-17 19:00:00"),pacos,academica,gw1));
        gameList.add(new Game(format.parse("2015-08-21 19:30:00"),rioAve,braga,gw2));
        gameList.add(new Game(format.parse("2015-08-22 17:30:00"),sporting,pacos,gw2));
        gameList.add(new Game(format.parse("2015-08-22 19:45:00"),maritimo,porto,gw2));
        gameList.add(new Game(format.parse("2015-08-23 16:00:00"),guimaraes,belenenses,gw2));
        gameList.add(new Game(format.parse("2015-08-23 16:00:00"),boavista,tondela,gw2));
        gameList.add(new Game(format.parse("2015-08-23 16:00:00"),nacionalMadeira,uniaoMadeira,gw2));
        gameList.add(new Game(format.parse("2015-08-23 16:00:00"),estoril,moreirense,gw2));
        gameList.add(new Game(format.parse("2015-08-23 18:15:00"),arouca,benfica,gw2));
        gameList.add(new Game(format.parse("2015-08-24 19:00:00"),academica,setubal,gw2));
        gameList.add(new Game(format.parse("2015-08-29 15:15:00"),setubal,rioAve,gw3));
        gameList.add(new Game(format.parse("2015-08-29 17:30:00"),porto,estoril,gw3));
        gameList.add(new Game(format.parse("2015-08-29 19:45:00"),benfica,moreirense,gw3));
        gameList.add(new Game(format.parse("2015-08-30 15:00:00"),tondela,nacionalMadeira,gw3));
        gameList.add(new Game(format.parse("2015-08-30 16:00:00"),pacos,arouca,gw3));
        gameList.add(new Game(format.parse("2015-08-30 16:00:00"),braga,boavista,gw3));
        gameList.add(new Game(format.parse("2015-08-30 18:15:00"),academica,sporting,gw3));
        gameList.add(new Game(format.parse("2015-08-31 18:00:00"),belenenses,maritimo,gw3));
        gameList.add(new Game(format.parse("2015-08-31 20:00:00"),uniaoMadeira,guimaraes,gw3));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),moreirense,uniaoMadeira,gw4));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),guimaraes,tondela,gw4));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),benfica,belenenses,gw4));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),estoril,braga,gw4));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),maritimo,setubal,gw4));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),nacionalMadeira,academica,gw4));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),rioAve,sporting,gw4));
        gameList.add(new Game(format.parse("2015-09-13 15:00:00"),arouca,porto,gw4));
        gameList.add(new Game(format.parse("2015-09-14 19:00:00"),boavista,pacos,gw4));
        gameList.add(new Game(format.parse("2015-09-20 15:00:00"),uniaoMadeira,arouca,gw5));
        gameList.add(new Game(format.parse("2015-09-20 15:00:00"),pacos,rioAve,gw5));
        gameList.add(new Game(format.parse("2015-09-20 15:00:00"),setubal,guimaraes,gw5));
        gameList.add(new Game(format.parse("2015-09-20 15:00:00"),academica,boavista,gw5));
        gameList.add(new Game(format.parse("2015-09-20 15:00:00"),tondela,estoril,gw5));
        gameList.add(new Game(format.parse("2015-09-20 18:15:00"),porto,benfica,gw5));
        gameList.add(new Game(format.parse("2015-09-21 17:00:00"),belenenses,moreirense,gw5));
        gameList.add(new Game(format.parse("2015-09-21 18:00:00"),braga,maritimo,gw5));
        gameList.add(new Game(format.parse("2015-09-21 20:00:00"),sporting,nacionalMadeira,gw5));
        gameList.add(new Game(format.parse("2015-09-25 19:30:00"),moreirense,porto,gw6));
        gameList.add(new Game(format.parse("2015-09-26 17:30:00"),benfica,pacos,gw6));
        gameList.add(new Game(format.parse("2015-09-26 19:45:00"),boavista,sporting,gw6));
        gameList.add(new Game(format.parse("2015-09-27 15:00:00"),maritimo,tondela,gw6));
        gameList.add(new Game(format.parse("2015-09-27 15:00:00"),nacionalMadeira,setubal,gw6));
        gameList.add(new Game(format.parse("2015-09-27 16:00:00"),arouca,belenenses,gw6));
        gameList.add(new Game(format.parse("2015-09-27 16:00:00"),estoril,uniaoMadeira,gw6));
        gameList.add(new Game(format.parse("2015-09-27 18:15:00"),guimaraes,braga,gw6));
        gameList.add(new Game(format.parse("2015-09-28 19:00:00"),rioAve,academica,gw6));
        gameList.add(new Game(format.parse("2015-10-02 19:30:00"),setubal,estoril,gw7));
        gameList.add(new Game(format.parse("2015-10-03 15:00:00"),tondela,moreirense,gw7));
        gameList.add(new Game(format.parse("2015-10-03 15:00:00"),pacos,nacionalMadeira,gw7));
        gameList.add(new Game(format.parse("2015-10-03 17:30:00"),academica,maritimo,gw7));
        gameList.add(new Game(format.parse("2015-10-03 19:45:00"),rioAve,boavista,gw7));
        gameList.add(new Game(format.parse("2015-10-04 17:00:00"),braga,arouca,gw7));
        gameList.add(new Game(format.parse("2015-10-04 17:15:00"),porto,belenenses,gw7));
        gameList.add(new Game(format.parse("2015-10-04 19:30:00"),sporting,guimaraes,gw7));
        gameList.add(new Game(format.parse("2015-10-23 19:30:00"),nacionalMadeira,boavista,gw8));
        gameList.add(new Game(format.parse("2015-10-24 15:15:00"),maritimo,pacos,gw8));
        gameList.add(new Game(format.parse("2015-10-24 17:30:00"),estoril,rioAve,gw8));
        gameList.add(new Game(format.parse("2015-10-24 19:45:00"),guimaraes,academica,gw8));
        gameList.add(new Game(format.parse("2015-10-25 16:00:00"),arouca,tondela,gw8));
        gameList.add(new Game(format.parse("2015-10-25 16:00:00"),moreirense,setubal,gw8));
        gameList.add(new Game(format.parse("2015-10-25 17:00:00"),benfica,sporting,gw8));
        gameList.add(new Game(format.parse("2015-10-25 19:15:00"),porto,braga,gw8));
        gameList.add(new Game(format.parse("2015-10-26 20:00:00"),belenenses,uniaoMadeira,gw8));
        gameList.add(new Game(format.parse("2015-10-30 20:30:00"),tondela,benfica,gw9));
        gameList.add(new Game(format.parse("2015-10-31 16:15:00"),braga,belenenses,gw9));
        gameList.add(new Game(format.parse("2015-10-31 20:45:00"),sporting,estoril,gw9));
        gameList.add(new Game(format.parse("2015-11-01 16:00:00"),rioAve,nacionalMadeira,gw9));
        gameList.add(new Game(format.parse("2015-11-01 16:00:00"),academica,moreirense,gw9));
        gameList.add(new Game(format.parse("2015-11-01 16:00:00"),setubal,arouca,gw9));
        gameList.add(new Game(format.parse("2015-11-01 19:15:00"),boavista,maritimo,gw9));
        gameList.add(new Game(format.parse("2015-11-02 20:00:00"),pacos,guimaraes,gw9));
        gameList.add(new Game(format.parse("2015-11-06 20:30:00"),estoril,academica,gw10));
        gameList.add(new Game(format.parse("2015-11-07 20:45:00"),guimaraes,nacionalMadeira,gw10));
        gameList.add(new Game(format.parse("2015-11-08 16:00:00"),benfica,boavista,gw10));
        gameList.add(new Game(format.parse("2015-11-08 16:00:00"),moreirense,pacos,gw10));
        gameList.add(new Game(format.parse("2015-11-08 16:00:00"),maritimo,rioAve,gw10));
        gameList.add(new Game(format.parse("2015-11-08 18:15:00"),porto,setubal,gw10));
        gameList.add(new Game(format.parse("2015-11-08 20:30:00"),arouca,sporting,gw10));
        gameList.add(new Game(format.parse("2015-11-09 19:00:00"),belenenses,tondela,gw10));
        gameList.add(new Game(format.parse("2015-11-09 21:00:00"),uniaoMadeira,braga,gw10));
        gameList.add(new Game(format.parse("2015-11-27 20:30:00"),nacionalMadeira,maritimo,gw11));
        gameList.add(new Game(format.parse("2015-11-28 20:45:00"),boavista,guimaraes,gw11));
        gameList.add(new Game(format.parse("2015-11-29 16:00:00"),rioAve,moreirense,gw11));
        gameList.add(new Game(format.parse("2015-11-29 16:00:00"),academica,arouca,gw11));
        gameList.add(new Game(format.parse("2015-11-29 16:00:00"),setubal,uniaoMadeira,gw11));
        gameList.add(new Game(format.parse("2015-11-29 19:15:00"),pacos,estoril,gw11));
        gameList.add(new Game(format.parse("2015-11-29 19:15:00"),tondela,porto,gw11));
        gameList.add(new Game(format.parse("2015-11-30 19:00:00"),sporting,belenenses,gw11));
        gameList.add(new Game(format.parse("2015-11-30 21:00:00"),braga,benfica,gw11));
        gameList.add(new Game(format.parse("2015-12-02 20:00:00"),uniaoMadeira,porto,gw9));
        gameList.add(new Game(format.parse("2015-12-04 20:30:00"),benfica,academica,gw12));
        gameList.add(new Game(format.parse("2015-12-05 16:15:00"),belenenses,setubal,gw12));
        gameList.add(new Game(format.parse("2015-12-05 18:30:00"),porto,pacos,gw12));
        gameList.add(new Game(format.parse("2015-12-05 20:45:00"),maritimo,sporting,gw12));
        gameList.add(new Game(format.parse("2015-12-06 16:00:00"),estoril,nacionalMadeira,gw12));
        gameList.add(new Game(format.parse("2015-12-06 16:00:00"),uniaoMadeira,tondela,gw12));
        gameList.add(new Game(format.parse("2015-12-06 16:00:00"),arouca,boavista,gw12));
        gameList.add(new Game(format.parse("2015-12-06 17:00:00"),moreirense,braga,gw12));
        gameList.add(new Game(format.parse("2015-12-07 20:00:00"),guimaraes,rioAve,gw12));
        gameList.add(new Game(format.parse("2015-12-11 20:30:00"),boavista,estoril,gw13));
        gameList.add(new Game(format.parse("2015-12-12 16:15:00"),rioAve,arouca,gw13));
        gameList.add(new Game(format.parse("2015-12-12 18:30:00"),guimaraes,maritimo,gw13));
        gameList.add(new Game(format.parse("2015-12-12 20:45:00"),setubal,benfica,gw13));
        gameList.add(new Game(format.parse("2015-12-13 16:00:00"),pacos,uniaoMadeira,gw13));
        gameList.add(new Game(format.parse("2015-12-13 16:00:00"),nacionalMadeira,porto,gw13));
        gameList.add(new Game(format.parse("2015-12-13 18:15:00"),sporting,moreirense,gw13));
        gameList.add(new Game(format.parse("2015-12-13 20:30:00"),tondela,braga,gw13));
        gameList.add(new Game(format.parse("2015-12-14 20:00:00"),academica,belenenses,gw13));
        gameList.add(new Game(format.parse("2015-12-19 18:30:00"),arouca,maritimo,gw14));
        gameList.add(new Game(format.parse("2015-12-19 20:45:00"),estoril,guimaraes,gw14));
        gameList.add(new Game(format.parse("2015-12-20 16:00:00"),moreirense,nacionalMadeira,gw14));
        gameList.add(new Game(format.parse("2015-12-20 16:00:00"),tondela,setubal,gw14));
        gameList.add(new Game(format.parse("2015-12-20 16:00:00"),benfica,rioAve,gw14));
        gameList.add(new Game(format.parse("2015-12-20 18:15:00"),uniaoMadeira,sporting,gw14));
        gameList.add(new Game(format.parse("2015-12-20 20:30:00"),porto,academica,gw14));
        gameList.add(new Game(format.parse("2015-12-21 19:00:00"),belenenses,boavista,gw14));
        gameList.add(new Game(format.parse("2015-12-21 21:00:00"),braga,pacos,gw14));
        gameList.add(new Game(format.parse("2015-12-23 20:00:00"),uniaoMadeira,benfica,gw7));
        gameList.add(new Game(format.parse("2016-01-02 16:00:00"),nacionalMadeira,arouca,gw15));
        gameList.add(new Game(format.parse("2016-01-02 16:00:00"),maritimo,estoril,gw15));
        gameList.add(new Game(format.parse("2016-01-02 16:00:00"),boavista,moreirense,gw15));
        gameList.add(new Game(format.parse("2016-01-02 16:00:00"),academica,uniaoMadeira,gw15));
        gameList.add(new Game(format.parse("2016-01-02 16:15:00"),setubal,braga,gw15));
        gameList.add(new Game(format.parse("2016-01-02 18:30:00"),guimaraes,benfica,gw15));
        gameList.add(new Game(format.parse("2016-01-02 20:45:00"),sporting,porto,gw15));
        gameList.add(new Game(format.parse("2016-01-03 17:00:00"),rioAve,tondela,gw15));
        gameList.add(new Game(format.parse("2016-01-03 19:15:00"),pacos,belenenses,gw15));
        gameList.add(new Game(format.parse("2016-01-06 16:00:00"),uniaoMadeira,boavista,gw16));
        gameList.add(new Game(format.parse("2016-01-06 16:00:00"),belenenses,nacionalMadeira,gw16));
        gameList.add(new Game(format.parse("2016-01-06 16:00:00"),tondela,pacos,gw16));
        gameList.add(new Game(format.parse("2016-01-06 16:00:00"),arouca,estoril,gw16));
        gameList.add(new Game(format.parse("2016-01-06 16:00:00"),braga,academica,gw16));
        gameList.add(new Game(format.parse("2016-01-06 16:00:00"),moreirense,guimaraes,gw16));
        gameList.add(new Game(format.parse("2016-01-06 19:30:00"),benfica,maritimo,gw16));
        gameList.add(new Game(format.parse("2016-01-06 20:15:00"),porto,rioAve,gw16));
        gameList.add(new Game(format.parse("2016-01-06 21:15:00"),setubal,sporting,gw16));
        gameList.add(new Game(format.parse("2016-01-09 18:30:00"),guimaraes,arouca,gw17));
        gameList.add(new Game(format.parse("2016-01-10 16:00:00"),rioAve,uniaoMadeira,gw17));
        gameList.add(new Game(format.parse("2016-01-10 16:00:00"),academica,tondela,gw17));
        gameList.add(new Game(format.parse("2016-01-10 16:00:00"),sporting,braga,gw17));
        gameList.add(new Game(format.parse("2016-01-10 16:00:00"),estoril,belenenses,gw17));
        gameList.add(new Game(format.parse("2016-01-10 16:00:00"),maritimo,moreirense,gw17));
        gameList.add(new Game(format.parse("2016-01-10 18:15:00"),boavista,porto,gw17));
        gameList.add(new Game(format.parse("2016-01-11 12:00:00"),nacionalMadeira,benfica,gw17));
        gameList.add(new Game(format.parse("2016-01-11 20:00:00"),pacos,setubal,gw17));
        gameList.add(new Game(format.parse("2016-01-15 20:30:00"),sporting,tondela,gw18));
        gameList.add(new Game(format.parse("2016-01-16 16:15:00"),academica,pacos,gw18));
        gameList.add(new Game(format.parse("2016-01-17 16:00:00"),maritimo,uniaoMadeira,gw18));
        gameList.add(new Game(format.parse("2016-01-17 16:00:00"),estoril,benfica,gw18));
        gameList.add(new Game(format.parse("2016-01-17 16:00:00"),arouca,moreirense,gw18));
        gameList.add(new Game(format.parse("2016-01-17 16:00:00"),rioAve,belenenses,gw18));
        gameList.add(new Game(format.parse("2016-01-17 18:15:00"),nacionalMadeira,braga,gw18));
        gameList.add(new Game(format.parse("2016-01-17 20:30:00"),guimaraes,porto,gw18));
        gameList.add(new Game(format.parse("2016-01-18 20:00:00"),boavista,setubal,gw18));
        gameList.add(new Game(format.parse("2016-01-22 20:30:00"),setubal,academica,gw19));
        gameList.add(new Game(format.parse("2016-01-23 16:00:00"),moreirense,estoril,gw19));
        gameList.add(new Game(format.parse("2016-01-23 16:15:00"),uniaoMadeira,nacionalMadeira,gw19));
        gameList.add(new Game(format.parse("2016-01-23 18:30:00"),benfica,arouca,gw19));
        gameList.add(new Game(format.parse("2016-01-23 20:45:00"),pacos,sporting,gw19));
        gameList.add(new Game(format.parse("2016-01-24 16:00:00"),belenenses,guimaraes,gw19));
        gameList.add(new Game(format.parse("2016-01-24 18:15:00"),braga,rioAve,gw19));
        gameList.add(new Game(format.parse("2016-01-24 20:30:00"),porto,maritimo,gw19));
        gameList.add(new Game(format.parse("2016-01-25 20:00:00"),tondela,boavista,gw19));
        gameList.add(new Game(format.parse("2016-01-29 19:00:00"),guimaraes,uniaoMadeira,gw20));
        gameList.add(new Game(format.parse("2016-01-30 18:30:00"),estoril,porto,gw20));
        gameList.add(new Game(format.parse("2016-01-30 20:45:00"),sporting,academica,gw20));
        gameList.add(new Game(format.parse("2016-01-31 16:00:00"),arouca,pacos,gw20));
        gameList.add(new Game(format.parse("2016-01-31 16:00:00"),nacionalMadeira,tondela,gw20));
        gameList.add(new Game(format.parse("2016-01-31 16:00:00"),rioAve,setubal,gw20));
        gameList.add(new Game(format.parse("2016-01-31 18:00:00"),boavista,braga,gw20));
        gameList.add(new Game(format.parse("2016-01-31 19:15:00"),moreirense,benfica,gw20));
        gameList.add(new Game(format.parse("2016-02-01 20:00:00"),maritimo,belenenses,gw20));
        gameList.add(new Game(format.parse("2016-02-05 20:30:00"),belenenses,benfica,gw21));
        gameList.add(new Game(format.parse("2016-02-06 18:30:00"),setubal,maritimo,gw21));
        gameList.add(new Game(format.parse("2016-02-06 20:45:00"),tondela,guimaraes,gw21));
        gameList.add(new Game(format.parse("2016-02-07 16:00:00"),pacos,boavista,gw21));
        gameList.add(new Game(format.parse("2016-02-07 16:00:00"),uniaoMadeira,moreirense,gw21));
        gameList.add(new Game(format.parse("2016-02-07 16:00:00"),academica,nacionalMadeira,gw21));
        gameList.add(new Game(format.parse("2016-02-07 19:15:00"),porto,arouca,gw21));
        gameList.add(new Game(format.parse("2016-02-08 19:00:00"),sporting,rioAve,gw21));
        gameList.add(new Game(format.parse("2016-02-08 21:00:00"),braga,estoril,gw21));
        gameList.add(new Game(format.parse("2016-02-12 20:30:00"),benfica,porto,gw22));
        gameList.add(new Game(format.parse("2016-02-13 16:15:00"),moreirense,belenenses,gw22));
        gameList.add(new Game(format.parse("2016-02-13 18:30:00"),nacionalMadeira,sporting,gw22));
        gameList.add(new Game(format.parse("2016-02-13 20:45:00"),guimaraes,setubal,gw22));
        gameList.add(new Game(format.parse("2016-02-14 16:00:00"),estoril,tondela,gw22));
        gameList.add(new Game(format.parse("2016-02-14 16:00:00"),arouca,uniaoMadeira,gw22));
        gameList.add(new Game(format.parse("2016-02-14 17:00:00"),boavista,academica,gw22));
        gameList.add(new Game(format.parse("2016-02-14 19:15:00"),maritimo,braga,gw22));
        gameList.add(new Game(format.parse("2016-02-15 20:00:00"),rioAve,pacos,gw22));
        gameList.add(new Game(format.parse("2016-02-19 20:30:00"),uniaoMadeira,estoril,gw23));
        gameList.add(new Game(format.parse("2016-02-20 18:30:00"),pacos,benfica,gw23));
        gameList.add(new Game(format.parse("2016-02-20 20:45:00"),academica,rioAve,gw23));
        gameList.add(new Game(format.parse("2016-02-21 16:00:00"),tondela,maritimo,gw23));
        gameList.add(new Game(format.parse("2016-02-21 16:00:00"),setubal,nacionalMadeira,gw23));
        gameList.add(new Game(format.parse("2016-02-21 16:00:00"),belenenses,arouca,gw23));
        gameList.add(new Game(format.parse("2016-02-21 18:15:00"),porto,moreirense,gw23));
        gameList.add(new Game(format.parse("2016-02-21 20:30:00"),braga,guimaraes,gw23));
        gameList.add(new Game(format.parse("2016-02-22 20:00:00"),sporting,boavista,gw23));
        gameList.add(new Game(format.parse("2016-02-26 20:30:00"),nacionalMadeira,pacos,gw24));
        gameList.add(new Game(format.parse("2016-02-27 16:00:00"),estoril,setubal,gw24));
        gameList.add(new Game(format.parse("2016-02-27 16:15:00"),boavista,rioAve,gw24));
        gameList.add(new Game(format.parse("2016-02-27 18:30:00"),arouca,braga,gw24));
        gameList.add(new Game(format.parse("2016-02-28 16:00:00"),moreirense,tondela,gw24));
        gameList.add(new Game(format.parse("2016-02-28 16:00:00"),maritimo,academica,gw24));
        gameList.add(new Game(format.parse("2016-02-28 19:15:00"),belenenses,porto,gw24));
        gameList.add(new Game(format.parse("2016-02-29 19:45:00"),benfica,uniaoMadeira,gw24));
        gameList.add(new Game(format.parse("2016-02-29 20:00:00"),guimaraes,sporting,gw24));
        gameList.add(new Game(format.parse("2016-03-04 20:30:00"),pacos,maritimo,gw25));
        gameList.add(new Game(format.parse("2016-03-05 20:45:00"),sporting,benfica,gw25));
        gameList.add(new Game(format.parse("2016-03-06 16:00:00"),boavista,nacionalMadeira,gw25));
        gameList.add(new Game(format.parse("2016-03-06 16:00:00"),uniaoMadeira,belenenses,gw25));
        gameList.add(new Game(format.parse("2016-03-06 16:00:00"),tondela,arouca,gw25));
        gameList.add(new Game(format.parse("2016-03-06 16:00:00"),setubal,moreirense,gw25));
        gameList.add(new Game(format.parse("2016-03-06 18:15:00"),academica,guimaraes,gw25));
        gameList.add(new Game(format.parse("2016-03-06 20:30:00"),braga,porto,gw25));
        gameList.add(new Game(format.parse("2016-03-07 20:00:00"),rioAve,estoril,gw25));
        gameList.add(new Game(format.parse("2016-03-11 20:30:00"),maritimo,boavista,gw26));
        gameList.add(new Game(format.parse("2016-03-12 18:30:00"),estoril,sporting,gw26));
        gameList.add(new Game(format.parse("2016-03-12 20:45:00"),porto,uniaoMadeira,gw26));
        gameList.add(new Game(format.parse("2016-03-13 16:00:00"),arouca,setubal,gw26));
        gameList.add(new Game(format.parse("2016-03-13 16:00:00"),moreirense,academica,gw26));
        gameList.add(new Game(format.parse("2016-03-13 16:00:00"),nacionalMadeira,rioAve,gw26));
        gameList.add(new Game(format.parse("2016-03-13 18:15:00"),guimaraes,pacos,gw26));
        gameList.add(new Game(format.parse("2016-03-13 20:30:00"),belenenses,braga,gw26));
        gameList.add(new Game(format.parse("2016-03-14 20:00:00"),benfica,tondela,gw26));
        gameList.add(new Game(format.parse("2016-03-18 20:30:00"),rioAve,maritimo,gw27));
        gameList.add(new Game(format.parse("2016-03-19 18:30:00"),sporting,arouca,gw27));
        gameList.add(new Game(format.parse("2016-03-19 20:45:00"),setubal,porto,gw27));
        gameList.add(new Game(format.parse("2016-03-20 16:00:00"),academica,estoril,gw27));
        gameList.add(new Game(format.parse("2016-03-20 16:00:00"),pacos,moreirense,gw27));
        gameList.add(new Game(format.parse("2016-03-20 16:00:00"),tondela,belenenses,gw27));
        gameList.add(new Game(format.parse("2016-03-20 16:00:00"),nacionalMadeira,guimaraes,gw27));
        gameList.add(new Game(format.parse("2016-03-20 18:15:00"),boavista,benfica,gw27));
        gameList.add(new Game(format.parse("2016-03-20 20:30:00"),braga,uniaoMadeira,gw27));
        gameList.add(new Game(format.parse("2016-04-01 19:30:00"),benfica,braga,gw28));
        gameList.add(new Game(format.parse("2016-04-02 15:15:00"),arouca,academica,gw28));
        gameList.add(new Game(format.parse("2016-04-02 17:30:00"),maritimo,nacionalMadeira,gw28));
        gameList.add(new Game(format.parse("2016-04-02 19:45:00"),guimaraes,boavista,gw28));
        gameList.add(new Game(format.parse("2016-04-03 15:00:00"),uniaoMadeira,setubal,gw28));
        gameList.add(new Game(format.parse("2016-04-03 16:00:00"),estoril,pacos,gw28));
        gameList.add(new Game(format.parse("2016-04-03 18:15:00"),moreirense,rioAve,gw28));
        gameList.add(new Game(format.parse("2016-04-04 18:00:00"),porto,tondela,gw28));
        gameList.add(new Game(format.parse("2016-04-04 20:00:00"),belenenses,sporting,gw28));
        gameList.add(new Game(format.parse("2016-04-08 19:30:00"),boavista,arouca,gw29));
        gameList.add(new Game(format.parse("2016-04-09 16:30:00"),academica,benfica,gw29));
        gameList.add(new Game(format.parse("2016-04-09 19:45:00"),sporting,maritimo,gw29));
        gameList.add(new Game(format.parse("2016-04-10 15:00:00"),tondela,uniaoMadeira,gw29));
        gameList.add(new Game(format.parse("2016-04-10 15:00:00"),setubal,belenenses,gw29));
        gameList.add(new Game(format.parse("2016-04-10 15:00:00"),nacionalMadeira,estoril,gw29));
        gameList.add(new Game(format.parse("2016-04-10 17:15:00"),pacos,porto,gw29));
        gameList.add(new Game(format.parse("2016-04-10 19:30:00"),braga,moreirense,gw29));
        gameList.add(new Game(format.parse("2016-04-11 19:00:00"),rioAve,guimaraes,gw29));
        gameList.add(new Game(format.parse("2016-04-16 15:15:00"),arouca,rioAve,gw30));
        gameList.add(new Game(format.parse("2016-04-16 17:30:00"),estoril,boavista,gw30));
        gameList.add(new Game(format.parse("2016-04-16 19:45:00"),moreirense,sporting,gw30));
        gameList.add(new Game(format.parse("2016-04-17 15:00:00"),uniaoMadeira,pacos,gw30));
        gameList.add(new Game(format.parse("2016-04-17 15:00:00"),belenenses,academica,gw30));
        gameList.add(new Game(format.parse("2016-04-17 17:15:00"),maritimo,guimaraes,gw30));
        gameList.add(new Game(format.parse("2016-04-17 19:30:00"),porto,nacionalMadeira,gw30));
        gameList.add(new Game(format.parse("2016-04-18 18:00:00"),braga,tondela,gw30));
        gameList.add(new Game(format.parse("2016-04-18 20:00:00"),benfica,setubal,gw30));
        gameList.add(new Game(format.parse("2016-04-22 19:30:00"),boavista,belenenses,gw31));
        gameList.add(new Game(format.parse("2016-04-23 15:15:00"),academica,porto,gw31));
        gameList.add(new Game(format.parse("2016-04-23 17:30:00"),sporting,uniaoMadeira,gw31));
        gameList.add(new Game(format.parse("2016-04-23 19:45:00"),pacos,braga,gw31));
        gameList.add(new Game(format.parse("2016-04-24 15:00:00"),setubal,tondela,gw31));
        gameList.add(new Game(format.parse("2016-04-24 15:00:00"),maritimo,arouca,gw31));
        gameList.add(new Game(format.parse("2016-04-24 15:00:00"),nacionalMadeira,moreirense,gw31));
        gameList.add(new Game(format.parse("2016-04-24 17:15:00"),guimaraes,estoril,gw31));
        gameList.add(new Game(format.parse("2016-04-24 19:30:00"),rioAve,benfica,gw31));
        gameList.add(new Game(format.parse("2016-04-29 20:00:00"),braga,setubal,gw32));
        gameList.add(new Game(format.parse("2016-04-30 15:00:00"),belenenses,pacos,gw32));
        gameList.add(new Game(format.parse("2016-04-30 15:00:00"),benfica,guimaraes,gw32));
        gameList.add(new Game(format.parse("2016-04-30 15:00:00"),tondela,rioAve,gw32));
        gameList.add(new Game(format.parse("2016-04-30 17:30:00"),porto,sporting,gw32));
        gameList.add(new Game(format.parse("2016-05-01 15:00:00"),estoril,maritimo,gw32));
        gameList.add(new Game(format.parse("2016-05-01 15:00:00"),moreirense,boavista,gw32));
        gameList.add(new Game(format.parse("2016-05-01 17:15:00"),arouca,nacionalMadeira,gw32));
        gameList.add(new Game(format.parse("2016-05-01 19:30:00"),uniaoMadeira,academica,gw32));
        gameList.add(new Game(format.parse("2016-05-08 15:00:00"),nacionalMadeira,belenenses,gw33));
        gameList.add(new Game(format.parse("2016-05-08 15:00:00"),academica,braga,gw33));
        gameList.add(new Game(format.parse("2016-05-08 15:00:00"),pacos,tondela,gw33));
        gameList.add(new Game(format.parse("2016-05-08 15:00:00"),rioAve,porto,gw33));
        gameList.add(new Game(format.parse("2016-05-08 15:00:00"),sporting,setubal,gw33));
        gameList.add(new Game(format.parse("2016-05-08 15:00:00"),boavista,uniaoMadeira,gw33));
        gameList.add(new Game(format.parse("2016-05-08 18:15:00"),guimaraes,moreirense,gw33));
        gameList.add(new Game(format.parse("2016-05-08 19:30:00"),maritimo,benfica,gw33));
        gameList.add(new Game(format.parse("2016-05-09 19:00:00"),estoril,arouca,gw33));
        gameList.add(new Game(format.parse("2016-05-14 10:45:00"),porto,boavista,gw34));
        gameList.add(new Game(format.parse("2016-05-14 17:00:00"),arouca,guimaraes,gw34));
        gameList.add(new Game(format.parse("2016-05-14 18:30:00"),setubal,pacos,gw34));
        gameList.add(new Game(format.parse("2016-05-14 18:30:00"),uniaoMadeira,rioAve,gw34));
        gameList.add(new Game(format.parse("2016-05-14 18:30:00"),belenenses,estoril,gw34));
        gameList.add(new Game(format.parse("2016-05-14 18:30:00"),tondela,academica,gw34));
        gameList.add(new Game(format.parse("2016-05-15 14:00:00"),moreirense,maritimo,gw34));
        gameList.add(new Game(format.parse("2016-05-15 16:00:00"),braga,sporting,gw34));
        gameList.add(new Game(format.parse("2016-05-15 16:00:00"),benfica,nacionalMadeira,gw34));

        for(Game g: gameList){
            gameDAO.save(g);
        }
    }

    @Transactional
    public void populateVirtualTeamsUsers(){
        List <User> allUsers = new ArrayList<>();
        List <Player> allGR = playerRepo.findByPosition("GK");
        List <Player> allDEF = playerRepo.findByPosition("DEF");
        List <Player> allMID = playerRepo.findByPosition("MID");
        List <Player> allFOR = playerRepo.findByPosition("FOR");

        allUsers.add(new User("quim","quim@mail.pt","1"));
        allUsers.add(new User("besuntas","besuntas@mail.pt","1"));
        allUsers.add(new User("fagundes","fagundes@mail.pt","1"));
        allUsers.add(new User("badocha","badocha@mail.pt","1"));
        allUsers.add(new User("pogchamp","pogchamp@mail.pt","1"));
        allUsers.add(new User("kappa","kappa@mail.pt","1"));
        allUsers.add(new User("keepo","keepo@mail.pt","1"));
        allUsers.add(new User("dansgame","dansgame@mail.pt","1"));
        allUsers.add(new User("wutface","wutface@mail.pt","1"));
        allUsers.add(new User("opieop","opieop@mail.pt","1"));
        allUsers.add(new User("brokeback","brokeback@mail.pt","1"));
        allUsers.add(new User("frankerz","frankerz@mail.pt","1"));

        for (User u: allUsers){
            VirtualTeam vt = new VirtualTeam(u.getUsername() + " FC",1000,1);
            Random r = new SecureRandom();

            for(int i = 0; i < 6; i++){
                if(i < 2)
                    vt.addPlayer(allGR.remove(r.nextInt(allGR.size())));
                vt.addPlayer(allDEF.remove(r.nextInt(allDEF.size())));
                vt.addPlayer(allMID.remove(r.nextInt(allMID.size())));
                if( i < 4)
                    vt.addPlayer(allFOR.remove(r.nextInt(allFOR.size())));
            }

            vt.setOwner(u);
            u.setVirtualTeam(vt);
            userDAO.save(u);
            virtualTeamDAO.save(vt);
        }

    }

    @Transactional
    public HashMap<String,GameWeekSnapshot> genererateRandomSnapshots(GameWeek gw){
        HashMap<String,GameWeekSnapshot> allSnaps = new HashMap<>();
        List<VirtualTeam> allVTeams = virtualTeamDAO.findAll();
        List<Player> equipaTitular = new ArrayList<>();
        int totalGR = 0, totalDEF = 0, totalMID = 0, totalFOR = 0;

        for(VirtualTeam vt: allVTeams){
            List<Player> players = vt.getPlayers();

            Collections.shuffle(players, new SecureRandom());

            for(Player p: players){
                if(equipaTitular.size() == 11)
                    break;
                else{
                    switch (p.getPosition()){
                        case "GR":
                            if(totalGR == 0){
                                equipaTitular.add(p);
                                totalGR++;
                            }
                            break;
                        case "DEF":
                            if(totalDEF < 4){
                                equipaTitular.add(p);
                                totalDEF++;
                            }
                            break;
                        case "MID":
                            if(totalMID < 4){
                                equipaTitular.add(p);
                                totalMID++;
                            }
                            break;
                        case "FOR":
                            if(totalFOR < 2){
                                equipaTitular.add(p);
                                totalFOR++;
                            }
                            break;
                    }
                }
            }
            GameWeekSnapshot snap = new GameWeekSnapshot(equipaTitular,equipaTitular.get(0).getId(),0,gw,vt);
            allSnaps.put(vt.getName(),snap);
            snapDAO.save(snap);
        }
        return allSnaps;
    }

    @Transactional
    public void calculatePointsByWeek(GameWeek gw){

    }

}
