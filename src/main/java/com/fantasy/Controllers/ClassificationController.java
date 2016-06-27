package com.fantasy.Controllers;

import com.fantasy.Models.Player;
import com.fantasy.Models.User;
import com.fantasy.Models.VirtualTeam;
import com.fantasy.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

        List<VirtualTeam> teams = gestorVirtualTeams.getAllTeamsOrderedByPoints();

        model.addAttribute("currentUser", u);
        model.addAttribute("teams", gestorVirtualTeams.getAllTeamsOrderedByPoints());
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        model.addAttribute("number", number);
        model.addAttribute("gameweekID", gestorGameWeek.getGameWeekByNumber(number).getId());
        return "classificationGameWeek";
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

    @RequestMapping(value = "statistics/{teamId}/{positionId}", method = RequestMethod.GET)
    public String showStatistics(Model model, @PathVariable Integer teamId, @PathVariable Integer positionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        List<Player> lista;
        List<Player> lista2;
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
                    lista = gestorPlayers.getAllPlayersByCost();
                    break;
            }
        }else{
            lista = gestorPlayers.getAllPlayersByCost();
            switch (positionId) {
                case -1:
                    break;
                case 1:
                    lista2 = gestorPlayers.getAllPlayersByPosition("GK");
                    lista.retainAll(lista2);
                    break;
                case 2:
                    lista2 = gestorPlayers.getAllPlayersByPosition("DEF");
                    lista.retainAll(lista2);
                    break;
                case 3:
                    lista2 = gestorPlayers.getAllPlayersByPosition("MID");
                    lista.retainAll(lista2);
                    break;
                case 4:
                    lista2 = gestorPlayers.getAllPlayersByPosition("FOR");
                    lista.retainAll(lista2);
                    break;
                default:
                    lista = gestorPlayers.getAllPlayersByCost();
                    break;
            }
        }
        model.addAttribute("currentUser", u);
        model.addAttribute("players", lista);
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        model.addAttribute("realTeams", gestorRealTeams.getAllRealTeams());
        return "statistics";
    }
}