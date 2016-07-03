package com.fantasy.DAO;

import com.fantasy.Models.VirtualTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VirtualTeamDAO extends CrudRepository<VirtualTeam, Long> {
    
    VirtualTeam findById(long id);
    
    VirtualTeam findByUserId(long userId);

    List<VirtualTeam> findAllByOrderByTotalPointsDesc();

    List<VirtualTeam> findAll();
}