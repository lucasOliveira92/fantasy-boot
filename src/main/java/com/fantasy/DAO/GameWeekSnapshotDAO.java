package com.fantasy.DAO;


import com.fantasy.Models.GameWeekSnapshot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameWeekSnapshotDAO extends CrudRepository<GameWeekSnapshot, Long> {

    GameWeekSnapshot findById(long id);
    List<GameWeekSnapshot> findByVirtualTeamId(long virtual_team_id);
    List<GameWeekSnapshot> findByGameWeekId(long game_week_id);
}
