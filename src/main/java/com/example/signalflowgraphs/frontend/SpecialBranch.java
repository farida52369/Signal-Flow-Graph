package com.example.signalflowgraphs.frontend;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SpecialBranch {

    private final Circle circle;
    private final AnchorPane drawSpace;
    private final double gain;
    private Circle branch;

    public SpecialBranch(AnchorPane drawSpace, Circle circle, double gain) {
        this.drawSpace = drawSpace;
        this.circle = circle;
        this.gain = gain;

        drawSpecialBranch();
    }

    private void drawSpecialBranch() {
        branch = new Circle();
        branch.setCenterX(circle.getCenterX());
        branch.setCenterY(circle.getCenterY() - circle.getRadius());
        branch.setRadius(circle.getRadius());

        // Attributes
        branch.setFill(null);
        branch.setStroke(Color.BLACK);
        branch.setStrokeWidth(2);

        drawSpace.getChildren().add(2, branch);

        drawArrow();
        drawText();
    }

    private void drawArrow() {
        Polygon arrow = new Polygon();

        // getPoints().addAll(need_wrapper_class_double)
        Double[] values = new Double[]{-14.0, 0.0, 0.0, 4.5, 0.0, -4.5};
        arrow.getPoints().addAll(values);

        // Attributes
        arrow.setFill(Color.BLACK);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(1);

        arrow.setTranslateX(branch.getCenterX());
        arrow.setTranslateY(branch.getCenterY() - branch.getRadius() + 1);

        drawSpace.getChildren().add(arrow);
    }

    private void drawText() {
        Text text = new Text(gain + "");
        text.setFill(Color.BLACK);
        text.setStroke(Color.DARKGOLDENROD);
        text.setStrokeWidth(1);
        text.setFont(Font.font("verdana", 18));

        double x = circle.getCenterX() - 10;
        double y = circle.getCenterY() - 2 * circle.getRadius() - 15;

        text.setX(x);
        text.setY(y);
        drawSpace.getChildren().add(text);
    }
}
