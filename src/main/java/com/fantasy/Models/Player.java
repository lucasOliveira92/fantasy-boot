package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@Table(name = "Players")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name="real_team_id", nullable = false)
    private long realTeamId;

    protected Player(){};

    public Player(String name, String position, int cost, long realTeam_id) {
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.realTeamId = realTeam_id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public long getRealTeam_id() {
        return realTeamId;
    }

    public void setRealTeam_id(long realTeam_id) {
        this.realTeamId = realTeam_id;
    }
}
