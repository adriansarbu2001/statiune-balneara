package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class that represents a review
 * The review has: id, userId representing the user that made the review
 *                 productId representing the product which is reviewing
 */
public class Payment implements Serializable {
    private int id;
    private String cnp;
    private LocalDate date;
    private float sum;

    public Payment() {}

    public Payment(int id, String cnp, LocalDate date, float sum) {
        this.id = id;
        this.cnp = cnp;
        this.date = date;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
