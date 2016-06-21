package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "REAL_TEAMS")
public class RealTeam implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "real_team_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "badge_url", nullable = false)
    private String badge;

    @Column(name = "equipment_url", nullable = false)
    private String equipment;

    @OneToMany(mappedBy="realTeam", fetch = FetchType.EAGER)
    private Set<Player> players;

    // deixar esta anotação apenas se for bidirecional
    //@OneToMany(mappedBy="RealTeam")
    //private Set<Game> games;

    protected RealTeam(){}

    public RealTeam(String name, String badge, String equipment) {
        this.name = name;
        this.badge = badge;
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }


    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public Long getId() {
        return id;
    }
/*
    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }*/
}
