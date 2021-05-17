package ca.uwaterloo.cs349;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;

public class canvasLine implements  canvasShape {
    private double x1, y1, x2, y2;
    Color lineColor;
    boolean isSelected = false;
    double lineThickness;
    double dashType[] = {};
    Line l;
    public double getThickness() {
        return lineThickness;
    }
    public void setThickness(double thickness) {
        lineThickness = thickness;
    }
    public canvasShape copyShape() {
        return new canvasLine(x1,y1,x2, y2,lineColor, lineThickness, dashType);
    }
    canvasLine(double x1, double y1, double x2, double y2, Color line, double lineThickness, double [] dash) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.lineColor = line;
        this.lineThickness = lineThickness;
        this.dashType = dash;
    }
    public void draw(Canvas canvas, GraphicsContext gc) {
        gc.setLineDashes(dashType);
        gc.setLineWidth(lineThickness);
        gc.setStroke(lineColor);
        gc.strokeLine(x1,y1,x2,y2);
    }
    public boolean contains(double x, double y) {
        Line l = new Line(x1,y1,x2,y2);
        if (l.contains(x,y)) {
            return true;
        }
        return false;

    }

    public void whoAmI() {
        System.out.println("Line! Fill: + " + lineColor + " X1: " + x1 + "Y1: " + y1);
    }

    public void selectShape(Canvas canvas, GraphicsContext gc) {
        isSelected = true;
        if (lineColor != Color.BLACK) {
            gc.setStroke(Color.BLACK);
        } else {
            gc.setStroke(Color.RED);
        }
        gc.strokeLine(x1,y1,x2,y2);
    }

    public void unselectShape(Canvas canvas, GraphicsContext gc) {
        isSelected = false;
        draw(canvas, gc);
    }
    public Color getLineColor() {
        return lineColor;
    }
    public Color getFillColor() {
        return null;
    }
    public void setFillColor(Color c)
    {
        return;
    }
    public void setLineColor(Color c) {
        this.lineColor = c;
    }

    public void translate(double x, double y) {
        double centerX = (this.x1 + this.x2) / 2;
        double centerY = (this.y1 + this.y2) / 2;
        double deltaX = x - centerX;
        double deltaY = y - centerY;
        this.x1 += deltaX;
        this.y1 += deltaY;
        this.x2 += deltaX;
        this.y2 += deltaY;
    }

    public String writeInfoToFile() {
        // Line x1 y1 x2 y2 lineColor
        return "Line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + lineColor +
                " " + lineThickness + " " + dashType[0] + "\n";
    }

    public void setLineStyle(double[] s) {
        dashType = s;
    }
    public double[] getLineStyle() {
        return dashType;
    }

}
