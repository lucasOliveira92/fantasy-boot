package com.fantasy.Controllers;

import com.fantasy.Models.User;
import com.fantasy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fantasy.Services.GenerateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HelloController {
    @Autowired
    private UserService gestorUser;

    @Autowired
    GenerateService generateService;

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);
        return "home";
    }
    
    //@Secured("ROLE_USER")
    @RequestMapping("/home")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }

    @RequestMapping("/generate/{gameWeek}")
    public String generate(@PathVariable Integer gameWeek) {
        if(gameWeek > 0 && gameWeek <= 34){
            generateService.genererateRandomSnapshots(gameWeek);
            generateService.generate(gameWeek);
        }

        return "home";
    }

    @RequestMapping("/seed")
    public String seed() {
        try {
            seed1();
            seed2();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping("/seed1")
    public String seed1() {
        try {
            generateService.populateRealTeamsPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }
    @RequestMapping("/seed2")
    public String seed2() {
        try {
            generateService.populateVirtualTeamsUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }

}