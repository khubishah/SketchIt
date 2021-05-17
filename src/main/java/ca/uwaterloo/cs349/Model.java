package ca.uwaterloo.cs349;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape3D;
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
import javafx.stage.Stage;
import java.awt.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.io.*;

public class Model {

	int numShapes = 0;

	ArrayList<canvasShape> shapes = new ArrayList<canvasShape>();
	canvasShape currentShape = null;
	canvasShape copiedShape = null;
	int indexSelectedShape = -1;
	boolean tryingToSelect = false;
	boolean tryingToErase = false;
	boolean tryingToDrag = false;
	boolean tryingToFill = false;
	boolean tryingToCopy = false;
	boolean tryingToCut = false;
	boolean tryingToPaste = false;
	double [] lineStyleChoice = {1};
	double[] solidLine = {1};
	double[] dashWide = {30};
	double[] dashTight = {15};
	String actionButton; // which action from the tool bar has the user chosen. "" is none. make sure to set to empty string after action is done
	Color currentLineFill;
	Color currentFill;
	double currentLineThickness = 3;
	GraphicsContext gc;
	Canvas canvas;
	//boolean eraseMode = false;


	// all views of this model
	private ArrayList<IView> views = new ArrayList<IView>();



	public void setLineStyleChoice(String choice) {

		if (choice.equals("Solid")) {
			System.out.println("Solid");
			lineStyleChoice = solidLine;
		} else if (choice.equals("Dashed Wide")) {
			System.out.println("Dashed wide");
			lineStyleChoice = dashWide;
		} else {
			System.out.println("Dashed Tight");
			lineStyleChoice = dashTight;
		}

	}
	// method that the views can use to register themselves with the Model
	// once added, they are told to update and get state from the Model
	public void clearCanvas() {
		shapes.clear();
		indexSelectedShape = -1;
		currentShape = null;
		numShapes = 0;
		notifyObservers();

	}
	public void quitApplication() {
		Platform.exit();
	}


	public void saveCanvasToFile(File filename) {
		// ask user for new filename
		// write shapeType, locations, colors for each shape to file
		FileWriter file = null;
		BufferedWriter writer = null;

		try {
			file = new FileWriter(filename);
			writer = new BufferedWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			// one line is written for each row of data
			for (int i = shapes.size() - 1; i >= 0; i--) {
				writer.write(shapes.get(i).writeInfoToFile());
			}


			writer.close();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
	}

	public void loadCanvasToFile(File filename) {
		FileReader file = null;
		BufferedReader reader = null;
		String[] values;
		clearCanvas();


		// open input file
		try {
			file = new FileReader(filename);
			reader = new BufferedReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// read and process lines one at a time
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				// DELIMITER separates values on a row
				values = line.split(" ");
				System.out.println(values[0]);
				System.out.println(values[1]);
				System.out.println(values[2]);
				System.out.println(values[3]);
				System.out.println(values[4]);

				// do something with values here e.g. print them

				if (values[0].equals("Rectangle")) {
					System.out.println("RECT CREATED");
					double [] dashType = {Double.parseDouble(values[8])};
					canvasRectangle r = new canvasRectangle(Double.parseDouble(values[1])  , Double.parseDouble(values[2]) ,
							Double.parseDouble(values[3]) , Double.parseDouble(values[4]) , Color.valueOf(values[5]),
							Color.valueOf(values[6]), Double.parseDouble(values[7]), dashType);
					shapes.add(r);
					numShapes++;
				} else if (values[0].equals("Circle")) {
					System.out.println("Circle CREATED");
					double [] dashType = {Double.parseDouble(values[7])};
					canvasCircle c = new canvasCircle(Double.parseDouble(values[1])  , Double.parseDouble(values[2]) ,
							Double.parseDouble(values[3]), Color.valueOf(values[4]), Color.valueOf(values[5]),
							Double.parseDouble(values[6]), dashType);
					shapes.add(c);
					numShapes++;
				} else if (values[0].equals("Line")) {
					System.out.println("Line CREATED");
					double [] dashType = {Double.parseDouble(values[7])};
					canvasLine l = new canvasLine(Double.parseDouble(values[1])  , Double.parseDouble(values[2]) ,
							Double.parseDouble(values[3]) , Double.parseDouble(values[4]) , Color.valueOf(values[5]),
							Double.parseDouble(values[6]), dashType);
					shapes.add(l);
					numShapes++;
				}


			}
		} catch (IOException e) {
			e.printStackTrace();
		}


		notifyObservers();

	}


	public void dragCurrentShape(double x, double y) {
		currentShape.translate(x,y);
		notifyObservers();
	}
	public void addView(IView view) {
		views.add(view);
		view.updateView();
	}

	public void setCurrShapeStroke() {
		this.currentShape.setLineColor(currentLineFill);
		System.out.println("setCurrShapeStroke");
		notifyObservers();
	}
	public void setCurrentLineStyle() {
		this.currentShape.setLineStyle(lineStyleChoice);
		notifyObservers();
	}
	public void setCurrShapeFill() {
		System.out.println("setCurrShapeFill");
		this.currentShape.whoAmI();
		this.currentShape.setFillColor(currentFill);
		notifyObservers();
	}
	public void setCurrentLineThickness() {
		System.out.println("setCurrShapeFill");
		this.currentShape.whoAmI();
		this.currentShape.setThickness(currentLineThickness);
		notifyObservers();
	}

	public void matchPropertiesToSelectedShape() {
		System.out.println("Matching properties to shape selected");
		// update color pick value for line, fill, border thickness, border
		currentLineFill = currentShape.getLineColor();
		if (currentShape.getFillColor() != null) {
			currentFill = currentShape.getFillColor();
		}
		currentLineThickness = currentShape.getThickness();
		lineStyleChoice = currentShape.getLineStyle();
		System.out.println("Selected shape's line style: " + lineStyleChoice);
		notifyObservers();
	}

	public void deselectCurrentShape() {
		System.out.println("Deselecting current shape");
		indexSelectedShape = -1;
		currentShape = null;
		tryingToDrag = false;
		notifyObservers();
	}



	// simple accessor method to fetch the current state
	//public int getCounterValue() {
	//	return counter;
	//}

	// method that the Controller uses to tell the Model to change state
	// in a larger application there would probably be multiple entry points like this
	//public void incrementCounter() {
		//counter++;
		//System.out.println("Model: increment counter to " + counter);
		//notifyObservers();
	//}
	
	// the model uses this method to notify all of the Views that the data has changed
	// the expectation is that the Views will refresh themselves to display new data when appropriate
	private void notifyObservers() {
		for (IView view : this.views) {
			view.updateView();
		}
	}
}
