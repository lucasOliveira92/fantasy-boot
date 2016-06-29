package com.fantasy.DAO;

import com.fantasy.Models.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerDAO extends CrudRepository<Player, Long> {

    Player findById(long player_id);
    Player findByName(String name);
    List<Player> findAllByOrderByCostDesc();
    List<Player> findAllByOrderByYellowTotalDesc();
    List<Player> findAllByOrderByRedTotalDesc();
    List<Player> findAllByOrderByGoalTotalDesc();
    List<Player> findAllByOrderByTotalTimesBoughtDesc();
    List<Player> findAllByOrderByTotalPointsDesc();
    List<Player> findByPosition(String position);
}