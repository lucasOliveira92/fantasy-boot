package com.fantasy.Controllers;

import com.fantasy.Models.User;
import com.fantasy.Services.GameWeekService;
import com.fantasy.Services.GameWeekSnapshotService;
import com.fantasy.Services.UserService;
import com.fantasy.Services.VirtualTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "classification", method = RequestMethod.GET)
    public String showClassificationLoggedIn(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);
        model.addAttribute("teams", gestorVirtualTeams.getAllTeamsOrderedByPoints());
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        return "classification";
    }
}
