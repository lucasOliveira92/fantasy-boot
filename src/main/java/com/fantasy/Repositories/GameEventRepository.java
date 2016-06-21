package com.fantasy.Repositories;


import com.fantasy.Models.GameEvent;
import com.fantasy.Models.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GameEventRepository extends CrudRepository<GameEvent, Long> {

    GameEvent findById(long game_event_id);
    Set<GameEvent> findByPlayer(Player player);
}
