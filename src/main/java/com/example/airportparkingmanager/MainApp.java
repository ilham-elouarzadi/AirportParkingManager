package com.example.airportparkingmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage; // On garde une référence du stage principal
        showLogin(); // On appelle la méthode showLogin au démarrage
    }

    private void showLogin() {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/airportparkingmanager/parking-view.fxml"));

            // Création de la scène (1200x800 comme tu l'avais défini)
            Scene scene = new Scene(loader.load(), 1200, 800);

            // Configuration du Stage
            primaryStage.setTitle("Gestion Parking Aéroport - Login");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}