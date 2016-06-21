package com.fantasy.Services;

import com.fantasy.Models.Utilizador;
import com.fantasy.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
    }
    
    public Utilizador create(UserCreateForm form) {
        Utilizador user = new Utilizador("",form.getEmail(),form.getPassword());
        return users.save(user);
    }*/
}
