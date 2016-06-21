package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "GAME_WEEKS")
public class GameWeek implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "game_week_id")
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "number", nullable = false)
    private int number;

    @OneToMany(mappedBy="gameWeek", fetch = FetchType.EAGER)
    private Set<Game> games;

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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Long getId() {
        return id;
    }
}
