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
    private int gameWeekPoints;

    @Column(name = "game_week_comulative_points")
    private int gameWeekComulativePoints;

    @ManyToOne
    @JoinColumn(name ="game_week_id")
    private GameWeek gameWeek;

    @ManyToOne
    @JoinColumn(name ="virtual_team_id")
    private VirtualTeam virtualTeam;

    public GameWeekSnapshot() {
    }

    public GameWeekSnapshot(Set<Player> players, long capitao, int gameWeekPoints, GameWeek gameWeek, VirtualTeam virtualTeam) {
        this.players = players;
        this.capitao = capitao;
        this.gameWeekPoints = gameWeekPoints;
        this.gameWeek = gameWeek;
        this.virtualTeam = virtualTeam;
    }

    public GameWeekSnapshot(Set<Player> players, long capitao, int gameWeekPoints) {
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

    public int getGameWeekPoints() {
        return gameWeekPoints;
    }

    public void setGameWeekPoints(int gameWeekPoints) {
        this.gameWeekPoints = gameWeekPoints;
    }

    public Set<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(Set<Player> players)
    {
        this.players = players;
    }

    public GameWeek getGameWeek() {
        return gameWeek;
    }

    public void setGameWeek(GameWeek gameWeek) {
        this.gameWeek = gameWeek;
    }

    public VirtualTeam getVirtualTeam() {
        return virtualTeam;
    }

    public void setVirtualTeam(VirtualTeam virtualTeam) {
        this.virtualTeam = virtualTeam;
    }

    public int getGameWeekComulativePoints() {
        return gameWeekComulativePoints;
    }

    public void setGameWeekComulativePoints(int gameWeekComulativePoints) {
        this.gameWeekComulativePoints = gameWeekComulativePoints;
    }
}
