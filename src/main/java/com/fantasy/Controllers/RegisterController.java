/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.Controllers;

import com.fantasy.Models.Utilizador;
import com.fantasy.Services.GestorUtilizadores;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/register")
public class RegisterController {
    
    @Autowired
    private GestorUtilizadores users;
    
 
    @RequestMapping(method = RequestMethod.GET)
    public String viewRegistration(Map<String, Object> model) {
        Utilizador user = new Utilizador();    
        model.put("user", user);         
        return "registration";
    }
     
    @RequestMapping(method = RequestMethod.POST)
    public String processRegistration(Map<String, Object> model,Utilizador user) {
         System.out.println(user.getName());
         System.out.println(user.getEmail());
         System.out.println(user.getPassword());
         
        Utilizador create;
        try {
            create = users.create(user);
        } catch (Exception ex) {
            return "error";
        }
        
        model.put("user", create); 
        return "registration_sucess";
            
    }
}