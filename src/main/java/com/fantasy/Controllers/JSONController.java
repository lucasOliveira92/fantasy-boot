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
}
