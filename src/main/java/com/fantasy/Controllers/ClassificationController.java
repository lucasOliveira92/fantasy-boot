package com.fantasy.Controllers;

import com.fantasy.Models.Player;
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

import java.util.ArrayList;
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
        List<Integer> lista = new ArrayList<>();
        int tot = gestorUser.getUserByUsername("Quim").getVirtualTeam().getGameWeekSnapshots().size()-1;
        for(int i=1; i<=tot; i++)
            lista.add(i);

        model.addAttribute("currentUser", u);

        model.addAttribute("gameWeekNumber",lista);
        model.addAttribute("teams", gestorVirtualTeams.getAllTeamsOrderedByPoints());
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        return "classification";
    }

    // Fazer pesquisa por numero da jornada
    @RequestMapping(value = "classification/{number}", method = RequestMethod.GET)
    public String showClassificationByGameWeek(Model model, @PathVariable Integer number) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        List<Integer> lista = new ArrayList<>();
        int tot = gestorUser.getUserByUsername("Quim").getVirtualTeam().getGameWeekSnapshots().size()-1;
        for(int i=1; i<=tot; i++)
            lista.add(i);

        model.addAttribute("currentUser", u);
        model.addAttribute("gameWeekNumber",lista);
        model.addAttribute("teams", gestorVirtualTeams.getAllTeamsOrderedByPoints());
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        if(number != -1) {
            model.addAttribute("gameweekID", gestorGameWeek.getGameWeekByNumber(number).getId());
            model.addAttribute("number", number);
        }else{
            model.addAttribute("gameweekID",gestorGameWeek.getGameWeekByNumber(tot).getId());
            model.addAttribute("number", tot);
        }
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
        model.addAttribute("order",-1);
        return "statistics";
    }

    @RequestMapping(value = "statistics/{teamId}/{positionId}/{orderId}", method = RequestMethod.GET)
    public String showStatisticsBySearch(Model model, @PathVariable Integer teamId, @PathVariable Integer positionId, @PathVariable Integer orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());
        List<Player> lista = null;
        List<Player> lista2;
        List<Player> listafinal;
        switch (orderId){
            case -1:
                listafinal = gestorPlayers.getAllPlayersByCost();
                break;
            case 1:
                listafinal = gestorPlayers.getAllPlayersByTotalPoints();
                break;
            case 2:
                listafinal = gestorPlayers.getAllPlayersByGoalScored();
                break;
            case 3:
                listafinal = gestorPlayers.getAllPlayersByYellow();
                break;
            case 4:
                listafinal = gestorPlayers.getAllPlayersByRed();
                break;
            default:
                listafinal = gestorPlayers.getAllPlayersByCost();
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
                    lista2 = gestorPlayers.getAllPlayersByPosition("GK");
                    listafinal.retainAll(lista2);
                    break;
                case 2:
                    lista2 = gestorPlayers.getAllPlayersByPosition("DEF");
                    listafinal.retainAll(lista2);
                    break;
                case 3:
                    lista2 = gestorPlayers.getAllPlayersByPosition("MID");
                    listafinal.retainAll(lista2);
                    break;
                case 4:
                    lista2 = gestorPlayers.getAllPlayersByPosition("FOR");
                    listafinal.retainAll(lista2);
                    break;
                default:
                    break;
            }
        }
        model.addAttribute("currentUser", u);
        model.addAttribute("players", listafinal);
        model.addAttribute("gameWeeks", gestorGameWeek.getAllGameWeeks());
        model.addAttribute("realTeams", gestorRealTeams.getAllRealTeams());
        model.addAttribute("order",orderId);
        return "statistics";
    }

    @RequestMapping(value = "winners", method = RequestMethod.GET)
    public String showWinners(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);
        return "winners";
    }

    @RequestMapping(value = "rules", method = RequestMethod.GET)
    public String showRules(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestorUser.getUserByUsername(auth.getName());

        model.addAttribute("currentUser", u);
        return "rules";
    }
}