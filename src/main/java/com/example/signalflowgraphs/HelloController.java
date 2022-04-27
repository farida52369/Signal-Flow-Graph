package com.example.signalflowgraphs;

import com.example.signalflowgraphs.backend.Mason;
import com.example.signalflowgraphs.frontend.Shapes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane drawSpace;

    @FXML
    private TextField numberOfNodes;

    @FXML
    private TextField gain;

    private Shapes shapes;
    private boolean firstAddEdge;
    public static double gainVal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapes = new Shapes(drawSpace);

        // get a warning to the user that he can't add
        // any more Nodes unless he clear all
        firstAddEdge = true;

        gain.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                gain.setText(oldValue);
            }
            try {
                gainVal = Double.parseDouble(gain.getText());
            } catch (NumberFormatException nfe) {
                // Oooops
            }
        });
    }

    public void addEdge_() {
        // gain
        gainVal = Double.parseDouble(gain.getText());
        if (firstAddEdge) {
            shapes.error_alert.play();
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setTitle("Oooooops, Warning!");
            warning.setHeaderText(null);
            warning.setGraphic(null);
            warning.setContentText("If You pushed OK, You can't add any nodes unless pushing (Clear)");

            // Setting Attributes __ CSS for text in alert
            DialogPane dialogPane = warning.getDialogPane();
            dialogPane.setStyle("-fx-font-size: larger; -fx-font-family: vijaya;");
            dialogPane.lookupButton(ButtonType.OK).setStyle("-fx-font-size: smaller;");
            dialogPane.lookupButton(ButtonType.CANCEL).setStyle("-fx-font-size: smaller;");

            warning.showAndWait();
            if (warning.getResult() == ButtonType.OK) {
                shapes.addEdge();
            } else return;
            firstAddEdge = false;
        }
        shapes.addEdge();
    }

    public void solve_() {
        // Output
        try {
            // For root
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("output.fxml"));
            Scene scene = new Scene(loader.load(), 1250, 700);
            Stage stage = new Stage();
            stage.setTitle("Output of Signal Flow Graphs");

            //
            Mason mason = shapes.solve();
            Output output = loader.getController();

            output.displayOutput(mason.getFP(), mason.getGainFP(),
                    mason.getFBL(), mason.getGainFBL(),
                    mason.getNTL(), mason.getGainNTL(),
                    mason.getValues(), mason.output()
            );

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addNode_() {
        shapes.addNode();

        // update text_area for number of nodes
        int oldVal = Integer.parseInt(numberOfNodes.getText());
        numberOfNodes.setText((oldVal + 1) + "");
    }

    public void clear_() {
        drawSpace.getChildren().clear();

        // when clear set all
        shapes = new Shapes(drawSpace);
        firstAddEdge = true;
        numberOfNodes.setText(2 + "");
    }
}