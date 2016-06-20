package com.fantasy;


import com.fantasy.Models.Game;
import com.fantasy.Models.RealTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GameRepository extends CrudRepository<Game, Long> {

    Game findById(long game_event_id);
    Set<Game> findByTeam1(RealTeam team1_ID);
}