package com.fantasy.Services;

import com.fantasy.DAO.PlayerDAO;
import com.fantasy.Models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    public List<Player> getAllPlayers() { return (List<Player>)playerDAO.findAll(); }

    public Player getPlayerById(long id){
        return playerDAO.findById(id);
    }

    public List<Player> getAllPlayersByCost(){ return playerDAO.findAllByOrderByCostDesc();}

    public List<Player> getAllPlayersByYellow(){ return playerDAO.findAllByOrderByYellowTotalDesc();}

    public List<Player> getAllPlayersByRed(){ return playerDAO.findAllByOrderByRedTotalDesc();}

    public List<Player> getAllPlayersByGoalScored(){ return playerDAO.findAllByOrderByGoalTotalDesc();}

    public List<Player> getAllPlayersByTimesBought(){ return playerDAO.findAllByOrderByTotalTimesBoughtDesc();}

    public List<Player> getAllPlayersByTotalPoints(){ return playerDAO.findAllByOrderByTotalPointsDesc();}

    public List<Player> getAllPlayersByPosition(String position){ return playerDAO.findByPosition(position); }
}
