package com.fantasy.Services;


import com.fantasy.DAO.GameWeekDAO;
import com.fantasy.Models.Game;
import com.fantasy.Models.GameWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameWeekService {
    @Autowired
    private GameWeekDAO gameWeeks;

    public GameWeek getGameWeekById(long id){ return gameWeeks.findById(id); }

    public GameWeek getGameWeekByNumber(int number){ return gameWeeks.findByNumber(number); }

    public GameWeek saveGameWeek(GameWeek gameWeek){ return gameWeeks.save(gameWeek); }

    public Iterable<GameWeek> getAllGameWeeks(){ return gameWeeks.findAll(); }

    public Set<Game> getGamesByGameWeekId(long id){ return getGameWeekById(id).getGames(); }

    public Set<Game> getGamesByGameWeekNumber(int number){ return getGameWeekByNumber(number).getGames(); }
}
