package com.fantasy.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
//@Table(name = "virtual_teams")
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
    @JoinColumn (name="utilizador")
    private Utilizador utilizador;


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

    public VirtualTeam(String name, Utilizador owner) {
        this.name = name;
        this.utilizador = owner;
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

    public Utilizador getOwner() {
        return utilizador;
    }

    public void setOwner(Utilizador owner) {
        this.utilizador = owner;
    }

    public long getSeason_id() {
        return season_id;
    }

    public void setSeason_id(long season_id) {
        this.season_id = season_id;
    }
}