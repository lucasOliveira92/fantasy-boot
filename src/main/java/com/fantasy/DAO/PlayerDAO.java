package com.fantasy.DAO;

import com.fantasy.Models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerDAO extends CrudRepository<Player, Long>, JpaRepository<Player,Long> {

    Player findById(long player_id);
    List<Player> findAllByOrderByCostDesc();
    List<Player> findAllByOrderByYellowTotalDesc();
    List<Player> findAllByOrderByRedTotalDesc();
    List<Player> findAllByOrderByGoalTotalDesc();
    List<Player> findAllByOrderByTotalTimesBoughtDesc();
    List<Player> findAllByOrderByTotalPointsDesc();
    List<Player> findByPosition(String position);

    @Query(value = "SELECT p.player_id,p.cost,p.goal_total,p.name,p.position,p.red_total,p.total_points,p.yellow_total,p.total_times_bought,p.real_team_id FROM players p WHERE p.player_id NOT IN (SELECT v.player_id FROM virtual_team_players v WHERE v.virtual_team_id = :virtual_team_id)", nativeQuery = true)
    List<Player> findAllPlayersExceptFromTeam(@Param("virtual_team_id") long virtual_team_id);
}