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

    public GameWeekSnapshot getGameWeekSnapshotById(long id){ return gameWeekSnapshots.findById(id); }

    public List<GameWeekSnapshot> getGameWeekSnapshotsByGameWeekId(long game_week_id){
        return gameWeekSnapshots.findByGameWeekId(game_week_id);
    }

    public List<GameWeekSnapshot> getGameWeekSnapshotsOrderedCumulative(long game_week_id){
        List<GameWeekSnapshot> gameWekk = gameWeekSnapshots.findByGameWeekId(game_week_id);
        return null;
    }

 /*   public Iterable<GameWeekSnapshot> getAllGameWeekSnapshotsByTeamId(long virtual_team_id){ return gameWeekSnapshots.findByVirtualTeamId(virtual_team_id); }

    public Iterable<GameWeekSnapshot> getAllGameWeekSnapshotsByGameWeekId(long game_week_id){ return gameWeekSnapshots.findByGameWeekId(game_week_id); }

   // public GameWeekSnapshot saveGameWeekSnapshot(GameWeekSnapshot gameWeekSnapshot){ return gameWeekSnapshots.save(gameWeekSnapshot); }

    // Provavelmente deve ser só 1 elemento, mas e preferivel fazer assim
    public ArrayList<GameWeekSnapshot> getGameWeekSnapshotsByTeamAndGameWeek(long game_week_id, long virtual_team_id){
        ArrayList<GameWeekSnapshot> lista = (ArrayList<GameWeekSnapshot>) gameWeekSnapshots.findByGameWeekId(game_week_id);
        lista.removeAll((ArrayList<GameWeekSnapshot>) gameWeekSnapshots.findByVirtualTeamId(virtual_team_id));
        return lista;
    }

    public long getPointsByTeamId(long virtual_team_id){
        long points = 0;
        ArrayList<GameWeekSnapshot> lista = (ArrayList<GameWeekSnapshot>) gameWeekSnapshots.findByVirtualTeamId(virtual_team_id);
        for(GameWeekSnapshot gameWeekS : lista) {
            points += gameWeekS.getGameWeekPoints();
        }
        return points;
    }

    public long getPointsByTeamAndGameWeek(long virtual_team_id, long game_week_id){
        long points = 0;
        ArrayList<GameWeekSnapshot> lista = getGameWeekSnapshotsByTeamAndGameWeek(game_week_id,virtual_team_id);
        for(GameWeekSnapshot gameWeekS : lista) {
            points += gameWeekS.getGameWeekPoints();
        }
        return points;
    }*/
}
