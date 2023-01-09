package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Planification implements Serializable {
    private int id;
    private String name;
    private String cnp;
    private LocalDate date;
    private int idl;
    private int idt;
    private LocalDate treatmentDate;
    private LocalTime treatmentTime;

    public Planification() {}

    public Planification(int id, String name, String cnp, LocalDate date, int idl, int idt, LocalDate treatmentDate, LocalTime treatmentTime) {
        this.id = id;
        this.name = name;
        this.cnp = cnp;
        this.date = date;
        this.idl = idl;
        this.idt = idt;
        this.treatmentDate = treatmentDate;
        this.treatmentTime = treatmentTime;
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

    public int getIdl() {
        return idl;
    }

    public void setIdl(int idl) {
        this.idl = idl;
    }

    public int getIdt() {
        return idt;
    }

    public void setIdt(int idt) {
        this.idt = idt;
    }

    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(LocalDate treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public LocalTime getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(LocalTime treatmentTime) {
        this.treatmentTime = treatmentTime;
    }
}
