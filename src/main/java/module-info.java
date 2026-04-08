module com.example.airportparkingmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.airportparkingmanager to javafx.fxml;
    exports com.example.airportparkingmanager;
}