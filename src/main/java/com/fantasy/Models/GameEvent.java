package com.fantasy.Models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
//@Table(name = "GAME_EVENT")
public class GameEvent implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "game_event_id")
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "minute", nullable = false)
    private int minute;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    @JoinColumn(name="player_id")
    private Player player;

    protected GameEvent(){}

    public GameEvent(String type, int minute, Game game, Player player) {
        this.type = type;
        this.minute = minute;
        this.game = game;
        this.player = player;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
