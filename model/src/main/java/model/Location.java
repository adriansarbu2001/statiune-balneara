package model;

import java.io.Serializable;

/**
 * Class that represents a review
 * The review has: id, userId representing the user that made the review
 *                 productId representing the product which is reviewing
 */
public class Location implements Serializable {
    private int id;
    private String name;

    public Location() {}

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
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
}
