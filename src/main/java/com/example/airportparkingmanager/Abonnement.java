package com.example.airportparkingmanager;

import java.time.LocalDateTime;

public class Abonnement {

    private String type;
    private String status;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Vehicule vehicle;
    private double tarif;

    public Abonnement(String type, LocalDateTime dateDebut, LocalDateTime dateFin, double tarif, Vehicule vehicle, String status) {
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.tarif = tarif;
        this.vehicle = vehicle;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public Vehicule getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicule vehicle) {
        this.vehicle = vehicle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}