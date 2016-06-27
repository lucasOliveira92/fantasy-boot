package com.fantasy.Controllers;

import com.fantasy.DAO.GameEventDAO;
import com.fantasy.DAO.GameWeekDAO;
import com.fantasy.Models.*;
import com.fantasy.Services.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Controller
public class HelloController {

    @Autowired
    GenerateService generateService;

    @RequestMapping("/")
    public String index() {
        return "home";
    }
    
    //@Secured("ROLE_USER")
    @RequestMapping("/home")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }

    @RequestMapping("/generate/{gameWeek}")
    public String generate(@PathVariable Integer gameWeek) {
        if(gameWeek > 0 && gameWeek <= 34)
            generateService.generate(gameWeek);
        return "home";
    }

}