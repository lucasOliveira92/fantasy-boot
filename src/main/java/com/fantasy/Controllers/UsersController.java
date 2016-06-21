package com.fantasy.Controllers;

import com.fantasy.Models.Utilizador;
import com.fantasy.Services.GestorUtilizadores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {

    @Autowired
    private GestorUtilizadores gestor;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("utilizador", gestor.getAllUsers());
        System.out.println("Returning users:");
        return "users";
    }

    @RequestMapping("users/{id}")
    public String showProduct(@PathVariable Integer id, Model model){
        model.addAttribute("utilizador", gestor.getUserById(id));
        return "usershow";
    }

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("utilizador", gestor.getUserById(id));
        return "userform";
    }

    @RequestMapping("user/new")
    public String newProduct(Model model){
        model.addAttribute("utilizador", new Utilizador());
        return "userform";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveProduct(Utilizador utilizador){

        gestor.saveUtilizador(utilizador);

        return "redirect:/user/" + utilizador.getId();
    }
}
