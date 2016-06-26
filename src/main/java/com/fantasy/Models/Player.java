package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PLAYERS")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_team_id")
    private RealTeam realTeam;

    protected Player(){}

    public Player(String name, String position, int cost, RealTeam team) {
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.realTeam = team;
    }

    public Player(String name, String position, int cost) {
        this.name = name;
        this.position = position;
        this.cost = cost;
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

    public RealTeam getRealTeam() {
        return realTeam;
    }

    public void setRealTeam(RealTeam realTeam) {
        this.realTeam = realTeam;
    }

    public String getPositionFullName(){
        String response = "";
        switch (position){
            case "GK":
                response = "Goalkeeper";
                break;
            case "DEF":
                response = "Defender";
                break;
            case "MID":
                response = "Midfielder";
                break;
            case "FOR":
                response = "Forward";
                break;
            default:
                break;
        }
        return response;
    }
}
