package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "GAME_WEEKS")
public class GameWeek implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "game_week_id")
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "number", nullable = false, unique = true)
    private int number;

    @OneToMany(mappedBy="gameWeek", fetch = FetchType.LAZY)
    private List<Game> games;

    @OneToMany(mappedBy="gameWeek")
    private List<GameWeekSnapshot> gameWeekSnapshots;

    protected GameWeek(){}

    public GameWeek(Date date, int number) {
        this.date = date;
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Long getId() {
        return id;
    }

    public List<GameWeekSnapshot> getGameWeekSnapshots() {
        return gameWeekSnapshots;
    }

    public void setGameWeekSnapshots(List<GameWeekSnapshot> gameWeekSnapshots) {
        this.gameWeekSnapshots = gameWeekSnapshots;
    }

    public String prettyPrintDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }
}
