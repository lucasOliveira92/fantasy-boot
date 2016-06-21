package com.fantasy.Models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
//@Table(username = "Users")
public class Utilizador implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "utilizador_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne (mappedBy="utilizador")
    private VirtualTeam virtualTeam;


    public VirtualTeam getTeam() {
        return virtualTeam;
    }

    public Utilizador() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setPassword(String passwordl) {
        this.password = passwordl;
    }
}