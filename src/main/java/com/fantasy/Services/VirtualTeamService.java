package com.fantasy.Services;

import com.fantasy.DAO.VirtualTeamDAO;
import com.fantasy.Models.Player;
import com.fantasy.Models.User;
import com.fantasy.Models.VirtualTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


        vt.removePlayerB(out);
        vt.addPlayerB(in);


        virtualTeams.save(vt);
        return virtualTeams.findById(virtual_team_id);
    }


}
