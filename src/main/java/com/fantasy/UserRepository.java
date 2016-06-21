package com.fantasy;

import com.fantasy.Models.Utilizador;
        import java.util.List;

        import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Utilizador, Long> {

    Utilizador findByName(String name);
}