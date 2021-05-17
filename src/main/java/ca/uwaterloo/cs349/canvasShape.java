package ca.uwaterloo.cs349;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.GraphicsContext;

interface canvasShape {
    void draw(Canvas canvas, GraphicsContext gc);
    boolean contains(double x, double y);
    void selectShape(Canvas canvas, GraphicsContext gc);
    void unselectShape(Canvas canvas, GraphicsContext gc);
    Color getLineColor();
    Color getFillColor();
    void setFillColor(Color c);
    void setLineColor(Color c);
    void translate(double x, double y);
    String writeInfoToFile();
    void whoAmI();
    void setThickness(double t);
    double getThickness();
    void setLineStyle(double[] s);
    double[] getLineStyle();
    canvasShape copyShape();

}

