package com.fantasy.Services;


import com.fantasy.DAO.GameWeekDAO;
import com.fantasy.Models.GameWeek;
import org.springframework.beans.factory.annotation.Autowired;

public class GameWeekService {
    @Autowired
    private GameWeekDAO gameWeeks;

    public GameWeek getGameWeekById(long id){ return gameWeeks.findById(id); }

    public GameWeek getGameWeekByNumber(int number){ return gameWeeks.findByNumber(number); }

    public GameWeek saveGameWeek(GameWeek gameWeek){ return gameWeeks.save(gameWeek); }

    public Iterable<GameWeek> getAllGameWeeks(){ return gameWeeks.findAll(); }
}
