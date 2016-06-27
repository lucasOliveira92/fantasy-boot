package com.fantasy.DAO;


import com.fantasy.Models.GameWeek;
import com.fantasy.Models.GameWeekSnapshot;
import org.springframework.data.repository.CrudRepository;

public interface GameWeekSnapshotDAO extends CrudRepository<GameWeekSnapshot, Long> {

    GameWeekSnapshot findById(long id);
  //  Iterable<GameWeekSnapshot> findByVirtualTeamId(long virtual_team_id);
  //  Iterable<GameWeekSnapshot> findByGameWeekId(long game_week_id);
}
