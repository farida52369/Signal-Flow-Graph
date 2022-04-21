package com.example.signalflowgraphs;

import com.example.signalflowgraphs.gui.Shapes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;

import java.net.URL;
import java.util.List;
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

        /*
        Circle c1 = new Circle();
        c1.setCenterX(200);
        c1.setCenterY(500);
        c1.setRadius(50);
        c1.setEffect(new DropShadow());
        c1.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

        });


        Circle c2 = new Circle();
        c2.setCenterX(700);
        c2.setCenterY(500);
        c2.setRadius(50);
        c2.setFill(null);
        c2.setStroke(Color.GOLD);
        c2.setStrokeWidth(4);



        // Arc drawing __


        // Arrow


        double p1x = -8;
        double p1y = 0;

        double p2x = -25;
        double p2y = -7;

        double p3x = -25;
        double p3y = 7;

//        double p1x = 20;
//        double p1y = 20;
//
//        double p2x = 80;
//        double p2y = 20;
//
//        double p3x = 70;
//        double p3y = 100;

        Double[] arrowShape = new Double[]{p1x, p1y, p2x, p2y, p3x, p3y};


        System.out.println(shape.getTranslateX() + " " + shape.getTranslateY());
        System.out.println(arc.getBoundsInLocal().getMinY() + " " + arc.getBoundsInLocal().getMaxY());
//        double deltaY = arc.getEndY() - arc.getStartY();
//        double deltaX = arc.getEndX() - arc.getStartX();
//        double angle = Math.atan2(deltaY, deltaX) * (180 / Math.PI);
//        shape.setRotate(angle);
        // updateRotate(curve);

        drawSpace.getChildren().add(arc);
        drawSpace.getChildren().add(shape);
        drawSpace.getChildren().add(c1);
        drawSpace.getChildren().add(c2);

         */

    }

    public void clear_(ActionEvent event) {
    }
}