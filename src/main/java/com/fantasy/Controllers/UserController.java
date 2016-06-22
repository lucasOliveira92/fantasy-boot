package com.fantasy.Controllers;

import com.fantasy.Models.User;
import com.fantasy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "userform";
    }

    @RequestMapping("user/new")
    public String newProduct(Model model){
        model.addAttribute("user", new User());
        return "userform";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveProduct(User utilizador){

        gestor.saveUser(utilizador);

        return "redirect:/user/" + utilizador.getId();
    }
}