package com.fantasy.DAO;


import com.fantasy.Models.GameWeekSnapshot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameWeekSnapshotDAO extends CrudRepository<GameWeekSnapshot, Long> {

    GameWeekSnapshot findById(long id);
    GameWeekSnapshot findByGameWeekIdAndVirtualTeamId(long game_week_id,long virtual_team_id);
  //  Iterable<GameWeekSnapshot> findByVirtualTeamId(long virtual_team_id);
  //  Iterable<GameWeekSnapshot> findByGameWeekId(long game_week_id);
        List<GameWeekSnapshot> findByVirtualTeamId(long virtual_team_id);
        List<GameWeekSnapshot> findByGameWeekId(long game_week_id);
}
