package com.example.airportparkingmanager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parking {

    private List<Stationnement> parks;
    private List<Abonnement> abonnements;

    public Parking() {
        this.parks = new ArrayList<>();
        this.abonnements = new ArrayList<>();
    }

    public List<Stationnement> getParks() {
        return parks;
    }

    public void setParks(List<Stationnement> parks) {
        this.parks = parks;
    }

    public void addPark(Stationnement p) {
        parks.add(p);
    }

    public void addAbonnement(Abonnement a) {
        abonnements.add(a);
    }

    public List<Abonnement> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<Abonnement> abonnements) {
        this.abonnements = abonnements;
    }

    public List<Stationnement> getActualParks() {
        return parks.stream()
                .filter(p -> p.getExitDateTime() == null)
                .collect(Collectors.toList());
    }

    public Stationnement getParks(Vehicule vehicle) {
        return parks.stream()
                .filter(p -> p.getVehicle().equals(vehicle))
                .findFirst()
                .orElse(null);
    }

    public Stationnement getActualPark(String plaque) {
        return parks.stream()
                .filter(p -> p.getVehicle().getPlaque().equals(plaque) && p.getExitDateTime() == null)
                .findFirst()
                .orElse(null);
    }

    public void saveExit(Vehicule vehicle) {
        Stationnement park = parks.stream()
                .filter(p -> p.getVehicle().equals(vehicle))
                .findFirst()
                .orElse(null);
        if(park != null)
            park.setExitDateTime(LocalDateTime.now());
        else
            System.out.println("Véhicule non trouvé pour la sortie !");

    }
}