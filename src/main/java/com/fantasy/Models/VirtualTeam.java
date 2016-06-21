package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VIRTUAL_TEAMS")
public class VirtualTeam implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "virtual_team_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "budget", nullable = false)
    private int budget;

    @Column(name = "number_transfers", nullable = false)
    private int numberTransfers;

    @Column(name = "captain_id")
    private Long idCaptain;

    @Column(name="season_id", nullable = false)
    private long season_id;


    @OneToOne
    @JoinColumn (name="user_id")
    private User user;


    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="virtual_team_players", joinColumns=@JoinColumn(name="virtual_team_id"), inverseJoinColumns=@JoinColumn(name="player_id"))
    private Set<Player> players;

    public Set<Player> getPlayers()
    {
        return players;
    }
    public void setPlayers(Set<Player> players)
    {
        this.players = players;
    }

    public Set<Player> addPlayer(Player p){
        if(players == null){
            this.players = new HashSet<>();
        }
        this.players.add(p);
        return players;
    }
    
    public Set<Player> removePlayer(Player p){
        if(players == null){
            return this.players = new HashSet<>();
        }
        for(Player pl: this.players){
            if(pl.getName() == p.getName()){
                this.players.remove(pl);
            }
        }
        return this.players;
    }


    public VirtualTeam(){

    }

    public VirtualTeam(String name, int budget, int numberTransfers) {
        this.name = name;
        this.budget = budget;
        this.numberTransfers = numberTransfers;
    }

    public VirtualTeam(String name) {
        this.name = name;
        this.budget = 1000;
        this.numberTransfers = 0;
    }

    public VirtualTeam(String name, User owner) {
        this.name = name;
        this.user = owner;
        this.budget = 1000;
        this.numberTransfers = 0;
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

    public Long getIdCaptain() {
        return idCaptain;
    }

    public void setIdCaptain(Long idCaptain) {
        this.idCaptain = idCaptain;
    }

    public User getOwner() {
        return user;
    }

    public void setOwner(User owner) {
        this.user = owner;
    }

    public long getSeason_id() {
        return season_id;
    }

    public void setSeason_id(long season_id) {
        this.season_id = season_id;
    }
}