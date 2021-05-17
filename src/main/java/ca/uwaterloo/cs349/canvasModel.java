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
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;

import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import javafx.scene.input.MouseEvent;

public class canvasModel {

    private int numShapes = 0;
    ArrayList<canvasShape> shapes = new ArrayList<canvasShape>();
    private Canvas drawingCanvas;

    // all views of this model
    private ArrayList<IView> views = new ArrayList<IView>();


    // method that the views can use to register themselves with the Model
    // once added, they are told to update and get state from the Model
    public void addView(IView view) {
        views.add(view);
        view.updateView();
    }



    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private void notifyObservers() {
        for (IView view : this.views) {
            System.out.println("Model: notify View");
            view.updateView();
        }
    }
}
