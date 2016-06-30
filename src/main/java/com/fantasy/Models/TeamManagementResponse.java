package com.fantasy.Models;

import java.util.List;

/**
 * Created by Lucas on 29-Jun-16.
 */
public class TeamManagementResponse {


    private int capitao;

    private int user;

    private List titulares;


    public TeamManagementResponse() {
    }


    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public List getTitulares() {
        return titulares;
    }

    public void setTitulares(List titulares) {
        this.titulares = titulares;
    }


    public int getCapitao() {
        return capitao;
    }

    public void setCapitao(int capitao) {
        this.capitao = capitao;
    }

}
