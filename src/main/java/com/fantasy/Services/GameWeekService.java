package com.fantasy.Services;


import com.fantasy.DAO.GameWeekDAO;
import com.fantasy.DAO.UserDAO;
import com.fantasy.Models.Game;
import com.fantasy.Models.GameWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameWeekService {
    @Autowired
    private GameWeekDAO gameWeeks;
    @Autowired
    private UserDAO userDAO;

    public GameWeek getGameWeekById(long id){ return gameWeeks.findById(id); }

    public GameWeek getGameWeekByNumber(int number){ return gameWeeks.findByNumber(number); }

    public Iterable<GameWeek> getAllGameWeeks(){ return gameWeeks.findAll(); }

    public List<Game> getGamesByGameWeekNumber(int number){ return getGameWeekByNumber(number).getGames(); }

    public int getTotalGeneratedWeeks(){
        return userDAO.findByUsername("Quim").getVirtualTeam().getGameWeekSnapshots().size()-1;
    }
}
