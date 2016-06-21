package com.fantasy.DAO;

import com.fantasy.Models.User;
        import java.util.List;

        import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);
}