package com.fantasy;

import com.fantasy.Models.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findById(long player_id);
}