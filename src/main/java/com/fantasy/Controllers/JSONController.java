package com.fantasy.Controllers;

import com.fantasy.Services.RESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JSONController {

    @Autowired
    RESTService restService;

    @RequestMapping(value = "/api/games", method = RequestMethod.GET)
    public String serveAPI() {
        return restService.serveREST();
    }
}
