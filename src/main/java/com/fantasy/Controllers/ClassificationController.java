package com.fantasy.Controllers;

import com.fantasy.Services.GameWeekService;
import com.fantasy.Services.GameWeekSnapshotService;
import com.fantasy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "classification/{id}", method = RequestMethod.GET)
    public String showClassificationLoggedIn(@PathVariable long id, Model model) {

        return "";
    }
}
