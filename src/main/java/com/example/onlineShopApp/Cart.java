package com.example.onlineShopApp;

public class Cart extends Product{
    private int cantitateCos;
    private double sumaTotala;

    public int getCantitateCos() {
        return cantitateCos;
    }

    public void setCantitateCos(int cantitateCos) {
        this.cantitateCos = cantitateCos;
    }

    public double getSumaTotala() {
        return sumaTotala;
    }

    public void setSumaTotala(double sumaTotala) {
        this.sumaTotala = sumaTotala;
    }
}
