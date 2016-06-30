package com.fantasy.Services;

import com.fantasy.DAO.VirtualTeamDAO;
import com.fantasy.Models.Player;
import com.fantasy.Models.User;
import com.fantasy.Models.VirtualTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class VirtualTeamService {
    @Autowired
    private VirtualTeamDAO virtualTeams;
    
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
    
    public VirtualTeam doTransfer(List<Player> in, List<Player> out, int virtual_team_id) throws Exception{
        VirtualTeam vt = virtualTeams.findById(virtual_team_id);
        
        int grIN=0,defIN=0,medIN=0,avaIN=0;
        int grOUT=0,defOUT=0,medOUT=0,avaOUT=0;
        if(in.size() != out.size())
            throw new Exception("Número de jogadores é diferente");
        for(Player p: in){
            switch(p.getPosition()){
                case "GR": grIN++;break;
                case "DEF": defIN++;break;
                case "MED": medIN++;break;
                case "AVA": avaIN++;break;
            }
        }
        for(Player p: out){
            switch(p.getPosition()){
                case "GR": grOUT++;break;
                case "DEF": defOUT++;break;
                case "MED": medOUT++;break;
                case "AVA": avaOUT++;break;
            }
        }
        if(!(grOUT == grIN && defIN == defOUT && medIN == medOUT && avaIN == avaOUT)){
            throw new Exception("Posições erradas");
        }
        
        for(Player p: in){
            vt.addPlayer(p);
        }
        for(Player p: out){
            vt.removePlayer(p);
        }
        
        return virtualTeams.save(vt);
    }

    public VirtualTeam doTransfer(Player in, Player out, long virtual_team_id){
        VirtualTeam vt = virtualTeams.findById(virtual_team_id);

        if((in.getPosition().equals(out.getPosition()) && (vt.getBudget()>(out.getCost()-in.getCost())))){
            vt.removePlayerB(out);
            vt.addPlayerB(in);

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
