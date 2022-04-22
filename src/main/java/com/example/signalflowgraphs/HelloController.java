package com.example.signalflowgraphs;

import com.example.signalflowgraphs.gui.Shapes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane drawSpace;

    @FXML
    private TextField numberOfNodes;

    @FXML
    private TextField gain;

    @FXML
    private Button addNode;

    @FXML
    private Button addEdge;

    @FXML
    private Button solve;

    @FXML
    private Button clear;

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
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                gain.setText(oldValue);
            }
            try {
                gainVal = Double.parseDouble(gain.getText());
            } catch (NumberFormatException nfe) {
                // Oooops
            }
        });

    }

    public void addEdge_(ActionEvent event) {
        // gain
        gainVal = Double.parseDouble(gain.getText());

        if (firstAddEdge) {
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setTitle("Oooops, Warning!");
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

    public void solve_(ActionEvent event) {
    }


    public void addNode_(ActionEvent event) {
        shapes.addNode();

        // update text_area for number of nodes
        int oldVal = Integer.parseInt(numberOfNodes.getText());
        numberOfNodes.setText((oldVal + 1) + "");
    }

    public void clear_(ActionEvent event) {
        drawSpace.getChildren().clear();

        // when clear set all
        shapes = new Shapes(drawSpace);
        firstAddEdge = true;
        numberOfNodes.setText(2 + "");
    }
}