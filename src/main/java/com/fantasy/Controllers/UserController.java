package com.fantasy.Controllers;

import com.fantasy.Models.User;
import com.fantasy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService gestor;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestor.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        model.addAttribute("users", gestor.getAllUsers());
        return "user/users";
    }

    @RequestMapping(value ="user", method = RequestMethod.GET)
    public String showUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestor.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        model.addAttribute("user",u);
        return "user/show";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value ="user/edit", method = RequestMethod.GET)
    public String edit(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestor.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        if(u != null) {
            model.addAttribute("user", u);
            return "user/form";
        }
        else
            return "redirect:/home";
    }

    @RequestMapping(value = "user/new", method = RequestMethod.GET)
    public String newUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestor.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);
        model.addAttribute("user", new User());
        return "user/form";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestor.getUserByUsername(auth.getName());
        model.addAttribute("currentUser", u);

        if(user.getPassword().compareTo(user.getPasswordCheck()) != 0){
            bindingResult.reject("password","Passwords não fazem match");
        }
        else{
            User u2 = gestor.getUserByUserameOrEmail(user.getUsername(),user.getEmail());
            if( u2 != null && u.getUsername().compareTo(user.getUsername()) == 0)
                bindingResult.reject("username","Username já existe");
            else if(u2 != null && u2.getEmail().compareTo(user.getEmail()) == 0){
                bindingResult.reject("email","Email já existe");
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "user/form";
        }

        gestor.saveUser(user);

        return "redirect:/team/new";
    }
}