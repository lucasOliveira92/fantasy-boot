package com.fantasy.Controllers;

import com.fantasy.DAO.GameDAO;
import com.fantasy.Models.Game;
import com.fantasy.Models.Player;
import com.fantasy.Models.TeamManagementResponse;
import com.fantasy.Models.User;
import com.fantasy.Services.RESTService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Lucas on 29-Jun-16.
 */
@RestController
public class JSONController {

    @Autowired
    RESTService restService;

    @RequestMapping(value = "/api/games", method = RequestMethod.GET)
    public String serveAPI() {
        return restService.serveREST();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/games", method = RequestMethod.POST)
    public @ResponseBody  String  getSearchUserProfiles(@RequestBody TeamManagementResponse rs, HttpServletRequest request) {
        System.out.println(rs.getName());
        System.out.println(rs.getId());


        return "OK";
    }
}
