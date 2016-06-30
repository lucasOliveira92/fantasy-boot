package com.fantasy.Controllers;

import com.fantasy.Models.Player;
import com.fantasy.Models.User;
import com.fantasy.Models.VirtualTeam;
import com.fantasy.Services.PlayerService;
import com.fantasy.Services.UserService;
import com.fantasy.Services.VirtualTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Secured("ROLE_USER")
    @RequestMapping(value = "team", method = RequestMethod.GET)
    public String showVirtualTeam(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
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
                lista.removeAll(u.getTeam().getPlayers());

                List<List<Player>> listFormation = gestor.getListsAllPlayersByPosition(u.getVirtualTeam().getId());
                model.addAttribute("gks", listFormation.get(0));
                model.addAttribute("defs", listFormation.get(1));
                model.addAttribute("mids", listFormation.get(2));
                model.addAttribute("fors", listFormation.get(3));
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
    @RequestMapping(value = "team/transfers/{newId}/{oldId}", method = RequestMethod.GET)
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
    @RequestMapping(value = "team/new", method = RequestMethod.GET)
    public String newVirtualTeam(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        if (u != null) {
            if (u.hasVirtualTeam()) {
                return "redirect:/team/" + u.getId();
            } else {
                model.addAttribute("players", playerService.getAllPlayers());
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
}
