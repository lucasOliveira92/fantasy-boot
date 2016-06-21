package com.fantasy.DAO;

import com.fantasy.Models.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerDAO extends CrudRepository<Player, Long> {

    Player findById(long player_id);
    Player findByName(String name);
}