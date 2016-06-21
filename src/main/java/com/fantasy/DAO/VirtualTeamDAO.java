package com.fantasy.DAO;

import com.fantasy.Models.User;
import java.util.List;

import com.fantasy.Models.VirtualTeam;
import org.springframework.data.repository.CrudRepository;

public interface VirtualTeamDAO extends CrudRepository<VirtualTeam, Long> {

    List<VirtualTeam> findByName(String name);
    
    VirtualTeam findById(long id);
    
    VirtualTeam findByUser (long userId);
}