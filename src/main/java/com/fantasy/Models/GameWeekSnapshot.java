package com.fantasy.Models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "GAME_WEEK_SNAPSHOTS")
public class GameWeekSnapshot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "snapshot_id")
    private Long id;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="snapshot_players", joinColumns=@JoinColumn(name="snapshot_id"), inverseJoinColumns=@JoinColumn(name="player_id"))
    private List<Player> players;

    @Column(name = "captain_id")
    private long capitao;

    @Column(name = "game_week_points")
    private int gameWeekPoints;

    @Column(name = "game_week_cumulative_points")
    private long gameWeekCumulativePoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="game_week_id")
    private GameWeek gameWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="virtual_team_id")
    private VirtualTeam virtualTeam;

    public GameWeekSnapshot() {
    }

    public GameWeekSnapshot(List<Player> players, long capitao, GameWeek gameWeek, VirtualTeam virtualTeam) {
        this.players = players;
        this.capitao = capitao;
        this.gameWeekPoints = 0;
        this.gameWeekCumulativePoints = 0;
        this.gameWeek = gameWeek;
        this.virtualTeam = virtualTeam;
    }

    public Long getId() {
        return id;
    }

    public long getGameWeekCumulativePoints() {
        return gameWeekCumulativePoints;
    }

    public void setComulativePOints(long comulativo){
        this.gameWeekCumulativePoints = comulativo;
    }

    public void addPointsFromEvent(long points){
        this.gameWeekPoints+= points;
        this.gameWeekCumulativePoints+=points;
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


    public List<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(List<Player> players)
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

    public void setGameWeekCumulativePoints(long gameWeekCumulativePoints) {
        this.gameWeekCumulativePoints = gameWeekCumulativePoints;
    }
}
