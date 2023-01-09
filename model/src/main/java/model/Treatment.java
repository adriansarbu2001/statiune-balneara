package model;

import java.io.Serializable;

public class Treatment implements Serializable {
    private int id;
    private String name;
    private int cost;
    private int durationMinutes;

    public Treatment() {}

    public Treatment(int id, String name, int cost, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.durationMinutes = durationMinutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
