package com.fantasy.Services;

import com.fantasy.DAO.GameWeekSnapshotDAO;
import com.fantasy.DAO.VirtualTeamDAO;
import com.fantasy.Models.GameWeekSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SnapshotService {
    @Autowired
    VirtualTeamDAO virtualTeamDAO;

    @Autowired
    VirtualTeamService virtualTeamService;

    @Autowired
    GameWeekSnapshotDAO gameWeekSnapshotDAO;

    public GameWeekSnapshot getLastSnapshotByUser(long id){
        return virtualTeamService.getUserById(id).getVirtualTeam().getLastSnapshot();
    }

    public void saveSnap(GameWeekSnapshot gws){
        gameWeekSnapshotDAO.save(gws);
    }

    public GameWeekSnapshot getSnapshotByGameWeekIdAndVirtualTeamId(long gameweekId, long virtualTeamId){
        return gameWeekSnapshotDAO.findByGameWeekIdAndVirtualTeamId(gameweekId,virtualTeamId);
    }
}
