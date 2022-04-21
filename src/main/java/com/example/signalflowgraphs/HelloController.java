package com.example.signalflowgraphs;

import com.example.signalflowgraphs.gui.Shapes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapes = new Shapes(drawSpace);
    }

    public void addEdge_(ActionEvent event) {
        // gain
        shapes.addEdge(2);
    }

    public void solve_(ActionEvent event) {
    }


    public void addNode_(ActionEvent event) {
        shapes.addNode();
    }

    public void clear_(ActionEvent event) {
    }
}