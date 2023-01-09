package model;

import java.io.Serializable;

public class MaxPatient implements Serializable {
    private int idl;
    private int idt;
    private int max;

    public MaxPatient() {}

    public MaxPatient(int idl, int idt, int max) {
        this.idl = idl;
        this.idt = idt;
        this.max = max;
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

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
