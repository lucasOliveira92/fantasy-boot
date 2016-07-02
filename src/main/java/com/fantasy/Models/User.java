package com.fantasy.Models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty
    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 15)
    @Column(nullable = false)
    private String password;

    @Transient
    private String passwordCheck;

    public VirtualTeam getVirtualTeam() {
        return virtualTeam;
    }

    public void setVirtualTeam(VirtualTeam virtualTeam) {
        this.virtualTeam = virtualTeam;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    @OneToOne (mappedBy="user")
    private VirtualTeam virtualTeam;

    public VirtualTeam getTeam() {
        return virtualTeam;
    }

    public User() {
    }

    public User(String name, String email, String password) {
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


    public boolean hasVirtualTeam() {
        if (virtualTeam == null)
            return false;
        else
            return true;
    }
}