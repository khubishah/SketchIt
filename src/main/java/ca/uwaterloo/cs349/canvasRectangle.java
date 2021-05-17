package ca.uwaterloo.cs349;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape.*;

public class canvasRectangle implements  canvasShape {
    private double x, y;
    private double width, height;
    Color fillColor;
    Color lineColor;
    double lineThickness;
    boolean isSelected = false;
    double [] dashType = {};
    Rectangle r;

    public double getThickness() {
        return lineThickness;
    }
    public void setThickness(double thickness) {
        lineThickness = thickness;
    }
    public canvasShape copyShape() {
        return new canvasRectangle(x,y,width, height,fillColor, lineColor, lineThickness, dashType);
    }
    canvasRectangle(double x, double y, double width, double height, Color fill, Color line, double lineThickness, double [] dash) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColor = fill;
        this.lineColor = line;
        this.lineThickness = lineThickness;
        this.dashType = dash;
    }
    public void draw(Canvas canvas, GraphicsContext gc) {
        gc.setLineDashes(dashType);
        gc.setLineWidth(lineThickness);
        gc.setFill(fillColor);
        gc.setStroke(lineColor);
        gc.fillRect(x,y,width, height);
        gc.strokeRect(x,y,width, height);

    }
    public void whoAmI() {
        System.out.println("RECTANGLE! Fill: + " + fillColor + " X: " + x + "Y: " + y);

    }
    public boolean contains(double x, double y) {
        if (x >= this.x && x <= (this.x + width) && y >= this.y && y <= (this.y + height)) {
            return true;
        }
        return false;
    }
    public void selectShape(Canvas canvas, GraphicsContext gc) {
        isSelected = true;
        System.out.println("Rectangle dash type: " + dashType);
        gc.setLineDashes(dashType);
        if (lineColor != Color.BLACK) {
            gc.setStroke(Color.BLACK);
        } else {
            gc.setStroke(Color.RED);
        }

        gc.strokeRect(x,y,width, height);
    }
    public void unselectShape(Canvas canvas, GraphicsContext gc) {
        isSelected = false;
        draw(canvas, gc);
    }
    public Color getLineColor() {
        return lineColor;
    }
    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color c)
    {
        this.fillColor = c;
    }
    public void setLineColor(Color c) {
        this.lineColor = c;
    }

    public void translate(double x, double y) {

        this.x = x - width/2;
        this.y = y - height/2;

    }

    public String writeInfoToFile() {
        // Rectangle x y width height fill linefill
        return "Rectangle " + x + " " + y + " " + width + " " + height + " " + fillColor + " " + lineColor +
                " " + lineThickness + " " + dashType[0] + "\n";

    }

    public void setLineStyle(double[] s) {
        dashType = s;
    }
    public double[] getLineStyle() {
        return dashType;
    }

}
