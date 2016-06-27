package com.fantasy.Services;

import com.fantasy.DAO.RealTeamDAO;
import com.fantasy.Models.Player;
import com.fantasy.Models.RealTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealTeamService {

    @Autowired
    private RealTeamDAO realTeamDAO;

    public List<RealTeam> getAllRealTeams(){ return (List<RealTeam>)realTeamDAO.findAll(); }

    public List<Player> getPlayersfromRealTeam(long id){return realTeamDAO.findById(id).getPlayers();}

    public RealTeam getById(long id){ return realTeamDAO.findById(id); }
}
