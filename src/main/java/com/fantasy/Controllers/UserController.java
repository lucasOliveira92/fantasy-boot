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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService gestor;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("users", gestor.getAllUsers());
        return "users";
    }

    @RequestMapping(value ="user/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable Integer id, Model model){
        model.addAttribute("user",gestor.getUserById(id));
        return "show";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value ="user/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = gestor.getUserById(id);

        if(u.getUsername().compareTo(auth.getName()) == 0) {
            model.addAttribute("user", u);
            return "form";
        }
        else
            return "redirect:/home";
    }

    @RequestMapping("user/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "form";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){

        if(user.getPassword().compareTo(user.getPasswordCheck()) != 0){
            bindingResult.reject("password","Passwords não fazem match");
        }
        else{
            User u = gestor.getUserByUserameOrEmail(user.getUsername(),user.getEmail());
            if( u != null && u.getUsername().compareTo(user.getUsername()) == 0)
                bindingResult.reject("username","Username já existe");
            else if(u != null && u.getEmail().compareTo(user.getEmail()) == 0){
                bindingResult.reject("email","Email já existe");
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "user/form";
        }

        gestor.saveUser(user);

       // return "redirect:/user/" + user.getId();
        return "redirect:/team/new/" + user.getId();
    }
}