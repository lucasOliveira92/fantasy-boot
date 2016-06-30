package com.fantasy.Controllers;

import com.fantasy.Models.TeamManagementResponse;
import com.fantasy.Services.RESTService;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("User");
        System.out.println(rs.getUser());
        System.out.println("Capitao");
        System.out.println(rs.getCapitao());
        List lista = rs.getTitulares();
        System.out.println("Players");
        for(Object o: lista){
            System.out.println(o);
        }

        return "OK";
    }
}
