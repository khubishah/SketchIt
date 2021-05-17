package ca.uwaterloo.cs349;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.TextArea;
import java.util.Optional;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.Popup;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;
import java.io.File;


class fileMenuView extends VBox implements IView {


    private Model model;

    MenuBar menubar;
    Menu fileMenu;
    FileChooser fileChooser = new FileChooser();;
    Menu helpMenu;
    MenuItem quit;
    MenuItem newFile;
    MenuItem save;
    MenuItem load;
    MenuItem about;
    Dialog<String> dialog = new Dialog<String>();
    Alert askSave;


    fileMenuView(Model model) {
        // keep track of the model
        this.model = model;
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
        menubar = new MenuBar();
        //menubar.setStyle("fx-font: 18pt Verdana;");
        fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        newFile = new MenuItem("New");
        newFile.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        save = new MenuItem("Save");
        save.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        load = new MenuItem("Load");
        load.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        quit = new MenuItem("Quit");
        quit.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        helpMenu = new Menu("Help");
        helpMenu.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        about = new MenuItem("About");
        about.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        fileMenu.getItems().addAll(newFile, save, load, quit);
        helpMenu.getItems().add(about);
        menubar.getMenus().addAll(fileMenu, helpMenu);
        dialog.setTitle("About");
        ButtonType doneOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText("SketchIt built by Khubi U. Shah, 3A Computer Science, ID: 20764562");
        dialog.getDialogPane().getButtonTypes().add(doneOk);
        //dialog.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        askSave = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save changes?",
                ButtonType.YES, ButtonType.NO);
        askSave.setTitle("Save Drawing");

        dialog.setHeight(300);
        dialog.setWidth(500);
        askSave.setHeight(300);
        askSave.setWidth(500);
        about.setOnAction(e -> {
            dialog.showAndWait();
        });

        quit.setOnAction(e -> {
            // ask them if they want to save.
            // if yes, save logic
            // quit logic
            askSave.showAndWait().ifPresent(type -> {
                System.out.println("alert");
                if (type.equals(ButtonType.YES)) {
                    System.out.println("saving file");
                    saveFile();
                }
            });
            model.quitApplication();
        });

        newFile.setOnAction(e -> {
            // show a display dialog box with your program name, your name, and WatID
            askSave.showAndWait().ifPresent(type -> {
                System.out.println("alert");
                if (type.equals(ButtonType.YES)) {
                    System.out.println("saving file");
                    saveFile();
                }
            });
            System.out.println("out of alert");
            model.clearCanvas();
        });

        save.setOnAction(e -> {
            saveFile();
            // show a display dialog box with your program name, your name, and WatID
        });

        load.setOnAction(e -> {
            askSave.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    saveFile();
                }
            });
            loadFile();
            // show a display dialog box with your program name, your name, and WatID
        });



        this.getChildren().add(menubar);

        this.setAlignment(Pos.CENTER);

        // register with the model when we're ready to start receiving data
        model.addView(this);
    }

    public void loadFile() {

        fileChooser.setTitle("Select a Text File");
        File file = fileChooser.showOpenDialog(this.getScene().getWindow());

        if (file != null) {
            try {
                model.loadCanvasToFile(file);
            } catch (Exception e) {
                System.out.println("ERROR");
            }

        }

    }

    public void saveFile() {
        fileChooser.setTitle("Save a text file");
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        // make sure it's a text file
        if (file != null) {
            try {
                model.saveCanvasToFile(file);
            } catch (Exception e) {
                System.out.println("ERROR: No FILE SELECTED");
            }

        }

    }

    // When notified by the model that things have changed,
    // update to display the new value
    public void updateView() {

    }
}