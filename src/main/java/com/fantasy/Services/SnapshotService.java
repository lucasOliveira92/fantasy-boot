package com.fantasy.Services;

import com.fantasy.DAO.GameWeekSnapshotDAO;
import com.fantasy.DAO.VirtualTeamDAO;
import com.fantasy.Models.GameWeekSnapshot;
import com.fantasy.Models.VirtualTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lucas on 30-Jun-16.
 */
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
}
