package com.fantasy.Models;

import java.util.List;


public class TeamManagementResponse {


    private long capitao;

    private long user;

    private List titulares;


    public TeamManagementResponse() {
    }


    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public List getTitulares() {
        return titulares;
    }

    public void setTitulares(List titulares) {
        this.titulares = titulares;
    }


    public long getCapitao() {
        return capitao;
    }

    public void setCapitao(long capitao) {
        this.capitao = capitao;
    }

}
