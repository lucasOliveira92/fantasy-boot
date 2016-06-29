package com.fantasy.DAO;


import com.fantasy.Models.Game;
import com.fantasy.Models.RealTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface GameDAO extends CrudRepository<Game, Long> {

    Game findById(long game_event_id);
    Set<Game> findByTeam1(RealTeam team1_ID);
    List<Game> findByGameWeekId(long game_week_id);
    List<Game> findAll();
}