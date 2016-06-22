package com.fantasy.Controllers;

import com.fantasy.Models.User;
import com.fantasy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("Returning users:");
        return "user/users";
    }

    @RequestMapping("user/{id}")
    public String showProduct(@PathVariable Integer id, Model model){
        model.addAttribute("user", gestor.getUserById(id));
        return "user/show";
    }

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("user", gestor.getUserById(id));
        return "user/form";
    }

    @RequestMapping("user/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "user/form";
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

        return "redirect:/user/" + user.getId();
    }
}