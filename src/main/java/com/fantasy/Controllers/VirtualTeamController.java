package com.fantasy.Controllers;

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

@Controller
public class VirtualTeamController {

    @Autowired
    private VirtualTeamService gestor;

    @Autowired
    private UserService gestorUser;

    @Autowired
    private PlayerService playerService;

    @Secured("ROLE_USER")
    @RequestMapping(value = "team/{id}", method = RequestMethod.GET)
    public String showVirtualTeam(@PathVariable Integer id, Model model) {
        model.addAttribute("team", gestor.getVirtualTeam(id));
        return "virtualTeam/show";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "team/new", method = RequestMethod.GET)
    public String newVirtualTeam(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

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

    @RequestMapping(value = "team", method = RequestMethod.POST)
    public String saveVirtualTeam(@Valid @ModelAttribute("team") VirtualTeam virtualTeam, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("team", virtualTeam);
            return "virtualTeam/new";
        }

        gestor.saveVirtualTeam(virtualTeam);

        return "redirect:/team/new";
    }
}
