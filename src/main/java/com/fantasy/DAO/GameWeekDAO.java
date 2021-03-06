package com.fantasy.DAO;


import com.fantasy.Models.GameWeek;
import org.springframework.data.repository.CrudRepository;

public interface GameWeekDAO extends CrudRepository<GameWeek, Long> {

    GameWeek findById(long game_week_id);
    GameWeek findByNumber(int number);
}