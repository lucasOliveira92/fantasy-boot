package com.fantasy.DAO;


import com.fantasy.Models.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameDAO extends CrudRepository<Game, Long> {

    Game findById(long game_event_id);
    List<Game> findByGameWeekId(long game_week_id);
    List<Game> findAll();
}