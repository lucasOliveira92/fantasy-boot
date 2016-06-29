package com.fantasy.Models;

/**
 * Created by Lucas on 29-Jun-16.
 */
public class TeamManagementResponse {

    private String name;

    private int id;

    public TeamManagementResponse(String name) {
        this.name = name;
    }

    public TeamManagementResponse() {
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
