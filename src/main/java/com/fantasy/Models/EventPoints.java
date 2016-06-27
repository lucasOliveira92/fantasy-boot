package com.fantasy.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by Lucas on 27-Jun-16.
 */

@Entity
@Table(name = "EVENT_POINTS")
public class EventPoints {
    @Id
    @Column(name = "event_type")
    private String eventType;

    @Column(name = "points")
    private int points;

    public EventPoints() {
    }

    public EventPoints(String eventType, int points) {
        this.eventType = eventType;
        this.points = points;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
