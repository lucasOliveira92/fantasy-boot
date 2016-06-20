package com.fantasy.Models;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Lucas on 20-Jun-16.
 */

@Entity
public class SnapshotEquipaJornada {
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

    @Column(name = "pontos_jornada")
    private long pontosJornada;

    public Long getId() {
        return id;
    }


    public long getCapitao() {
        return capitao;
    }

    public void setCapitao(long capitao) {
        this.capitao = capitao;
    }

    public long getPontosJornada() {
        return pontosJornada;
    }

    public void setPontosJornada(long pontosJornada) {
        this.pontosJornada = pontosJornada;
    }
}
