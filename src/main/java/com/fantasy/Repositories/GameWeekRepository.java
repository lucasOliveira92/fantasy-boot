package com.fantasy.Repositories;


import com.fantasy.Models.GameWeek;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GameWeekRepository extends CrudRepository<GameWeek, Long> {

    GameWeek findById(long game_week_id);
    Set<GameWeek> findByNumber(int number);
}