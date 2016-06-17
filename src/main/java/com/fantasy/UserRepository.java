package com.fantasy;

import com.fantasy.Models.User;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);
}