package com.fantasy.Controllers;

import com.fantasy.Models.GameWeek;
import com.fantasy.Models.GameWeekSnapshot;
import com.fantasy.Services.GameWeekService;
import com.fantasy.Services.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;




@Controller
public class AdminController {

    @Autowired
    GameWeekService gameWeekService;

    @Autowired
    GenerateService generateService;

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String greeting(Model model) {
        Iterable<GameWeek> allGameWeeks = gameWeekService.getAllGameWeeks();
        Iterator<GameWeek> it = allGameWeeks.iterator();
        while(it.hasNext()){
            GameWeek gw = it.next();
        }
        return "admin";
    }
    @RequestMapping(value = "admin/generate/{gameWeek}")
    public String generate(@PathVariable Integer gameWeek) {

        /*Iterable<GameWeek> allGameWeeks = gameWeekService.getAllGameWeeks();
        Iterator<GameWeek> it = allGameWeeks.iterator();
        while(it.hasNext()){
            GameWeek gw = it.next();
        }*/

        if(gameWeek > 0 && gameWeek <= 34){
            generateService.generate(gameWeek);
            if(gameWeek < 34)
                generateService.genererateRandomSnapshots(gameWeek + 1 );
        }

        System.out.println(gameWeek);
        System.out.println(gameWeek);
        System.out.println(gameWeek);
        System.out.println(gameWeek);
        System.out.println(gameWeek);
        return "admin";
    }
}
