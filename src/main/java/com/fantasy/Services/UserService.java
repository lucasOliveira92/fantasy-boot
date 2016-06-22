package com.fantasy.Services;

import com.fantasy.DAO.UserDAO;
import com.fantasy.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User u = userDAO.findByUsername(name);
        if (u == null) {
            throw new UsernameNotFoundException("Username not found: " + name);
        }
        Collection<GrantedAuthority> auth = new LinkedList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return new org.springframework.security.core.userdetails.User(name, u.getPassword(), auth);
    }
    
    public User create(User user) throws Exception {
        return userDAO.save(user);
    }

    public User getUserByUserameOrEmail(String username, String email){
        return userDAO.findByUsernameOrEmail(username,email);
    }
    
    public User  getUserByUsername(String username){
        return userDAO.findByUsername(username);
    }
    
    public Iterable<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User getUserById(long id){
        return userDAO.findOne(id);
    }

    public User saveUser(User ut){
        return userDAO.save(ut);
    }

}
