package com.fantasy;

import com.fantasy.Models.Utilizador;
import java.util.List;

import com.fantasy.Models.VirtualTeam;
import org.springframework.data.repository.CrudRepository;

public interface VirtualTeamRepository extends CrudRepository<VirtualTeam, Long> {

    List<VirtualTeam> findByName(String name);
}