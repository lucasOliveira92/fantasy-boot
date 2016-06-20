package com.fantasy.Models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
//@Table(name = "Users")
public class Utilizador implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "utilizador_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne (mappedBy="utilizador")
    private VirtualTeam virtualTeam;


    public VirtualTeam getTeam() {
        return virtualTeam;
    }

    protected Utilizador() {
    }

    public Utilizador(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordl() {
        return password;
    }

    public void setPasswordl(String passwordl) {
        this.password = passwordl;
    }
}