package com.fantasy.Services;

import com.fantasy.DAO.PlayerDAO;
import com.fantasy.Models.Player;
import com.fantasy.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lucas on 23-Jun-16.
 */

@Service
public class PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    public Iterable<Player> getAllPlayers() {
        return playerDAO.findAll();
    }

    public Player getPlayerById(long id){
        return playerDAO.findById(id);
    }
}
