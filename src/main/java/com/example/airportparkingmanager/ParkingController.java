package com.example.airportparkingmanager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingController {

    @FXML
    private Label heureLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField entreePlaqueInput;
    @FXML
    private TextField sortiePlaqueInput;
    @FXML
    private ListView<Stationnement> vehiculeInParking;
    @FXML
    private ListView<Stationnement> parkHistoryList;
    @FXML
    private ListView<Abonnement> abonnementsList;
    @FXML
    private TextField abonnementPlaqueInput;
    @FXML
    private RadioButton optionA;
    @FXML
    private RadioButton optionB;

    private Parking parking = new Parking();

    private static final double TARIF_PAR_MINUTE = 0.50;
    private static final double TARIF_ABONNEMENT_MENSUEL = 50;
    private static final double TARIF_ABONNEMENT_ANNUEL = 500;
    private static final String ABONNEMENT_ANNUEL = "Annuel";
    private static String ACTIF_STATUS = "ACTIF";
    private static String INACTIFèSTATUS = "INACTIF";

    @FXML
    public void initialize() {
        Timeline heureTimeline = new Timeline( new KeyFrame(Duration.seconds(1), e -> updateHeure()));
        heureTimeline.setCycleCount(Timeline.INDEFINITE);  // Continue en boucle
        heureTimeline.play();

        Timeline dateTimeline = new Timeline( new KeyFrame(Duration.seconds(1), e -> updateDate()) );
        dateTimeline.setCycleCount(Timeline.INDEFINITE);  // Continue en boucle
        dateTimeline.play();

        vehiculeInParking.setCellFactory(lv -> new ListCell<>() {
            private Timeline timeline;

                @Override
                protected void updateItem(Stationnement park, boolean empty) {
                    super.updateItem(park, empty);

                    if (timeline != null) {
                        timeline.stop();
                        timeline = null;
                    }

                    if (empty || park == null) {
                        setText(null);
                        setGraphic(null);
                    }
                    else {
                        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                            long seconds = java.time.Duration.between(park.getEnterDateTime(), LocalDateTime.now()).getSeconds();
                            long minutes = seconds / 60;
                            long remainingSeconds = seconds % 60;
                            String formattedTime = String.format("%02d:%02d", minutes, remainingSeconds);

                            String text = String.format(
                                    "Plaque d'immatriculation : %s\nDurée : %s\nEntrée : %s",
                                    park.getVehicle().getPlaque(),
                                    formattedTime,
                                    park.getEnterDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                            );

                            Platform.runLater(() -> setText(text));
                        }));
                        timeline.setCycleCount(Timeline.INDEFINITE);
                        timeline.play();
                    }
            };
        });


        parkHistoryList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Stationnement park, boolean empty) {
                super.updateItem(park, empty);
                if (empty || park == null) {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    String text = String.format(
                            "Immatriculation : %s\nDurée : %s\nDate d'entrée : %s\nDate de sortie%s",
                            park.getVehicle().getPlaque(),
                            java.time.Duration.between(park.getEnterDateTime(), park.getExitDateTime()).toMinutes(),
                            park.getEnterDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                            park.getExitDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                    );
                    setText(text);
                }
            }
        });

        abonnementsList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Abonnement abonnement, boolean empty) {
                super.updateItem(abonnement, empty);
                if (empty || abonnement == null) {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    String text = String.format(
                            "Immatriculation : %s\nType : %s\nDate de début : %s\nDate de fin%s\nStatus : %s",
                            abonnement.getVehicle().getPlaque(),
                            abonnement.getType(),
                            abonnement.getDateDebut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                            abonnement.getDateFin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                            abonnement.getStatus()
                    );
                    setText(text);
                }
            }
        });
    }

    private void updateHeure() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        heureLabel.setText(formattedTime);
    }

    private void updateDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        dateLabel.setText(formattedDate);
    }

    @FXML
    public void enregistrerEntreeVehicule() {
        String plaque = entreePlaqueInput.getText();
        if(!plaque.equals("")) {
            Stationnement park = parking.getActualPark(plaque);
            System.out.println(park);

            if (park == null || (park.getExitDateTime() != null && park.getExitDateTime().isBefore(LocalDateTime.now())) ){
                Vehicule vehicle = new Vehicule(plaque);
                Stationnement p = new Stationnement(vehicle);
                parking.addPark(p);

                vehiculeInParking.getItems().add(p);
                entreePlaqueInput.setText("");
                System.out.println("Véhicule entré : " + plaque);
            }
            else {
                System.out.println("Véhicule déjà enregistré dans le système");
                Alert alert = new Alert(AlertType.WARNING, "Véhicule déjà enregistré dans le système", ButtonType.OK);
                alert.setTitle("Véhicule déjà enregistré");
                alert.setHeaderText("Véhicule déjà enregistré");
                alert.showAndWait();
            }
        }
        else{
            System.out.println("Veuillez Entrer une plaque");
            // Créer l'alerte avec le message
            Alert alert = new Alert(AlertType.ERROR, "Veuillez Entrer une plaque", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("Champ vide");
            alert.showAndWait();
        }
    }

    @FXML
    public void enregistrerAbonnement() {
        String plaque = abonnementPlaqueInput.getText();
        if(!plaque.equals("") && (optionA.isSelected() || optionB.isSelected())) {
            String typeAbonnement = optionA.isSelected() ? optionA.getText() : optionB.getText();

            Abonnement abonnementExistant = parking.getAbonnements().stream()
                    .filter(
                            a -> a.getVehicle().getPlaque().equals(plaque)
                                    && a.getStatus().equals(ACTIF_STATUS)
                                    && a.getDateFin().isAfter(LocalDateTime.now())).findFirst().orElse(null);

            if(abonnementExistant == null) {
                Vehicule v = new Vehicule(plaque);

                LocalDateTime dateDebutAbonnement = LocalDateTime.now();
                LocalDateTime dateFinAbonnement = typeAbonnement.equals(ABONNEMENT_ANNUEL) ? dateDebutAbonnement.plusYears(1) : dateDebutAbonnement.plusMonths(1);

                Abonnement a = new Abonnement(
                        typeAbonnement,
                        dateDebutAbonnement,
                        dateFinAbonnement,
                        typeAbonnement.equals(ABONNEMENT_ANNUEL) ? TARIF_ABONNEMENT_ANNUEL : TARIF_ABONNEMENT_MENSUEL,
                        v,
                        ACTIF_STATUS
                );

                parking.addAbonnement(a);
                abonnementsList.getItems().add(a);
                abonnementPlaqueInput.setText("");
                optionA.setSelected(false);
                optionB.setSelected(false);
                System.out.println("Abonnement créer avec succès");
            }
            else {
                System.out.println("Un abonnement actif existe déjà pour ce véhicule");
                Alert alert = new Alert(AlertType.WARNING, "Un abonnement actif existe déjà pour ce véhicule", ButtonType.OK);
                alert.setTitle("Abonnement existant");
                alert.setHeaderText("Abonnement existant");
                alert.showAndWait();
            }
        }
        else{
            System.out.println("Veuillez Entrer une plaque et un type d'abonnement");
            // Créer l'alerte avec le message
            Alert alert = new Alert(AlertType.ERROR, "Veuillez Entrer une plaque et un type d'abonnement", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("Champs vides");
            alert.showAndWait();
        }
    }

    private void afficherMontant(Stationnement park, Abonnement abonnement) {

        long tempsTotalEnMinutes = java.time.Duration.between(park.getEnterDateTime(), park.getExitDateTime()).toMinutes();

        String message = String.format("Le véhicule avec la plaque %s a stationné pendant %d minutes.\n" +
                "Montant à payer: %.2f€\nEntrée: %s\nSortie: %s\n%s",
                park.getVehicle().getPlaque(),
                tempsTotalEnMinutes,
                park.getPrice(),
                park.getEnterDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                park.getExitDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                abonnement != null ? "Status abonnement : " + abonnement.getStatus() : ""
        );

        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Montant à payer");
        alert.setHeaderText("Sortie du véhicule");
        alert.showAndWait();
    }

    @FXML
    public void enregistrerSortieVehicule() {
        String plaque = sortiePlaqueInput.getText();
        if(!plaque.equals("")) {

            Stationnement park = parking.getActualPark(plaque);

            if (park != null) {
                Abonnement abonnement = parking.getAbonnements().stream()
                        .filter(a -> a.getVehicle().getPlaque().equals(plaque) && a.getStatus().equals(ACTIF_STATUS) && a.getDateFin().isAfter(LocalDateTime.now()))
                        .findFirst()
                        .orElse(null);

                park.setExitDateTime(LocalDateTime.now());

                long tempsTotalEnMinutes = java.time.Duration.between(park.getEnterDateTime(), park.getExitDateTime()).toMinutes();
                double montant = abonnement == null ? tempsTotalEnMinutes * TARIF_PAR_MINUTE : 0;

                park.setPrice(montant);

                vehiculeInParking.getItems().remove(park);
                parkHistoryList.getItems().add(park);

                afficherMontant(park, abonnement);
                sortiePlaqueInput.setText("");
                System.out.println("Plaque sortie : " + plaque);
            } else {
                System.out.println("Le véhicule avec la plaque " + plaque + " n'est pas dans le parking.");
                Alert alert = new Alert(AlertType.ERROR, "Le véhicule avec la plaque " + plaque + " n'est pas dans le parking.", ButtonType.OK);
                alert.setTitle("Véhicule inconnu");
                alert.setHeaderText("véhicule hors du parking");
                alert.showAndWait();
            }
        }
        else{
            System.out.println("Veuillez Entrer une plaque");
            Alert alert = new Alert(AlertType.ERROR, "Veuillez Entrer une plaque", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("Champ vide");
            alert.showAndWait();
        }
    }
}