package com.fantasy.DAO;

import com.fantasy.Models.RealTeam;
import org.springframework.data.repository.CrudRepository;

public interface RealTeamDAO extends CrudRepository<RealTeam, Long> {

    RealTeam findById(long real_team_id);
}