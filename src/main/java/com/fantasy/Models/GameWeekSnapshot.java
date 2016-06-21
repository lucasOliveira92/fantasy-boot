package com.fantasy.Models;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Lucas on 20-Jun-16.
 */

@Entity
@Table(name = "GAME_WEEK_SNAPSHOTS")
public class GameWeekSnapshot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "snapshot_id")
    private Long id;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="snapshot_players", joinColumns=@JoinColumn(name="snapshot_id"), inverseJoinColumns=@JoinColumn(name="player_id"))
    private Set<Player> players;

    public Set<Player> getPlayers()
    {
        return players;
    }
    public void setPlayers(Set<Player> players)
    {
        this.players = players;
    }

    @Column(name = "captain_id")
    private long capitao;

    @Column(name = "game_week_points")
    private long gameWeekPoints;

    @Column (name = "game_week")
    private long gameWeek;


    public GameWeekSnapshot() {
    }

    public GameWeekSnapshot(Set<Player> players, long capitao, long gameWeekPoints) {
        this.players = players;
        this.capitao = capitao;
        this.gameWeekPoints = gameWeekPoints;
    }

    public Long getId() {
        return id;
    }


    public long getCapitao() {
        return capitao;
    }

    public void setCapitao(long capitao) {
        this.capitao = capitao;
    }

    public long getGameWeekPoints() {
        return gameWeekPoints;
    }

    public void setGameWeekPoints(long gameWeekPoints) {
        this.gameWeekPoints = gameWeekPoints;
    }

    public long getGameWeek() {
        return gameWeek;
    }

    public void setGameWeek(long gameWeek) {
        this.gameWeek = gameWeek;
    }
    
    

    public void setId(Long id) {
        this.id = id;
    }
}
