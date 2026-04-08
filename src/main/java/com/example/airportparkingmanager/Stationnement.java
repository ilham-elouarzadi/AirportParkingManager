package com.example.airportparkingmanager;

import java.time.LocalDateTime;

public class Stationnement {
    private Vehicule vehicle;
    private LocalDateTime enterDateTime;
    private LocalDateTime exitDateTime;
    private double price;

    public Stationnement(Vehicule vehicle) {
        this.vehicle = vehicle;
        this.enterDateTime = LocalDateTime.now();
    }

    public Vehicule getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicule vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getEnterDateTime() {
        return enterDateTime;
    }

    public void setEnterDateTime(LocalDateTime enterDateTime) {
        this.enterDateTime = enterDateTime;
    }

    public LocalDateTime getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(LocalDateTime exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Park{" +
                "vehicle=" + vehicle +
                ", enterDateTime=" + enterDateTime +
                ", exitDateTime=" + exitDateTime +
                '}';
    }
}