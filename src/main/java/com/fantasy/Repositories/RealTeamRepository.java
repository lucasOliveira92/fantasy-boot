package com.fantasy.Repositories;

import com.fantasy.Models.RealTeam;
import org.springframework.data.repository.CrudRepository;

public interface RealTeamRepository extends CrudRepository<RealTeam, Long> {

    RealTeam findById(long real_team_id);
    RealTeam findByName(String name);
}