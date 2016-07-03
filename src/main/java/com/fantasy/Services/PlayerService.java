package com.fantasy.Services;

import com.fantasy.DAO.GameDAO;
import com.fantasy.DAO.GameEventDAO;
import com.fantasy.DAO.GameWeekDAO;
import com.fantasy.DAO.PlayerDAO;
import com.fantasy.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class PlayerService {

    @Autowired
    private PlayerDAO playerDAO;
    @Autowired
    private GameEventDAO gameEventDAO;
    @Autowired
    private GameWeekDAO gameWeekDAO;
    @Autowired
    private GameDAO gameDAO;

    public List<Player> getAllPlayers() {
        return (List<Player>) playerDAO.findAll();
    }

    public Player getPlayerById(long id) {
        return playerDAO.findById(id);
    }

    public List<Player> getAllPlayersByCost() {
        return playerDAO.findAllByOrderByCostDesc();
    }

    public List<Player> getAllPlayersByYellow() {
        return playerDAO.findAllByOrderByYellowTotalDesc();
    }

    public List<Player> getAllPlayersByRed() {
        return playerDAO.findAllByOrderByRedTotalDesc();
    }

    public List<Player> getAllPlayersByGoalScored() {
        return playerDAO.findAllByOrderByGoalTotalDesc();
    }

    public List<Player> getAllPlayersByTimesBought() {
        return playerDAO.findAllByOrderByTotalTimesBoughtDesc();
    }

    public List<Player> getAllPlayersByTotalPoints() {
        return playerDAO.findAllByOrderByTotalPointsDesc();
    }

    public List<Player> getAllPlayersByPosition(String position) {
        return playerDAO.findByPosition(position);
    }

    public List<GameEvent> getAllGameEventsFromGameAndPlayerIds(long game_id, long player_id) {
        return gameEventDAO.findByGameIdAndPlayerId(game_id, player_id);
    }

    public List<GameEvent> getAllGameEventsFromGameWeekNumberAndPlayerId(int number, long player_id) {
        GameWeek gameWeek = gameWeekDAO.findByNumber(number);
        Player player = playerDAO.findById(player_id);
        RealTeam team = player.getRealTeam();
        Game game = new Game();
        ArrayList<Game> listGames = new ArrayList<Game>(gameDAO.findByGameWeekId(gameWeek.getId()));
        for (Game g : listGames) {
            if (g.getTeam1().getId().equals(team.getId()) || g.getTeam2().getId().equals(team.getId())) {
                game = g;
            }
        }
        return getAllGameEventsFromGameAndPlayerIds(game.getId(), player_id);
    }


    public List<Player> getAllPlayersExceptFromTeam(long virtual_team_id) {
        return playerDAO.findAllPlayersExceptFromTeam(virtual_team_id);
    }

    public HashMap<Player, List<Integer>> getHashOfListEventsByPlayerId(HashMap<Long, List<GameEvent>> lista) {
        HashMap<Player, List<Integer>> listaFinal = new HashMap<>();
        List<Long> listaIds = new ArrayList<>(lista.keySet());
        for (int i = 0; i < lista.size(); i++) {
            int yellow = 0;
            int red = 0;
            int goalScored = 0;
            int cleanSheet = 0;
            int teamResult = 0;
            int totalPoints = 0;
            long id = listaIds.get(i);
            Player player = playerDAO.findById(id);
            String position = player.getPosition();
            for (GameEvent gE : lista.get(id)) {
                switch (gE.getType()) {
                    case "YELLOW CARD":
                        yellow -= 3;
                        break;
                    case "RED CARD":
                        red -= 5;
                        break;
                    case "WIN":
                        teamResult += 2;
                        break;
                    case "LOSE":
                        teamResult -= 2;
                        break;
                }
                switch (position) {
                    case "GK":
                        switch (gE.getType()) {
                            case "GOAL GK":
                                goalScored += 99999;
                                break;
                            case "NO GOALS GK":
                                cleanSheet += 4;
                                break;
                        }
                        break;
                    case "DEF":
                        switch (gE.getType()) {
                            case "GOAL DEF":
                                goalScored += 5;
                                break;
                            case "NO GOALS DEF":
                                cleanSheet += 3;
                                break;
                        }
                        break;
                    case "MID":
                        switch (gE.getType()) {
                            case "GOAL MID":
                                goalScored += 4;
                                break;
                            case "NO GOALS MID":
                                cleanSheet += 2;
                                break;
                        }
                        break;
                    case "FOR":
                        switch (gE.getType()) {
                            case "GOAL FOR":
                                goalScored += 3;
                                break;
                            case "NO GOALS FOR":
                                cleanSheet += 1;
                                break;
                        }
                        break;
                }
                // System.out.println("zzzzzzzzzz   "+id+"  aaaaaa  "+position+"  hjhjhjh   "+gE.getType());
            }
            totalPoints = yellow + red + cleanSheet + teamResult + goalScored;
            List<Integer> listaEventsPlayer = new ArrayList<>();
            listaEventsPlayer.add(goalScored);
            listaEventsPlayer.add(yellow);
            listaEventsPlayer.add(red);
            listaEventsPlayer.add(teamResult);
            listaEventsPlayer.add(cleanSheet);
            listaEventsPlayer.add(totalPoints);
            listaFinal.put(player, listaEventsPlayer);
        }
        return listaFinal;
    }
}


