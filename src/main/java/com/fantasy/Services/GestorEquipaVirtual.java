package com.fantasy.Services;

import com.fantasy.Models.Player;
import com.fantasy.Models.VirtualTeam;
import com.fantasy.Repositories.VirtualTeamRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class GestorEquipaVirtual {
    @Autowired
    private VirtualTeamRepository virtualTeams;
    
    public VirtualTeam criarEquipaVirtual(String nome){
        return virtualTeams.save(new VirtualTeam(nome,1000,2));
    }
    
    public VirtualTeam getEquipaVirtual(int utilizador){
        return virtualTeams.findByUtilizador(utilizador);
    }
    
    public VirtualTeam guardaEquipaVirtual(VirtualTeam vt){
        return virtualTeams.save(vt);
    }
    
    public VirtualTeam realizarTransferencia(List<Player> in, List<Player> out, int virtual_team_id) throws Exception{
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
    
}
