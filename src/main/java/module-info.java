module com.example.tp5_exo1_gui_software {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens com.example.tp5_exo1_gui_software to javafx.fxml;
    exports com.example.tp5_exo1_gui_software;
}