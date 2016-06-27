package com.fantasy.Models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "VIRTUAL_TEAMS")
public class VirtualTeam implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "virtual_team_id")
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "budget", nullable = false)
    private int budget;

    @Column(name = "number_transfers", nullable = false)
    private int numberTransfers;

    @OneToOne
    @JoinColumn (name="user_id")
    private User user;

    @Column(name = "total_points", nullable = false)
    private long totalPoints;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="virtual_team_players", joinColumns=@JoinColumn(name="virtual_team_id"), inverseJoinColumns=@JoinColumn(name="player_id"))
    private List<Player> players;

    @OneToMany(mappedBy="virtualTeam")
    private List<GameWeekSnapshot> gameWeekSnapshots;

    public VirtualTeam(){
        this.name = "";
        this.budget = 1000;
        this.numberTransfers = 2;
        this.totalPoints = 0;
    }

    public VirtualTeam(String name, int budget, int numberTransfers) {
        this.name = name;
        this.budget = budget;
        this.numberTransfers = numberTransfers;
        this.totalPoints = 0;
    }

    public VirtualTeam(String name) {
        this.name = name;
        this.budget = 1000;
        this.numberTransfers = 0;
        this.totalPoints = 0;
    }

    public VirtualTeam(String name, User owner) {
        this.name = name;
        this.user = owner;
        this.budget = 1000;
        this.numberTransfers = 0;
        this.totalPoints = 0;
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

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getNumberTransfers() {
        return numberTransfers;
    }

    public void setNumberTransfers(int numberTransfers) {
        this.numberTransfers = numberTransfers;
    }

    public User getOwner() {
        return user;
    }

    public void setOwner(User owner) {
        this.user = owner;
    }

    public List<Player> getPlayers()
    {
        return players;
    }
    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    public List<Player> addPlayer(Player p){
        if(players == null){
            this.players = new ArrayList<>();
        }
        this.players.add(p);
        return players;
    }

    public List<Player> removePlayer(Player p){
        if(players == null){
            return this.players = new ArrayList<>();
        }
        for(Player pl: this.players){
            if(pl.getName() == p.getName()){
                this.players.remove(pl);
            }
        }
        return this.players;
    }

    public long getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(long totalpoins) {
        this.totalPoints = totalpoins;
    }

    public void addPoints(long poins) {
        this.totalPoints += poins;
    }

    public List<GameWeekSnapshot> getGameWeekSnapshots() {
        return gameWeekSnapshots;
    }

    public void setGameWeekSnapshots(List<GameWeekSnapshot> gameWeekSnapshots) {
        this.gameWeekSnapshots = gameWeekSnapshots;
    }

    public int getPointsByGameWeek(long game_week_id){
        int pontos=0;
        for (GameWeekSnapshot gameweek:this.gameWeekSnapshots) {
            if(gameweek.getGameWeek().getId()==game_week_id){
                pontos=gameweek.getGameWeekPoints();
            }
        }
        return pontos;
    }

    public int getComulativePointsByGameWeek(long game_week_id){
        int pontos=0;
        for (GameWeekSnapshot gameweek:this.gameWeekSnapshots) {
            if(gameweek.getGameWeek().getId()==game_week_id){
                pontos=gameweek.getGameWeekComulativePoints();
            }
        }
        return pontos;
    }
}