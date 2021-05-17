package ca.uwaterloo.cs349;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
class canvasView extends Pane implements IView {


    private Model model; // reference to the model
    Canvas canvas;
    Group group;
    GraphicsContext gc;
    Rectangle rect;
    Circle circle;
    double[] init = {1};

    Line line;
    canvasView(Model model) {
        this.model = model;
        canvas = this.model.canvas;
        canvas = new Canvas(1000,700);
        gc = this.model.gc;
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(3);
        gc.setLineDashes(init);
        gc.setLineDashOffset(5);

        canvas.setOnMousePressed(event -> {
            System.out.println("canvas mouse pressed");
            System.out.println(model.actionButton);
            gc.setStroke(model.currentLineFill);
            gc.setFill(model.currentFill);
            gc.setLineDashes(model.lineStyleChoice);
            gc.setLineWidth(model.currentLineThickness);
            if (model.tryingToErase == true) {
                checkShapeSelection(event.getX(), event.getY());
                model.shapes.remove(model.currentShape);
                model.deselectCurrentShape();

            } else if (model.tryingToCut == true) {
                // delete shape but save it on clipboard
                boolean success = checkShapeSelection(event.getX(), event.getY());
                if (success) {
                    model.copiedShape = model.currentShape.copyShape();
                    model.shapes.remove(model.currentShape);
                    model.deselectCurrentShape();
                }

            } else if (model.tryingToCopy == true) {
                // make a copy of it
                boolean success = checkShapeSelection(event.getX(), event.getY());
                if (success) {
                    model.copiedShape = model.currentShape.copyShape();
                    model.deselectCurrentShape();
                }

            } else if (model.tryingToPaste == true) {
                // take the copiedShape and place it near the location of the mouse click
                if (model.copiedShape != null) {
                    model.copiedShape.translate(event.getX(), event.getY());
                    model.shapes.add(model.copiedShape.copyShape());
                    redrawCanvas();

                }

            }
            else if (model.tryingToFill == true) {
                boolean success = checkShapeSelection(event.getX(), event.getY());
                if (success) {
                    model.setCurrShapeFill();
                    model.deselectCurrentShape();
                }
            }
            else if (model.actionButton == "" && model.tryingToDrag == true &&
                    model.tryingToSelect == false && model.indexSelectedShape != -1) {
                System.out.println("DESELECTED SELECTED SHAPE");
                boolean actualShape = checkShapeSelection(event.getX(), event.getY());
                if (actualShape == false) {
                    model.tryingToSelect = true;
                    model.deselectCurrentShape();

                }
            }
            else if (model.tryingToSelect == true) {
                boolean successfulSelect = checkShapeSelection(event.getX(), event.getY());
                if (successfulSelect == true) {
                    strokeSelectedShape();
                    model.matchPropertiesToSelectedShape();
                    model.tryingToDrag = true;
                    model.tryingToSelect = false;
                }
            }
            else if (model.actionButton == "draw-rect") {
                rect = new Rectangle();
                rect.setX(event.getX());
                rect.setY(event.getY());
            } else if (model.actionButton == "draw-circle") {
                circle = new Circle();
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            } else if (model.actionButton == "draw-line") {
                line = new Line();
                line.setStartX(event.getX());
                line.setStartY(event.getY());
            }
        });

        canvas.setOnMouseDragged(event -> {
            if (model.actionButton == "" && model.tryingToDrag == false || model.tryingToErase == true ||
            model.tryingToFill == true) {
                return;
            } else if (model.tryingToDrag == true) {
                model.dragCurrentShape(event.getX(), event.getY());
            } else {
                redrawCanvas();
                if (model.actionButton == "draw-rect") {
                    drawRect(event.getX(), event.getY());
                } else if (model.actionButton  == "draw-circle") {
                    drawCircle(event.getX(), event.getY());
                } else if (model.actionButton == "draw-line") {
                    drawLine(event.getX(), event.getY());
                }
            }

        });

        canvas.setOnMouseReleased(event -> {
            System.out.println("mouse drag released");
            if (model.actionButton == "" && model.tryingToDrag == false || model.tryingToErase == true
            || model.tryingToFill == true) {
                return;
            } else if (model.tryingToDrag == true) {
                model.dragCurrentShape(event.getX(), event.getY());
            } else {
                redrawCanvas();
                if (model.actionButton == "draw-rect") {
                    drawRect(event.getX(), event.getY());
                    canvasRectangle r = new canvasRectangle(Math.min(rect.getX(), event.getX()),
                            Math.min(rect.getY(), event.getY()),
                            Math.abs(rect.getX() - event.getX()), Math.abs(rect.getY() - event.getY()),
                            model.currentFill, model.currentLineFill, model.currentLineThickness,
                            model.lineStyleChoice);
                    System.out.println("adding rectangle at index " + model.numShapes);
                    model.shapes.add(r);
                    model.numShapes++;
                    redrawCanvas();
                } else if (model.actionButton == "draw-circle") {
                    drawCircle(event.getX(), event.getY());
                    double radius = (Math.abs(event.getX() - circle.getCenterX()) + Math.abs(event.getY() - circle.getCenterY())) / 2;
                    canvasCircle c = new canvasCircle(Math.min(circle.getCenterX(), event.getX()),
                            Math.min(circle.getCenterY(), event.getY()), radius, model.currentFill, model.currentLineFill,
                            model.currentLineThickness, model.lineStyleChoice);
                    System.out.println("adding circle at index " + model.numShapes);
                    model.shapes.add(c);
                    model.numShapes++;
                    redrawCanvas();
                } else if (model.actionButton == "draw-line") {
                    drawLine(event.getX(), event.getY());
                    gc.closePath();
                    canvasLine l = new canvasLine(line.getStartX(), line.getStartY(), event.getX(), event.getY(), model.currentLineFill,
                            model.currentLineThickness, model.lineStyleChoice);
                    model.shapes.add(l);
                    model.numShapes++;
                    System.out.println("adding line at index " + model.numShapes);
                    redrawCanvas();
                }


            }

        });

        // add label widget to the pane
        this.getChildren().add(canvas);
        //this.getChildren().add(group);

        this.setStyle("-fx-border-color: black; -fx-background-color:white;");
        // register with the model when we're ready to start receiving data
        model.addView(this);

    }
    boolean checkShapeSelection(double x, double y) {
        for (int i = model.shapes.size() - 1; i >= 0; i--) {
            if (model.shapes.get(i).contains(x,y)) {
                System.out.println("shape contains click!!! with index i: " + i );
                model.shapes.get(i).whoAmI();
                model.indexSelectedShape = i;
                if (model.currentShape != null) {
                    model.currentShape.unselectShape(canvas, gc);
                }
                model.currentShape = model.shapes.get(i);

                return true;
            }
        }
        return false;
    }




    void strokeSelectedShape() {
        System.out.println("stroking shape");
        canvasShape curr = model.currentShape;
        curr.selectShape(canvas, gc); // circle, rectangle and line make border thick and black
    }

    void redrawCanvas() {
        System.out.println("REDRAWING CANVAS");
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < model.shapes.size(); i++) {
            model.shapes.get(i).draw(canvas, gc);
        }
        gc.setFill(model.currentFill);
        gc.setLineWidth(model.currentLineThickness);
        gc.setStroke(model.currentLineFill);
        gc.setLineDashes(model.lineStyleChoice);
    }
    void drawLine(double x, double y) {
        gc.strokeLine(line.getStartX(), line.getStartY(), x, y);

    }
    void drawCircle(double x, double y) {
        double radius = (Math.abs(x - circle.getCenterX()) + Math.abs(y - circle.getCenterY())) / 2;
        gc.fillOval(Math.min(circle.getCenterX(), x), Math.min(circle.getCenterY(), y), radius, radius);
        gc.strokeOval(Math.min(circle.getCenterX(), x), Math.min(circle.getCenterY(), y), radius, radius);
    }

    void drawRect(double x, double y) {
        gc.fillRect(Math.min(rect.getX(), x),
                Math.min(rect.getY(), y),
                Math.abs(rect.getX() - x), Math.abs(rect.getY() - y));
        gc.strokeRect(Math.min(rect.getX(), x),
                Math.min(rect.getY(), y),
                Math.abs(rect.getX() - x), Math.abs(rect.getY() - y));
    }

    public void updateView() {
        System.out.println("Canvas View: updateView");
        redrawCanvas();
        if (model.currentShape != null) {
            model.currentShape.selectShape(canvas, gc);
        }

    }
}