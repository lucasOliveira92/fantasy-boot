package com.fantasy.Controllers;

import com.fantasy.DAO.GameDAO;
import com.fantasy.Models.Game;
import com.fantasy.Models.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Lucas on 29-Jun-16.
 */
@RestController
public class JSONController {
    @Autowired
    GameDAO gamesDAO;

    @RequestMapping("/api/games")
    public String serveAPI() {
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
