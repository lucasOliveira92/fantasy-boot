package com.fantasy.DAO;

import com.fantasy.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);
<<<<<<< HEAD
=======
    User findByUsernameOrEmail (String username, String email);
 //   User findOne(long id);
 //   User save(User user);
 //   Iterable<User> findAll();
>>>>>>> validacoes-feitas
}