package com.fantasy.Controllers;

import com.fantasy.DAO.PlayerDAO;
import com.fantasy.Models.*;
import com.fantasy.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    GameWeekService gameWeekService;

    @Autowired
    PlayerDAO playerDAO;

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);
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
                VirtualTeam team2 = gestorTeam.doTransfer(playerService.getPlayerById(oldId),playerService.getPlayerById(newId),u2.getVirtualTeam().getId());
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
        return "redirect:/";
    }

    @RequestMapping(value = "team/history", method = RequestMethod.GET)
    public String historicVirtualTeam(Model model) {

        User u = gestorUser.getUserByUsername("Quim");
        model.addAttribute("currentUser", u);
        int tot = gestorUser.getUserByUsername("Quim").getVirtualTeam().getGameWeekSnapshots().size() - 1;
        if (u != null) {
            if (u.hasVirtualTeam()) {
                VirtualTeam team = gestorTeam.getVirtualTeam(u.getId());
                List<List<Player>> listFormation = gestorTeam.getListsPlayersByPositionByFormation(u.getVirtualTeam().getId());
                HashMap<Long, List<GameEvent>> listEventsByPlayer = new HashMap<>();
                if (tot > 0) {
                    for (Player p : listFormation.get(0)) {
                        listEventsByPlayer.put(p.getId(), playerService.getAllGameEventsFromGameWeekNumberAndPlayerId(tot, p.getId()));
                    }
                    for (Player p : listFormation.get(1)) {
                        listEventsByPlayer.put(p.getId(), playerService.getAllGameEventsFromGameWeekNumberAndPlayerId(tot, p.getId()));
                    }
                    for (Player p : listFormation.get(2)) {
                        listEventsByPlayer.put(p.getId(), playerService.getAllGameEventsFromGameWeekNumberAndPlayerId(tot, p.getId()));
                    }
                    for (Player p : listFormation.get(3)) {
                        listEventsByPlayer.put(p.getId(), playerService.getAllGameEventsFromGameWeekNumberAndPlayerId(tot, p.getId()));
                    }

                    HashMap<Player, List<Integer>> listEventPointsByPlayer = playerService.getHashOfListEventsByPlayerId(listEventsByPlayer);
                    List<Integer> lista = new ArrayList<>();
                    for (int i = 1; i <= tot; i++) {
                        lista.add(i);
                    }
                    model.addAttribute("gameWeekNumbers", lista);
                    model.addAttribute("hashEventsByPlayer", listEventPointsByPlayer);
                    model.addAttribute("GK", listFormation.get(0));
                    model.addAttribute("DEFs", listFormation.get(1));
                    model.addAttribute("MIDs", listFormation.get(2));
                    model.addAttribute("FORs", listFormation.get(3));
                    model.addAttribute("gameWeekNumber", tot);
                    model.addAttribute("gameWeekDate", gameWeekService.getGameWeekByNumber(tot).prettyPrintDate());
                    model.addAttribute("idCapitao", u.getVirtualTeam().getLastSnapshot().getCapitao());
                    model.addAttribute("team", team);
                } else {
                    model.addAttribute("gameWeekNumbers", null);
                    model.addAttribute("hashEventsByPlayer", null);
                }
                return "virtualTeam/historic";
            } else {
                return "redirect:/team/new";
            }
        } else {
            return "redirect:/";
        }
    }
    /*
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
    }*/

}