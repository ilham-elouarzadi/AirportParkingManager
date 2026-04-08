package com.example.airportparkingmanager;

public class Vehicule {
    private String plaque;
    private String abonnement;

    public Vehicule(String plaque) {
        this.plaque = plaque;
    }

    public Vehicule(String plaque, String abonnement) {
        this.plaque = plaque;
        this.abonnement = abonnement;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public String getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(String abonnement) {
        this.abonnement = abonnement;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "plaque='" + plaque + '\'' +
                ", abonnement='" + abonnement +
                '}';
    }
}
