package com.fantasy.Controllers;

import com.fantasy.Models.GameWeek;
import com.fantasy.Models.User;
import com.fantasy.Services.GameWeekService;
import com.fantasy.Services.GenerateService;
import com.fantasy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    GameWeekService gameWeekService;

    @Autowired
    GenerateService generateService;

    @Autowired
    private UserService gestorUser;

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String greeting(Model model) {
        Iterable<GameWeek> allGameWeeks = gameWeekService.getAllGameWeeks();
        Iterator<GameWeek> it = allGameWeeks.iterator();
        while(it.hasNext()){
            GameWeek gw = it.next();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        List<Integer> lista = new ArrayList<>();
        int tot = gestorUser.getUserByUsername("Quim").getVirtualTeam().getGameWeekSnapshots().size()-1;

        model.addAttribute("currentUser", u);
        model.addAttribute("nextgameweek",gameWeekService.getGameWeekByNumber(tot+1));
        model.addAttribute("oldgameweek",gameWeekService.getGameWeekByNumber(tot));
        model.addAttribute("games",gameWeekService.getGamesByGameWeekNumber(tot+1));
        model.addAttribute("oldgameweekNumber", tot);
        model.addAttribute("nextgameweekNumber", tot+1);
        return "admin";
    }
    @RequestMapping(value = "admin/generate")
    public String generate(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        List<Integer> lista = new ArrayList<>();
        int tot = gestorUser.getUserByUsername("Quim").getVirtualTeam().getGameWeekSnapshots().size();

        if(gameWeekService.getTotalGeneratedWeeks() >= tot)
            return "home";
        if(tot > 0 && tot <= 34){
            generateService.generate(tot);
            if(tot < 34)
                generateService.genererateRandomSnapshots(tot + 1 );
        }
        model.addAttribute("currentUser", u);
        model.addAttribute("nextgameweek",gameWeekService.getGameWeekByNumber(tot+1));
        model.addAttribute("oldgameweek",gameWeekService.getGameWeekByNumber(tot));
        model.addAttribute("games",gameWeekService.getGamesByGameWeekNumber(tot+1));
        model.addAttribute("oldgameweekNumber", tot);
        model.addAttribute("nextgameweekNumber", tot+1);
        return "admin";
    }
}
