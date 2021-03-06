package com.fantasy.DAO;


import com.fantasy.Models.GameEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameEventDAO extends CrudRepository<GameEvent, Long> {

    GameEvent findById(long game_event_id);
    List<GameEvent> findByGameIdAndPlayerId(long game_id,long player_id);
    void deleteByGameId(long id);

}
