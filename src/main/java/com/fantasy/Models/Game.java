package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
//@Table(name = "GAMES")
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "game_id")
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "team1_score")
    private int team1_score;

    @Column(name = "team2_score")
    private int team2_score;

    @OneToOne
    @JoinColumn(name="team1_ID")
    private RealTeam team1;

    @OneToOne
    @JoinColumn(name="team2_ID")
    private RealTeam team2;

    @Column(name="journey_id")
    private long journey_id;

    @OneToMany(mappedBy="game", fetch = FetchType.EAGER)
    private Set<GameEvent> events;

    protected Game(){}

    public Game(Date date, RealTeam team1, RealTeam team2, long journey_id) {
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.journey_id = journey_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTeam1_score() {
        return team1_score;
    }

    public void setTeam1_score(int team1_score) {
        this.team1_score = team1_score;
    }

    public int getTeam2_score() {
        return team2_score;
    }

    public void setTeam2_score(int team2_score) {
        this.team2_score = team2_score;
    }

    public RealTeam getTeam1() {
        return team1;
    }

    public void setTeam1(RealTeam team1) {
        this.team1 = team1;
    }

    public RealTeam getTeam2() {
        return team2;
    }

    public void setTeam2(RealTeam team2) {
        this.team2 = team2;
    }

    public Long getId() {
        return id;
    }

    public long getJourney_id() {
        return journey_id;
    }

    public void setJourney_id(long journey_id) {
        this.journey_id = journey_id;
    }

    public Set<GameEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<GameEvent> events) {
        this.events = events;
    }

    public void addEvent(GameEvent event){
        this.events.add(event);
    }
}
