package com.fantasy.Repositories;

import com.fantasy.Models.User;
import java.util.List;

import com.fantasy.Models.VirtualTeam;
import org.springframework.data.repository.CrudRepository;

public interface VirtualTeamRepository extends CrudRepository<VirtualTeam, Long> {

    List<VirtualTeam> findByName(String name);
    
    VirtualTeam findById(long id);
    
    VirtualTeam findByUser (long userId);
}