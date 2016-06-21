package com.fantasy.Services;

import com.fantasy.Models.User;
import com.fantasy.Repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GestorUtilizadores {
    @Autowired
    private UserRepository users;
    
    public Iterable<User> getAllUsers() {
        return users.findAll();
    }
    
    public User  getUserByName(String username){
        return users.findByUsername(username);
    }
    /*
    public User getUserById(long id){
        return users.findById(id);
    }*/
    
    public User create(User user) throws Exception {
        return users.save(user);
    }
}
