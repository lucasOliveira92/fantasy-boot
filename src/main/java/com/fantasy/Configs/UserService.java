/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.Configs;

import com.fantasy.Models.Utilizador;
import com.fantasy.Services.GestorUtilizadores;
import java.util.Collection;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private GestorUtilizadores userDAO;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Utilizador u = userDAO.getUserByName(name);
        if (u == null) {
            throw new UsernameNotFoundException("Username not found: " + name);
        }
        Collection<GrantedAuthority> auth = new LinkedList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return new org.springframework.security.core.userdetails.User(name, u.getPassword(), auth);
    }

}
