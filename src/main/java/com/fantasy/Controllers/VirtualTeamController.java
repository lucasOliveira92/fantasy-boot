package com.fantasy.Controllers;

import com.fantasy.Models.*;
import com.fantasy.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class VirtualTeamController {

    @Autowired
    private VirtualTeamService gestor;

    @Autowired
    private UserService gestorUser;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private RealTeamService gestorRealTeams;

    @Autowired
    private GameWeekService gestorGameWeeks;



    @Secured("ROLE_USER")
    @RequestMapping(value = "team", method = RequestMethod.GET)
    public String showVirtualTeam(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        int tot = gestorUser.getUserByUsername("Quim").getVirtualTeam().getGameWeekSnapshots().size()-1;
        if(u != null) {
            if (u.hasVirtualTeam()) {
                VirtualTeam team = gestor.getVirtualTeam(u.getId());
                List<List<Player>> listFormation = gestor.getListsPlayersByPositionByFormation(u.getVirtualTeam().getId());
                List<List<Player>> listSubstitutes = gestor.getListsPlayersByPositionBySubstitutes(u.getVirtualTeam().getId());
                model.addAttribute("GK",listFormation.get(0));
                model.addAttribute("DEFs",listFormation.get(1));
                model.addAttribute("MIDs",listFormation.get(2));
                model.addAttribute("FORs",listFormation.get(3));
                model.addAttribute("GKsub",listSubstitutes.get(0));
                model.addAttribute("DEFsub",listSubstitutes.get(1));
                model.addAttribute("MIDsub",listSubstitutes.get(2));
                model.addAttribute("FORsub",listSubstitutes.get(3));
                model.addAttribute("games",gestorGameWeeks.getGamesByGameWeekNumber(tot));
                model.addAttribute("gameWeekNumber", tot);
                model.addAttribute("idCapitao",u.getVirtualTeam().getLastSnapshot().getCapitao());
                model.addAttribute("team", team);
                return "virtualTeam/show";
            }else{
                return "redirect:/team/new";
            }
        }else{
            return "redirect:/";
        }
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "team/transfers", method = RequestMethod.GET)
    public String transfersVirtualTeam(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        if (u != null) {
            if (u.hasVirtualTeam()) {
                ArrayList<Player> lista = new ArrayList<>(playerService.getAllPlayers());

                List<List<Player>> listFormation = gestor.getListsAllPlayersByPosition(u.getVirtualTeam().getId());
                model.addAttribute("gks", listFormation.get(0));
                model.addAttribute("defs", listFormation.get(1));
                model.addAttribute("mids", listFormation.get(2));
                model.addAttribute("fors", listFormation.get(3));
                model.addAttribute("realTeams", gestorRealTeams.getAllRealTeams());
                model.addAttribute("players", lista);
                model.addAttribute("team", u.getTeam());
                return "virtualTeam/transfers";
            } else {
                return "redirect:/team/new";
            }
        } else {
            return "redirect:/login";
        }
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "team/transfers/{newId}/{oldId}", method = RequestMethod.POST)
    public String makeTransfersVirtualTeam(Model model, @PathVariable Integer newId, @PathVariable Integer oldId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        if (u != null) {
            if (u.hasVirtualTeam()) {
                VirtualTeam team = gestor.doTransfer(playerService.getPlayerById(newId),playerService.getPlayerById(oldId),u.getTeam().getId());
                ArrayList<Player> lista = new ArrayList<>(playerService.getAllPlayers());
                lista.removeAll(u.getTeam().getPlayers());

                List<List<Player>> listFormation = gestor.getListsAllPlayersByPosition(team.getId());
                model.addAttribute("gks", listFormation.get(0));
                model.addAttribute("defs", listFormation.get(1));
                model.addAttribute("mids", listFormation.get(2));
                model.addAttribute("fors", listFormation.get(3));
                model.addAttribute("realTeams", gestorRealTeams.getAllRealTeams());
                model.addAttribute("players", lista);
                model.addAttribute("team", u.getTeam());
                return "virtualTeam/transfers";
            } else {
                return "redirect:/team/new";
            }
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "team/transfers/{teamId}/{positionId}/{orderId}", method = RequestMethod.GET)
    public String searchMakeTransfersVirtualTeam(Model model, @PathVariable Integer teamId, @PathVariable Integer positionId, @PathVariable Integer orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        List<Player> lista = null;
        List<Player> lista2;
        List<Player> listafinal;
        switch (orderId){
            case -1:
                listafinal = playerService.getAllPlayersByCost();
                break;
            case 1:
                listafinal = playerService.getAllPlayersByTotalPoints();
                break;
            case 2:
                listafinal = playerService.getAllPlayersByGoalScored();
                break;
            case 3:
                listafinal = playerService.getAllPlayersByYellow();
                break;
            case 4:
                listafinal = playerService.getAllPlayersByRed();
                break;
            case 5:
                listafinal = playerService.getAllPlayersByTimesBought();
                break;
            default:
                listafinal = playerService.getAllPlayersByCost();
                break;
        }
        if(teamId!=-1) {
            switch (positionId) {
                case -1:
                    lista = gestorRealTeams.getPlayersfromRealTeam(teamId);
                    break;
                case 1:
                    lista = gestorRealTeams.getById(teamId).getPlayerByPosition("GK");
                    break;
                case 2:
                    lista = gestorRealTeams.getById(teamId).getPlayerByPosition("DEF");
                    break;
                case 3:
                    lista = gestorRealTeams.getById(teamId).getPlayerByPosition("MID");
                    break;
                case 4:
                    lista = gestorRealTeams.getById(teamId).getPlayerByPosition("FOR");
                    break;
                default:
                    break;
            }
            if (lista!=null)listafinal.retainAll(lista);
        }else{
            switch (positionId) {
                case -1:
                    break;
                case 1:
                    lista2 = playerService.getAllPlayersByPosition("GK");
                    listafinal.retainAll(lista2);
                    break;
                case 2:
                    lista2 = playerService.getAllPlayersByPosition("DEF");
                    listafinal.retainAll(lista2);
                    break;
                case 3:
                    lista2 = playerService.getAllPlayersByPosition("MID");
                    listafinal.retainAll(lista2);
                    break;
                case 4:
                    lista2 = playerService.getAllPlayersByPosition("FOR");
                    listafinal.retainAll(lista2);
                    break;
                default:
                    break;
            }
        }
        List<List<Player>> listFormation = gestor.getListsAllPlayersByPosition(u.getVirtualTeam().getId());
        model.addAttribute("currentUser", u);
        model.addAttribute("players", listafinal);
        model.addAttribute("gks", listFormation.get(0));
        model.addAttribute("defs", listFormation.get(1));
        model.addAttribute("mids", listFormation.get(2));
        model.addAttribute("fors", listFormation.get(3));
        model.addAttribute("realTeams", gestorRealTeams.getAllRealTeams());
        model.addAttribute("team", u.getTeam());
        model.addAttribute("order",orderId);
        return "virtualTeam/transfers";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "team/new", method = RequestMethod.GET)
    public String newVirtualTeam(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        if (u != null) {
            if (u.hasVirtualTeam()) {
                return "redirect:/team/" + u.getId();
            } else {
                model.addAttribute("players", playerService.getAllPlayersByCost());
                model.addAttribute("team", new VirtualTeam());
                return "virtualTeam/new";
            }
        } else {
            return "redirect:/login";
        }
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "team", method = RequestMethod.POST)
    public String saveVirtualTeam(@Valid @ModelAttribute("team") VirtualTeam virtualTeam, BindingResult bindingResult, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        if (bindingResult.hasErrors()) {
            model.addAttribute("team", virtualTeam);
            return "virtualTeam/new";
        }
        gestor.saveVirtualTeam(virtualTeam);
        return "redirect:/team/new";
    }

    @CrossOrigin
    @Secured("ROLE_USER")
    @RequestMapping(value = "/save/team", method = RequestMethod.POST)
    public @ResponseBody
    String  getSearchUserProfiles(@RequestBody TeamManagementResponse rs, HttpServletRequest request) {

        List lista = rs.getTitulares();
        List<Player> idPlayers = new ArrayList<>();
        System.out.println("Players");
        for(Object o: lista){
            idPlayers.add(playerService.getPlayerById(Long.parseLong(o.toString())));
        }

        GameWeekSnapshot snap = snapshotService.getLastSnapshotByUser(rs.getUser());
        if(snap != null){
            snap.setCapitao(rs.getCapitao());
            snap.setPlayers(idPlayers);
            snapshotService.saveSnap(snap);
        }

        return "OK";
    }

    @CrossOrigin
    @Secured("ROLE_USER")
    @RequestMapping(value = "/save/new/team", method = RequestMethod.POST)
    public @ResponseBody
    String  newVirtualTeam(@RequestBody NewTeamResponse rs, HttpServletRequest request) {
        System.out.println("User");
        System.out.println(rs.getUser());
        System.out.println("Team");
        System.out.println(rs.getTeamName());

        List lista = rs.getEquipa();
        List<Long> idPlayers = new ArrayList<>();
        System.out.println("Players");
        for(Object o: lista){
            idPlayers.add((Long.parseLong(o.toString())));
            System.out.println(o.toString());
        }

        gestor.createVirtualTeam(rs.getTeamName(),idPlayers,rs.getUser());

        return "redirect:/team";
    }
}
