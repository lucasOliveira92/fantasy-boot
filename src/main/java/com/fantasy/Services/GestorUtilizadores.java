package com.fantasy.Services;

import com.fantasy.Models.Utilizador;
import com.fantasy.Repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GestorUtilizadores {
    @Autowired
    private UserRepository users;
    
    public Iterable<Utilizador> getAllUsers() {
        return users.findAll();
    }
    
    public Utilizador  getUserByName(String name){
        return users.findByName(name);
    }
    /*
    public Utilizador getUserById(long id){
        return users.findById(id);
    }*/
    
    public Utilizador create(Utilizador user) throws Exception {
        if(users.exists(user.getId()))
            throw new Exception("User already exists!");
        else return users.save(user);
    }
}
