package com.fantasy.Controllers;

import com.fantasy.Models.Player;
import com.fantasy.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Lucas on 23-Jun-16.
 */

@Controller
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public String list(Model model){
        Iterable<Player> allPlayers = playerService.getAllPlayers();
        /*

        for(Player p: allPlayers){
            System.out.println(p.getName());
        }*/

        model.addAttribute("players", allPlayers);
        return "player/players";
    }

    @RequestMapping("player/{id}")
    public String showUser(@PathVariable Integer id, Model model){
        Player p = playerService.getPlayerById(id);
        model.addAttribute("player",p);
        return "player/show";
    }
}
