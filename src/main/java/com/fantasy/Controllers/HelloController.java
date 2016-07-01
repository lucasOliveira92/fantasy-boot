package com.fantasy.Controllers;

import com.fantasy.Models.*;
import com.fantasy.Services.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HelloController {
    @Autowired
    private UserService gestorUser;

    @Autowired
    GenerateService generateService;

    @Autowired
    VirtualTeamService gestorTeam;

    @Autowired
    PlayerService playerService;

    @Autowired
    RealTeamService gestorRealTeams;

    @Autowired
    SnapshotService snapService;

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


    @RequestMapping("/team/maketransfers")
    public String testManagement(Model model){
        User u = gestorUser.getUserByUsername("Quim");
        User u2 = gestorUser.getUserByUsername("besuntas");
        model.addAttribute("currentUser", u);
        if (u != null) {
            VirtualTeam vt = u.getVirtualTeam();
            long newId = vt.getPlayers().get(0).getId();
            long oldId = u2.getVirtualTeam().getPlayers().get(0).getId();
            if (u.hasVirtualTeam()) {
                VirtualTeam team = gestorTeam.doTransfer(playerService.getPlayerById(newId),playerService.getPlayerById(oldId),vt.getId());
                ArrayList<Player> lista = new ArrayList<>(playerService.getAllPlayers());
                lista.removeAll(u.getTeam().getPlayers());

                List<List<Player>> listFormation = gestorTeam.getListsAllPlayersByPosition(team.getId());
                model.addAttribute("gks", listFormation.get(0));
                model.addAttribute("defs", listFormation.get(1));
                model.addAttribute("mids", listFormation.get(2));
                model.addAttribute("fors", listFormation.get(3));
                model.addAttribute("realTeams", gestorRealTeams.getAllRealTeams());
                model.addAttribute("players", lista);
                model.addAttribute("team", vt);
                return "virtualTeam/transfers";
            } else {
                return "redirect:/team/new";
            }
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/team/saveteam")
    public String testSave(){
        User u = gestorUser.getUserByUsername("Quim");
        VirtualTeam vt = u.getVirtualTeam();
        GameWeekSnapshot snap = snapService.getLastSnapshotByUser(u.getId());
        System.out.println("Players");
        if(snap != null){
            snap.setCapitao(snap.getCapitao());
            snap.setPlayers(snap.getPlayers());
            snapService.saveSnap(snap);
        }
        return "home";
    }


    @RequestMapping("/generate/{gameWeek}")
    public String generate(@PathVariable Integer gameWeek) {
        if(gameWeek > 0 && gameWeek <= 34){
            generateService.generate(gameWeek);
            if(gameWeek < 34)
                generateService.genererateRandomSnapshots(gameWeek + 1 );
        }

        return "home";
    }


    //@Secured("ROLE_ADMIN")
    @RequestMapping("/seed")
    public String seed() {
        try {
            seed1();
            seed2();
            generateService.genererateRandomSnapshots(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }

    public String seed1() {
        try {
            generateService.populateRealTeamsPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }


    public String seed2() {
        try {
            generateService.populateVirtualTeamsUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }

}