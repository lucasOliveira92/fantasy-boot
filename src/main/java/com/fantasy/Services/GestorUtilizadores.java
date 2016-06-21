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
    
    public Utilizador  getUserByName(String username){
        return users.findByUsername(username);
    }
    /*
    public Utilizador getUserById(long id){
        return users.findById(id);
    }*/
    
    public Utilizador create(Utilizador user) throws Exception {
        return users.save(user);
    }
}
