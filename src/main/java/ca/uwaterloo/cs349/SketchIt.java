package ca.uwaterloo.cs349;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javafx.scene.paint.Color;
import javafx.scene.text.*;
// HelloMVC
// A simple MVC example inspired by Joseph Mack, http://www.austintek.com/mvc/
// This version uses MVC: two views coordinated with the observer pattern, but no separate controller.

public class SketchIt extends Application {

	static double WINDOW_WIDTH = 1600;
	static double WINDOW_HEIGHT = 1200;

	@Override
	public void start(Stage stage) throws Exception {
		// create and initialize the Model to hold our counter
		Model model = new Model();

		// create each view, and tell them about the model
		// the views will register themselves with the model
		toolsView toolsView = new toolsView(model);
		canvasView canvasView = new canvasView(model);
		fileMenuView fileMenuView = new fileMenuView(model);
		HBox title = new HBox();
		Text t = new Text("Sketch It");
		t.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size:18;");
		title.getChildren().add(t);
		GridPane grid = new GridPane();
		grid.add(fileMenuView, 0,0);
		grid.add(title, 1,0);
		grid.add(toolsView, 0, 1);      // top-view
		grid.add(canvasView, 1, 1);      // bottom-view
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(25);

		// Add grid to a scene (and the scene to the stage)
		Scene scene = new Scene(grid, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				switch (event.getCode()) {
					case ESCAPE:
						if (model.indexSelectedShape != -1) {
							model.tryingToSelect = true;
							model.deselectCurrentShape();
						}
						break;
					case DELETE:
						if (model.indexSelectedShape != -1) {
							model.shapes.remove(model.currentShape);
							model.deselectCurrentShape();
						}
						break;
				}
			}
		});

		stage.setScene(scene);
		stage.setMinWidth(640);
		stage.setMinHeight(480);
		stage.setMaxHeight(1440);
		stage.setMaxWidth(1920);
		stage.setResizable(true);
		stage.show();
	}
}
