package ca.uwaterloo.cs349;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape.*;
public class canvasCircle implements  canvasShape {
    private double x, y, r;
    Color fillColor;
    Color lineColor;
    double lineThickness;
    boolean isSelected = false;
    double [] dashType = {};
    Circle c;
    public canvasShape copyShape() {
        return new canvasCircle(x,y,r,fillColor, lineColor, lineThickness, dashType);
    }
    canvasCircle(double x, double y, double r, Color fill, Color line, double lineThickness, double [] dash) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.fillColor = fill;
        this.lineColor = line;
        this.lineThickness = lineThickness;
        this.dashType = dash;
    }
    public double getThickness() {
        return lineThickness;
    }
    public void setThickness(double thickness) {
        lineThickness = thickness;
    }
    public void draw(Canvas canvas, GraphicsContext gc) {
        gc.setLineDashes(dashType);
        gc.setLineWidth(lineThickness);
        gc.setFill(fillColor);
        gc.setStroke(lineColor);
        gc.fillOval(x,y,r,r);
        gc.strokeOval(x,y,r,r);
    }
    public boolean contains(double x, double y) {


        double centerX = this.x + r/2;
        double centerY = this.y + r/2;
        System.out.println("mouseX: " + x);
        System.out.println("mouseY: " + y);
        System.out.println("origX: " + this.x);
        System.out.println("origY: " + this.y);
        System.out.println("CenterX: " + centerX);
        System.out.println("CenterY: " + centerY);
        double distanceSquared = (centerX - x) * (centerX - x) + (centerY - y) * (centerY - y);
        if (distanceSquared <= r * r/4) {
            return true;
        }
        return false;

    }

    public void whoAmI() {
        System.out.println("Circle! Fill: + " + fillColor + " X: " + x + "Y: " + y);

    }

    public void selectShape(Canvas canvas, GraphicsContext gc) {
        isSelected = true;
        gc.setLineDashes(dashType);
        if (lineColor != Color.BLACK) {
            gc.setStroke(Color.BLACK);
        } else {
            gc.setStroke(Color.RED);
        }
        gc.strokeOval(x,y,r,r);
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
        System.out.println("RESET MY FILL COLOR");
        this.fillColor = c;
    }
    public void setLineColor(Color c) {
        this.lineColor = c;
    }

    public void translate(double x, double y) {
        double centerX = x;
        double centerY = y;
        this.x = centerX - r/2;
        this.y = centerY - r/2;
    }

    public String writeInfoToFile() {
        // Circle x y r fill lineFill
        return "Circle " + x + " " + y + " " + r + " " + fillColor + " " + lineColor +
                " " + lineThickness+ " " + dashType[0] + "\n";

    }

    public void setLineStyle(double[] s) {
        dashType = s;
    }
    public double[] getLineStyle() {
        return dashType;
    }

}
