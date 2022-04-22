package com.example.signalflowgraphs.gui;

import com.example.signalflowgraphs.HelloApplication;
import com.example.signalflowgraphs.HelloController;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Shapes {

    private List<Circle> circles;
    private List<Text> circlesText;
    private List<Branch> branches;
    private List<SpecialBranch> spBranches;
    private static final int width = 1250;
    private static final int height = 614;
    private int numOfNodes;
    private final AnchorPane drawSpace;
    private double cur_radius;
    private double cur_distance;
    private double gain;
    private boolean timeForEdges;
    private boolean noMoreNodes;
    private Circle circle_1;
    private boolean firstClick;
    private AudioClip error_alert;
    private AudioClip pass_alert;

    public Shapes(AnchorPane drawSpace) {
        // Global Variables
        this.circles = new LinkedList<>();
        this.circlesText = new LinkedList<>();
        this.branches = new LinkedList<>();
        this.spBranches = new LinkedList<>();

        this.drawSpace = drawSpace;
        this.numOfNodes = 0;
        this.gain = 3;
        this.timeForEdges = false;
        this.noMoreNodes = false;
        this.firstClick = true;

        File error = new File("src/main/resources/com/example/signalflowgraphs/error_alert.wav");
        File pass = new File("src/main/resources/com/example/signalflowgraphs/pass_alert.wav");
        this.error_alert = new AudioClip(error.toURI().toString());
        this.pass_alert = new AudioClip(pass.toURI().toString());

        // Initialization :)
        addNode();
        addNode();
    }

    public void addNode() {
        if (!noMoreNodes) {
            numOfNodes += 1;
            setCalculation();

            Circle curCircle = null;
            Text curText;

            int index; // From First to before the last
            for (index = 0; index < circles.size(); index++) {
                // Set Circles places
                curCircle = circles.get(index);
                if (index == 0) curCircle.setFill(Color.LIGHTPINK);
                curCircle.setCenterX(index * cur_distance + cur_distance / 2);
                curCircle.setRadius(cur_radius);

                // Set Text Places
                curText = circlesText.get(index);
                curText.setX(curCircle.getCenterX() - 8);
                curText.setY(curCircle.getCenterY() + 5);
            }
            if (index != 0 && circles.size() > 1) curCircle.setFill(Color.LIGHTGRAY);

            // Last Node Added
            curCircle = new Circle();
            curCircle.setCenterX(index * cur_distance + cur_distance / 2);
            curCircle.setCenterY(height / 2.0);
            curCircle.setRadius(cur_radius);
            curCircle.setEffect(new DropShadow());
            curCircle.setFill(Color.LIGHTGRAY);

            if (index == 0) curCircle.setFill(Color.LIGHTPINK);
            else curCircle.setFill(Color.LIGHTYELLOW); // End Node Color

            // add Mouse Event Handler
            eventHandler(curCircle);

            // Adding to global Circles
            circles.add(curCircle);

            // Add to Frontend
            drawSpace.getChildren().add(curCircle);

            // add Text for added Node
            addText(curCircle, index);
        }
    }

    private void eventHandler(Circle curCircle) {
        curCircle.setOnMouseClicked(mouseEvent -> {
            if (timeForEdges) {
                if (firstClick) {
                    circle_1 = curCircle;
                    firstClick = false;

                    // play an alert
                    pass_alert.play();
                } else {
                    int nodeI = circles.indexOf(circle_1);
                    int nodeII = circles.indexOf(curCircle);
                    gain = HelloController.gainVal;
                    if (nodeI == nodeII) {
                        if (!(nodeI == 0 || nodeI == circles.size() - 1)) {
                            spBranches.add(new SpecialBranch(drawSpace, circle_1, gain));
                            pass_alert.play();
                        } else error_alert.play();
                    } else {
                        if (!(nodeI == circles.size() - 1)) {
                            branches.add(new Branch(drawSpace, circle_1, curCircle, gain));
                            pass_alert.play();
                        } else error_alert.play();
                    }
                    firstClick = true;
                    // timeForEdges = false;
                }
            }
        });
    }

    private void addText(Circle curCircle, int index) {
        Text text = new Text((index + 1) + "");
        text.setFill(Color.BLACK);
        text.setFont(Font.font("verdana", 18));
        text.setX(curCircle.getCenterX() - 8);
        text.setY(curCircle.getCenterY() + 5);

        // Adding Text To Global Variable
        circlesText.add(text);

        // Adding to Frontend
        drawSpace.getChildren().add(text);
    }

    private void setCalculation() {
        this.cur_radius = 200 / (numOfNodes * 1.0);
        this.cur_distance = width / (numOfNodes * 1.0);
    }

    public void addEdge() {
        // variables drawing edges
        timeForEdges = true;
        noMoreNodes = true;
    }
}
