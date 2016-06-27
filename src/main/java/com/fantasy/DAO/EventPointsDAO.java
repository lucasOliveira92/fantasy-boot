package com.fantasy.DAO;

import com.fantasy.Models.EventPoints;
import org.springframework.data.repository.CrudRepository;

import java.awt.*;
import java.util.HashMap;


public interface EventPointsDAO extends CrudRepository<EventPoints, Long> {

    int findPointsByEventType (String event_type);
}
