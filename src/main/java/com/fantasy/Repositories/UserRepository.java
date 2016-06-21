package com.fantasy.Repositories;

import com.fantasy.Models.Utilizador;
        import java.util.List;

        import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Utilizador, Long> {

    Utilizador findByUsername(String username);
}