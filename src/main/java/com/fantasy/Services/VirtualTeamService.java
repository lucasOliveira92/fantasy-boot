package com.fantasy.Services;

import com.fantasy.DAO.VirtualTeamDAO;
import com.fantasy.Models.GameWeekSnapshot;
import com.fantasy.Models.Player;
import com.fantasy.Models.User;
import com.fantasy.Models.VirtualTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
@Transactional
public class VirtualTeamService {
    @Autowired
    private VirtualTeamDAO virtualTeams;
    @Autowired
    private SnapshotService snapshotService;
    
    public VirtualTeam createVirtualTeam(String nome){
        return virtualTeams.save(new VirtualTeam(nome,1000,2));
    }
    
    public VirtualTeam getVirtualTeam(long userId){
        return virtualTeams.findByUserId(userId);
    }
    
    public VirtualTeam saveVirtualTeam(VirtualTeam vt){
        return virtualTeams.save(vt);
    }

    public List<VirtualTeam> getAllTeamsOrderedByPoints(){ return virtualTeams.findAllByOrderByTotalPointsDesc(); }

    public List<VirtualTeam> getAllVirtualTeams(){ return (List<VirtualTeam>) virtualTeams.findAll();}

    public User getUserById(long id){ return virtualTeams.findByUserId(id).getOwner(); }

    public VirtualTeam doTransfer(Player in, Player out, long virtual_team_id){
        VirtualTeam vt = virtualTeams.findById(virtual_team_id);

        if((in.getPosition().equals(out.getPosition()) && (vt.getBudget()>(out.getCost()-in.getCost())))){
            vt.removePlayerB(out);
            vt.addPlayerB(in);
            GameWeekSnapshot snap = vt.getLastSnapshot();
            List<Player> players = snap.getPlayers();
            List<Player> newPlayers = new ArrayList<>();

            for (Player p: players) {
                if(p.getId() == out.getId()){
                    if(p.getId() == snap.getCapitao()){
                        snap.setCapitao(in.getId());

                    }
                    newPlayers.add(in);
                }
                else{
                    newPlayers.add(p);
                }
            }
            snap.setPlayers(newPlayers);
            snapshotService.saveSnap(snap);
        }

        virtualTeams.save(vt);
        return virtualTeams.findById(virtual_team_id);
    }

    public List<List<Player>> getListsPlayersByPositionByFormation(long virtual_team_id){
        VirtualTeam team = virtualTeams.findById(virtual_team_id);
        List<Player> formation = team.getLastTeamFormation();
        ArrayList<Player> gks = new ArrayList<>();
        ArrayList<Player> defs = new ArrayList<>();
        ArrayList<Player> mids = new ArrayList<>();
        ArrayList<Player> fors = new ArrayList<>();
        List<List<Player>> listaFinal = new ArrayList<>();
        for (Player p : formation) {
            switch (p.getPosition()){
                case "GK":
                    gks.add(p);
                    break;
                case "DEF":
                    defs.add(p);
                    break;
                case "MID":
                    mids.add(p);
                    break;
                case "FOR":
                    fors.add(p);
                    break;
            }
        }
        listaFinal.add(gks);
        listaFinal.add(defs);
        listaFinal.add(mids);
        listaFinal.add(fors);
        return listaFinal;
    }

    public List<List<Player>> getListsPlayersByPositionBySubstitutes(long virtual_team_id){
        VirtualTeam team = virtualTeams.findById(virtual_team_id);
        List<Player> formation = team.getLastTeamFormation();
        List<Player> substitutes = new ArrayList<>(team.getPlayers());
        substitutes.removeAll(formation);
        ArrayList<Player> gks = new ArrayList<>();
        ArrayList<Player> defs = new ArrayList<>();
        ArrayList<Player> mids = new ArrayList<>();
        ArrayList<Player> fors = new ArrayList<>();
        List<List<Player>> listaFinal = new ArrayList<>();
        for (Player p : substitutes) {
            switch (p.getPosition()){
                case "GK":
                    gks.add(p);
                    break;
                case "DEF":
                    defs.add(p);
                    break;
                case "MID":
                    mids.add(p);
                    break;
                case "FOR":
                    fors.add(p);
                    break;
            }
        }
        listaFinal.add(gks);
        listaFinal.add(defs);
        listaFinal.add(mids);
        listaFinal.add(fors);
        return listaFinal;
    }

    public List<List<Player>> getListsAllPlayersByPosition(long virtual_team_id){
        List<Player> formation = virtualTeams.findById(virtual_team_id).getPlayers();
        ArrayList<Player> gks = new ArrayList<>();
        ArrayList<Player> defs = new ArrayList<>();
        ArrayList<Player> mids = new ArrayList<>();
        ArrayList<Player> fors = new ArrayList<>();
        List<List<Player>> listaFinal = new ArrayList<>();
        for (Player p : formation) {
            switch (p.getPosition()){
                case "GK":
                    gks.add(p);
                    break;
                case "DEF":
                    defs.add(p);
                    break;
                case "MID":
                    mids.add(p);
                    break;
                case "FOR":
                    fors.add(p);
                    break;
            }
        }
        listaFinal.add(gks);
        listaFinal.add(defs);
        listaFinal.add(mids);
        listaFinal.add(fors);
        return listaFinal;
    }


}
