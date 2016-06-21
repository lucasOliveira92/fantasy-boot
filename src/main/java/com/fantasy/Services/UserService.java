package com.fantasy.Services;

import com.fantasy.Models.User;
import java.util.Collection;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.fantasy.DAO.UserDAO;


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
    
    public User  getUserByName(String username){
        return userDAO.findByUsername(username);
    }
    
    public Iterable<User> getAllUsers() {
        return userDAO.findAll();
    }

}
