package com.example.signalflowgraphs.frontend;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Branch {

    private final AnchorPane drawSpace;
    private final Circle circle_1;
    private final Circle circle_2;
    private QuadCurve arc;
    private final double gain;
    private boolean negative;

    public Branch(AnchorPane drawSpace, Circle circle_1, Circle circle_2, double gain) {
        this.drawSpace = drawSpace;
        this.circle_1 = circle_1;
        this.circle_2 = circle_2;
        this.gain = gain;
        this.negative = false;

        // Draw My Arc.
        drawArc();
    }

    private void drawArc() {
        arc = new QuadCurve();

        // Start, End Points
        arc.startXProperty().bind(circle_1.centerXProperty());
        arc.startYProperty().bind(circle_1.centerYProperty());
        arc.endXProperty().bind(circle_2.centerXProperty());
        arc.endYProperty().bind(circle_2.centerYProperty());

        // Controls
        double X = (circle_1.getCenterX() + circle_2.getCenterX()) / 2;
        double Y = (circle_1.getCenterX() - circle_2.getCenterX()) / 2 + 360;
        arc.setControlX(X);
        arc.setControlY(Y);

        // Attributes
        arc.setStroke(Color.BLACK);
        arc.setStrokeWidth(2);
        arc.setFill(null);

        if (circle_1.getCenterX() > circle_2.getCenterX()) {
            negative = true;
        }

        drawSpace.getChildren().add(2, arc);
        drawArrow();
        arcText();
    }

    private void drawArrow() {
        Polygon arrow = new Polygon();

        // getPoints().addAll(need_wrapper_class_double)
        Double[] values;
        if (negative) values = new Double[]{-14.0, 0.0, 0.0, 4.5, 0.0, -4.5};
        else values = new Double[]{0.0, 0.0, -14.0, -4.5, -14.0, 4.5};

        arrow.getPoints().addAll(values);
        arrow.setFill(Color.BLACK);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(1);

        arrow.setTranslateX((arc.getBoundsInLocal().getMinX() + arc.getBoundsInLocal().getMaxX()) / 2);
        if (negative) arrow.setTranslateY(arc.getBoundsInLocal().getMaxY() - 1);
        else arrow.setTranslateY(arc.getBoundsInLocal().getMinY() + 2);

        drawSpace.getChildren().add(arrow);
    }

    private void arcText() {
        Text text = new Text(gain + "");
        text.setFill(Color.BLACK);
        text.setStroke(Color.DARKGOLDENROD);
        text.setStrokeWidth(1);
        text.setFont(Font.font("verdana", 18));

        double x = (arc.getBoundsInLocal().getMinX() + arc.getBoundsInLocal().getMaxX()) / 2;
        double y;
        if (negative) {
            y = arc.getBoundsInLocal().getMaxY() + 20;
            x += 10;
        } else {
            y = arc.getBoundsInLocal().getMinY() - 10;
            x -= 10;
        }

        text.setX(x);
        text.setY(y);
        drawSpace.getChildren().add(text);
    }
}
