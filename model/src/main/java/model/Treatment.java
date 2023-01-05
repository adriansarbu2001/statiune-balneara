package model;

import java.io.Serializable;

/**
 * Class that represents a review
 * The review has: id, userId representing the user that made the review
 *                 productId representing the product which is reviewing
 */
public class Treatment implements Serializable {
    private int id;
    private String name;
    private int cost;
    private int durationMinutes;
    private int maxPatients;

    public Treatment() {}

    public Treatment(int id, String name, int cost, int durationMinutes, int maxPatients) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.durationMinutes = durationMinutes;
        this.maxPatients = maxPatients;
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

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }
}
