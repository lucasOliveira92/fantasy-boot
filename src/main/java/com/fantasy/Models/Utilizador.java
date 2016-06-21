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

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne (mappedBy="utilizador")
    private VirtualTeam virtualTeam;


    public VirtualTeam getTeam() {
        return virtualTeam;
    }

    protected Utilizador() {
    }

    public Utilizador(String name, String email, String password) {
        this.username = name;
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
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}