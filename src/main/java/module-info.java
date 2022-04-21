module com.example.signalflowgraphs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens com.example.signalflowgraphs to javafx.fxml;
    exports com.example.signalflowgraphs;
}