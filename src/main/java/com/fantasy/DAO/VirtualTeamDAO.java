package com.fantasy.DAO;

import com.fantasy.Models.VirtualTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VirtualTeamDAO extends CrudRepository<VirtualTeam, Long> {

    List<VirtualTeam> findByName(String name);
    
    VirtualTeam findById(long id);
    
    VirtualTeam findByUserId(long userId);
}