package com.fantasy.Controllers;

import com.fantasy.Models.User;
import com.fantasy.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClassificationController {
    @Autowired
    private UserService gestorUser;
    @Autowired
    private GameWeekService gestorGameWeek;
    @Autowired
    private GameWeekSnapshotService gestorGameWeekSnapshot;
    @Autowired
    private VirtualTeamService gestorVirtualTeams;
    @Autowired
    private PlayerService gestorPlayers;
    @Autowired
    private RealTeamService gestorRealTeams;

    @RequestMapping(value = "classification", method = RequestMethod.GET)
    public String showClassification(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);
        model.addAttribute("teams", gestorVirtualTeams.getAllTeamsOrderedByPoints());
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        return "classification";
    }

    // Fazer pesquisa por numero da jornada
    @RequestMapping(value = "classification/{number}", method = RequestMethod.GET)
    public String showClassificationByGameWeek(Model model, @PathVariable Integer number) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);

        return "classification";
    }

    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    public String showStatistics(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);
        model.addAttribute("players", gestorPlayers.getAllPlayersByCost());
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        model.addAttribute("realTeams", gestorRealTeams.getAllRealTeams());
        return "statistics";
    }
}
