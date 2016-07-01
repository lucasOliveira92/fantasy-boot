package com.fantasy.Models;


import java.util.List;

public class NewTeamResponse {

    private String teamName;

    private long user;

    private List equipa;

    public NewTeamResponse() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public List getEquipa() {
        return equipa;
    }

    public void setEquipa(List equipa) {
        this.equipa = equipa;
    }
}
