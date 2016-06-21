/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.Controllers;

import com.fantasy.Models.User;
import com.fantasy.Services.UserService;
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
    private UserService users;
    
 
    @RequestMapping(method = RequestMethod.GET)
    public String viewRegistration(Map<String, Object> model) {
        User user = new User();    
        model.put("user", user);         
        return "registration";
    }
     
    @RequestMapping(method = RequestMethod.POST)
    public String processRegistration(Map<String, Object> model,User user) {
         System.out.println(user.getUsername());
         System.out.println(user.getEmail());
         System.out.println(user.getPassword());
         
        User create;
        try {
            create = users.create(user);
        } catch (Exception ex) {
            return "error";
        }
        
        model.put("user", create); 
        return "registration_sucess";
            
    }
}