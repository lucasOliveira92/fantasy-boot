package com.fantasy.Services;

import com.fantasy.Models.Utilizador;
import com.fantasy.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GestorUtilizadores {
    @Autowired
    private UserRepository users;
    
    public Iterable<Utilizador> getAllUsers() {
        return users.findAll();
    }
    
    public Utilizador  getUserByName(String username){
        return users.findByUsername(username);
    }

    public Utilizador getUserById(long id){
        return users.findOne(id);
    }

    public Utilizador saveUtilizador(Utilizador ut){
        return users.save(ut);
    }
    /*
    public Utilizador create(UserCreateForm form) {
        Utilizador user = new Utilizador("",form.getEmail(),form.getPassword());
        return users.save(user);
    }*/
}
