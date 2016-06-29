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
                List<Player> formation = team.getLastTeamFormation();
                List<Player> substitutes = team.getPlayers();
                substitutes.removeAll(formation);
                Player gk=null;
                ArrayList<Player> defs = new ArrayList<>();
                ArrayList<Player> mids = new ArrayList<>();
                ArrayList<Player> fors = new ArrayList<>();
                Player gksub=null;
                ArrayList<Player> defsub = new ArrayList<>();
                ArrayList<Player> midsub = new ArrayList<>();
                ArrayList<Player> forsub = new ArrayList<>();
                for (Player p : formation) {
                    switch (p.getPosition()){
                        case "GK":
                            gk = p;
                            break;
                        case "DEF":
                            defs.add(p);
                            break;
                        case "MID":
                            mids.add(p);
                            break;
                        case "FOR":
                            fors.add(p);
                            break;
                    }
                }
                for (Player p : substitutes) {
                    switch (p.getPosition()){
                        case "GK":
                            gksub = p;
                            break;
                        case "DEF":
                            defsub.add(p);
                            break;
                        case "MID":
                            midsub.add(p);
                            break;
                        case "FOR":
                            forsub.add(p);
                            break;
                    }
                }
                model.addAttribute("GK",gk);
                model.addAttribute("DEFs",defs);
                model.addAttribute("MIDs",mids);
                model.addAttribute("FORs",fors);
                model.addAttribute("GKsub",gksub);
                model.addAttribute("DEFsub",defsub);
                model.addAttribute("MIDsub",midsub);
                model.addAttribute("FORsub",forsub);
                model.addAttribute("formation",team.getLastTeamFormation());
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
                ArrayList<Player> lista = (ArrayList<Player>) playerService.getAllPlayers();
                lista.removeAll(u.getTeam().getPlayers());
                ArrayList<Player> gks = new ArrayList<>();
                ArrayList<Player> defs = new ArrayList<>();
                ArrayList<Player> mids = new ArrayList<>();
                ArrayList<Player> fors = new ArrayList<>();
                for (Player p : u.getTeam().getPlayers()) {
                    switch (p.getPosition()){
                        case "GK":
                            gks.add(p);
                            break;
                        case "DEF":
                            defs.add(p);
                            break;
                        case "MID":
                            mids.add(p);
                            break;
                        case "FOR":
                            fors.add(p);
                            break;
                    }
                }
                model.addAttribute("gks", gks);
                model.addAttribute("defs", defs);
                model.addAttribute("mids", mids);
                model.addAttribute("fors", fors);
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
                ArrayList<Player> lista = (ArrayList<Player>) playerService.getAllPlayers();
                lista.removeAll(team.getPlayers());
                ArrayList<Player> gks = new ArrayList<>();
                ArrayList<Player> defs = new ArrayList<>();
                ArrayList<Player> mids = new ArrayList<>();
                ArrayList<Player> fors = new ArrayList<>();
                for (Player p : team.getPlayers()) {
                    switch (p.getPosition()){
                        case "GK":
                            gks.add(p);
                            break;
                        case "DEF":
                            defs.add(p);
                            break;
                        case "MID":
                            mids.add(p);
                            break;
                        case "FOR":
                            fors.add(p);
                            break;
                    }
                }
                model.addAttribute("gks", gks);
                model.addAttribute("defs", defs);
                model.addAttribute("mids", mids);
                model.addAttribute("fors", fors);
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
