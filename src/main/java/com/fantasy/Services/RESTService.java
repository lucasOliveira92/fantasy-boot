package com.fantasy.Services;

import com.fantasy.DAO.GameDAO;
import com.fantasy.Models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lucas on 29-Jun-16.
 */
@Service
public class RESTService {
    @Autowired
    GameDAO gamesDAO;

    public String serveREST(){
        List<Game> all = gamesDAO.findAll();
        String res="[";
        //
        for(Game g: all){
            res+="{";
            res+="\"gameweek\":" + "\""+g.getGameWeek().getNumber() + "\"" +",";
            res+="\"date\":"+ "\""+g.getDate().toString()+ "\""+",";
            res+="\"team1\":" + "\""+g.getTeam1().getName()+ "\""+",";
            res+="\"team2\":" + "\""+g.getTeam2().getName()+ "\""+"},";
        }
        String newRes =  res.substring(0,res.length()-1);
        newRes+="]";
        return newRes;
    }

}
