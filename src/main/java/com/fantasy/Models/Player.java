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


    @Column(name = "yellow_total", nullable = false)
    private int yellowTotal;

    @Column(name = "red_total", nullable = false)
    private int redTotal;

    @Column(name = "goal_total", nullable = false)
    private int goalTotal;

    @Column(name = "total_times_bought", nullable = false)
    private int totalTimesBought;

    @Column(name = "total_points", nullable = false)
    private int totalPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_team_id")
    private RealTeam realTeam;

    protected Player(){}

    public Player(String name, String position, int cost, RealTeam team) {
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.realTeam = team;
        this.totalTimesBought=0;
        this.yellowTotal=0;
        this.redTotal=0;
        this.goalTotal=0;
        this.totalPoints=0;
    }

    public Player(String name, String position, int cost) {
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.totalTimesBought=0;
        this.yellowTotal=0;
        this.redTotal=0;
        this.goalTotal=0;
        this.totalPoints=0;
    }

    public void addPontos(int pontos){
        this.totalPoints+= pontos;
    }
    public void addYellow(){
        this.yellowTotal++;
    }
    public void addRed(){
        this.redTotal++;
    }

    public void wasBought(){
        this.totalTimesBought++;
    }

    public void scoredGoal(){
        this.goalTotal++;
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

    public int getYellowTotal() {
        return yellowTotal;
    }

    public void setYellowTotal(int yellowTotal) {
        this.yellowTotal = yellowTotal;
    }

    public int getRedTotal() {
        return redTotal;
    }

    public void setRedTotal(int redTotal) {
        this.redTotal = redTotal;
    }

    public int getGoalTotal() {
        return goalTotal;
    }

    public void setGoalTotal(int goalTotal) {
        this.goalTotal = goalTotal;
    }

    public int getTotalTimesBought() {
        return totalTimesBought;
    }

    public void setTotalTimesBought(int totalTimesBought) {
        this.totalTimesBought = totalTimesBought;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
