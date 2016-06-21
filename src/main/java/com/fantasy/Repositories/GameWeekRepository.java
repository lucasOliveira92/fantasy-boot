package com.fantasy.Repositories;


import com.fantasy.Models.Journey;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface JourneyRepository extends CrudRepository<Journey, Long> {

    Journey findById(long journey_id);
    Set<Journey> findByNumber(int number);
}