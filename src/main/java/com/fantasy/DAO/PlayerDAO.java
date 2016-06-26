package com.fantasy.DAO;

import com.fantasy.Models.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerDAO extends CrudRepository<Player, Long> {

    Player findById(long player_id);
    Player findByName(String name);
    List<Player> findAllByOrderByCostDesc();
}