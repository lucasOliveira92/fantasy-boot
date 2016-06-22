package com.fantasy.DAO;

import com.fantasy.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);
}