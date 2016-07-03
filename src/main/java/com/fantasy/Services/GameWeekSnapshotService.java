package com.fantasy.Services;


import com.fantasy.DAO.GameWeekSnapshotDAO;
import com.fantasy.Models.GameWeekSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameWeekSnapshotService {
    @Autowired
    private GameWeekSnapshotDAO gameWeekSnapshots;

    public List<GameWeekSnapshot> getGameWeekSnapshotsOrderedCumulative(long game_week_id){
        return gameWeekSnapshots.findByGameWeekIdOrderByGameWeekCumulativePointsDesc(game_week_id);
    }

}
