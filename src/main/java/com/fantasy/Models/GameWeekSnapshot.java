package com.fantasy.Models;

import javax.persistence.*;
import java.util.Set;


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

    @Column(name = "captain_id")
    private long capitao;

    @Column(name = "game_week_points")
    private long gameWeekPoints;
/*
    @Column (name = "game_week_id")
    private long game_week_id;
*/
    @Column(name = "virtual_team_id")
    private long virtual_team_id;

    public GameWeekSnapshot() {
    }
/*
    public GameWeekSnapshot(Set<Player> players, long capitao, long gameWeekPoints, long gameWeek_id, long virtual_team_id) {
        this.players = players;
        this.capitao = capitao;
        this.gameWeekPoints = gameWeekPoints;
        this.game_week_id = gameWeek_id;
        this.virtual_team_id = virtual_team_id;
    }
*/
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
/*
    public long getGameWeek() {
        return game_week_id;
    }

    public void setGameWeek(long gameWeek) {
        this.game_week_id = gameWeek;
    }
*/
    public Set<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(Set<Player> players)
    {
        this.players = players;
    }

    public long getVirtual_team_id() {
        return virtual_team_id;
    }

    public void setVirtual_team_id(long virtual_team_id) {
        this.virtual_team_id = virtual_team_id;
    }
}
