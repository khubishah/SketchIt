package ca.uwaterloo.cs349;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.geometry.Insets;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

class toolsView extends VBox implements IView {

    Image circleImg = new Image("circle.jpg");
    ImageView circleView = new ImageView(circleImg);
    Image rectImg = new Image("rectangle.jpg");
    ImageView rectView = new ImageView(rectImg);
    Image eraseImg = new Image("erase.jpg");
    ImageView eraseView = new ImageView(eraseImg);
    Image fillImg = new Image("fill.jpg");
    ImageView fillView = new ImageView(fillImg);
    Image lineImg = new Image("line.jpg");
    ImageView lineView = new ImageView(lineImg);
    Image selectImg = new Image("selectIcon.jpg");
    ImageView selectView = new ImageView(selectImg);
    Image dashedNone = new Image("dashedNone.jpg");
    Image dashedWide = new Image("dashedWide.jpg");
    Image dashedTight = new Image("dashedTight.jpg");

    Image copyImg = new Image("copy.jpg");
    ImageView copyView = new ImageView(copyImg);
    Image cutImg = new Image("cut.jpg");
    ImageView cutView = new ImageView(cutImg);
    Image pasteImg = new Image("paste.jpg");
    ImageView pasteView = new ImageView(pasteImg);
    Image thick1Img = new Image("thick1.jpg");
    Image thick3Img = new Image("thick3.jpg");
    Image thick5Img = new Image("thick5.jpg");
    Image thick7Img = new Image("thick7.jpg");
    Image thick9Img = new Image("thick9.jpg");
    ImageView thick1View = new ImageView(thick1Img);
    ImageView thick3View = new ImageView(thick3Img);
    ImageView thick5View = new ImageView(thick5Img);
    ImageView thick7View = new ImageView(thick7Img);
    ImageView thick9View = new ImageView(thick9Img);

    ImageView solidView = new ImageView(dashedNone);
    ImageView dashTightView = new ImageView(dashedTight);
    ImageView dashWideView = new ImageView(dashedWide);
    private Model model;
    Button selectionBtn;
    Button eraseBtn;
    Button lineBtn;
    Button circleBtn;
    Button rectBtn;
    Button fillBtn;
    Button cutBtn;
    Button copyBtn;
    Button pasteBtn;
    Text fillColorLabel;
    Text lineColorLabel;
    ColorPicker fillColorPicker;
    ColorPicker lineColorPicker;
    ComboBox lineThickness;
    ComboBox lineStyle;
    Text lineStyleLabel;
    ToggleButton solid;
    ToggleButton dashedwide;
    ToggleButton dashedtight;
    ToggleGroup styleGroup;
    ToggleButton thick1;
    ToggleButton thick3;
    ToggleButton thick5;
    ToggleButton thick7;
    ToggleButton thick9;
    ToggleGroup thicknessGroup;
    Text lineThicknessLabel;
    HashMap<double[], String> mapLineStyle = new HashMap<double[], String>();

    // line thickness, line style add
    Button styleButton(String label) {
        Button b = new Button();
        b.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size:12;");
        b.setPrefSize(20,20);


        return b;
    }
    Text createText(String content) {
        Text t = new Text(content);
        t.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size:12;");
        return t;
    }
    String thickness[] = {"1.0","3.0","5.0","7.0","9.0"};
    String lineStyleOptions[] = {"Solid", "Dashed Wide", "Dashed Tight"};

    public void setWidthHeights() {
        thick1View.setFitHeight(20);
        thick1View.setFitWidth(20);
        thick3View.setFitHeight(20);
        thick3View.setFitWidth(20);
        thick5View.setFitHeight(20);
        thick5View.setFitWidth(20);
        thick7View.setFitHeight(20);
        thick7View.setFitWidth(20);
        thick9View.setFitHeight(20);
        thick9View.setFitWidth(20);
        solidView.setFitHeight(20);
        solidView.setFitWidth(20);
        dashWideView.setFitHeight(20);
        dashWideView.setFitWidth(20);
        dashTightView.setFitHeight(20);
        dashTightView.setFitWidth(20);
        circleView.setFitHeight(20);
        circleView.setFitWidth(20);
        rectView.setFitHeight(20);
        rectView.setFitWidth(20);
        eraseView.setFitHeight(20);
        eraseView.setFitWidth(20);
        fillView.setFitHeight(20);
        fillView.setFitWidth(20);
        lineView.setFitHeight(20);
        lineView.setFitWidth(20);
        selectView.setFitHeight(20);
        selectView.setFitWidth(20);
        copyView.setFitHeight(20);
        copyView.setFitWidth(20);
        cutView.setFitHeight(20);
        cutView.setFitWidth(20);
        pasteView.setFitHeight(20);
        pasteView.setFitWidth(20);
    }

    toolsView(Model model) {
        setWidthHeights();
        styleGroup = new ToggleGroup();
        solid = new ToggleButton("", solidView);
        dashedwide = new ToggleButton("", dashWideView);
        dashedtight = new ToggleButton("", dashTightView);
        solid.setToggleGroup(styleGroup);
        dashedwide.setToggleGroup(styleGroup);
        dashedtight.setToggleGroup(styleGroup);
        solid.setUserData("Solid");
        dashedwide.setUserData("Dashed Wide");
        dashedtight.setUserData("Dashed Tight");
        solid.setSelected(true);
        dashedwide.setSelected(false);
        dashedtight.setSelected(false);
        styleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                if (new_toggle == null) {
                    model.setLineStyleChoice("Solid");
                }
                else {
                    model.setLineStyleChoice((String) styleGroup.getSelectedToggle().getUserData());
                }
                if (model.indexSelectedShape != -1) {
                    model.setCurrentLineStyle();
                }

            }
        });
        thicknessGroup = new ToggleGroup();
        thick1 = new ToggleButton("", thick1View);
        thick3 = new ToggleButton("", thick3View);
        thick3.setSelected(true);
        thick5 = new ToggleButton("", thick5View);
        thick7 = new ToggleButton("", thick7View);
        thick9 = new ToggleButton("", thick9View);
        thick1.setUserData("1.0");
        thick3.setUserData("3.0");
        thick5.setUserData("5.0");
        thick7.setUserData("7.0");
        thick9.setUserData("9.0");
        thick1.setToggleGroup(thicknessGroup);
        thick3.setToggleGroup(thicknessGroup);
        thick5.setToggleGroup(thicknessGroup);
        thick7.setToggleGroup(thicknessGroup);
        thick9.setToggleGroup(thicknessGroup);
        HBox thickBox = new HBox();
        thickBox.setAlignment(Pos.CENTER);
        thickBox.setSpacing(5);
        thickBox.getChildren().add(thick1);
        thickBox.getChildren().add(thick3);
        thickBox.getChildren().add(thick5);
        thickBox.getChildren().add(thick7);
        thickBox.getChildren().add(thick9);
        thicknessGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                if (new_toggle == null) {
                    model.currentLineThickness = Double.parseDouble("3.0");
                }
                else {
                    model.currentLineThickness = Double.parseDouble((String) thicknessGroup.getSelectedToggle().getUserData());

                }
                if (model.indexSelectedShape != -1) {
                    System.out.println("need to update selected shape's LINE color");
                    model.setCurrentLineThickness();
                } else {
                    System.out.println("NO SELECTED SHAPE");
                }

            }
        });






        // keep track of the model
        this.model = model;
        // initialize view stuff - buttons labels
       // lineStyle = new ComboBox(FXCollections.observableArrayList(lineStyleOptions));
       // lineStyle.setValue("Solid");
       // lineStyle.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size:12;");
        lineThickness = new ComboBox(FXCollections.observableArrayList(thickness));
        lineThickness.setValue("3.0");
        lineThickness.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size:12;");
        lineBtn = styleButton("");
        lineBtn.setGraphic(lineView);
        circleBtn = styleButton("");
        circleBtn.setGraphic(circleView);
        rectBtn = styleButton("");
        rectBtn.setGraphic(rectView);
        cutBtn = styleButton("");
        cutBtn.setGraphic(cutView);
        copyBtn = styleButton("");
        copyBtn.setGraphic(copyView);
        pasteBtn = styleButton("");
        pasteBtn.setGraphic(pasteView);


        fillBtn = styleButton("");
        fillBtn.setGraphic(fillView);
        selectionBtn = styleButton("");
        selectionBtn.setGraphic(selectView);
        eraseBtn = styleButton("");
        eraseBtn.setGraphic(eraseView);
        fillColorPicker = new ColorPicker();
        fillColorPicker.setValue(Color.BLACK);
        this.model.currentFill = fillColorPicker.getValue();
        fillColorPicker.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size:12;");
        lineColorPicker = new ColorPicker();
        lineColorPicker.setValue(Color.BLACK);
        this.model.currentLineFill = lineColorPicker.getValue();
        lineColorPicker.setStyle("-fx-font-family: \"Verdana\";-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size:12;");
        fillColorLabel = createText("Fill Color:");
        lineColorLabel = createText("Line Color:");
        lineThicknessLabel = createText("Line Thickness: ");
        lineStyleLabel = createText("Line Style: ");
        pasteBtn.setDisable(true);

        copyBtn.setOnAction(event -> {
            model.tryingToErase = false;
            model.actionButton = "";
            model.tryingToSelect = false;
            model.tryingToFill = false;
            model.tryingToDrag = false;
            model.tryingToCopy = true;

            model.tryingToCut = false;
            toggleProperties(true);
            model.tryingToPaste = false;
        });
        cutBtn.setOnAction(event -> {
            model.tryingToErase = false;
            model.actionButton = "";
            model.tryingToSelect = false;
            model.tryingToFill = false;
            model.tryingToDrag = false;
            model.tryingToCopy = false;

            model.tryingToCut = true;
            toggleProperties(true);
            model.tryingToPaste = false;
        });
        pasteBtn.setOnAction(event -> {
            model.tryingToErase = false;
            model.actionButton = "";
            model.tryingToSelect = false;
            model.tryingToFill = false;
            model.tryingToDrag = false;
            toggleProperties(true);
            model.tryingToCopy = false;
            model.tryingToCut = false;
            model.tryingToPaste = true;
        });


        eraseBtn.setOnMouseClicked(event -> {
            model.tryingToErase = true;
            model.actionButton = "";
            model.tryingToSelect = false;
            model.tryingToFill = false;
            toggleProperties(true);
            model.tryingToDrag = false;
            model.tryingToCopy = false;

            model.tryingToCut = false;
            model.tryingToPaste = false;
            // deactivate drawing button until user is no longer trying to select
        });
        selectionBtn.setOnMouseClicked(event -> {
            model.tryingToSelect = true;
            model.actionButton = "";
            model.tryingToErase = false;
            model.tryingToFill = false;
            fillColorPicker.setDisable(false);
            model.tryingToCopy = false;
            model.tryingToCut = false;
            model.tryingToPaste = false;
            toggleProperties(true);
            // deactivate drawing button until user is no longer trying to select
        });
        fillBtn.setOnMouseClicked(event -> {
            model.tryingToFill = true;
            model.tryingToSelect = false;
            model.actionButton = "";
            model.tryingToErase = false;
            model.tryingToDrag = false;
            model.tryingToCopy = false;
            model.tryingToCut = false;
            model.tryingToPaste = false;
            toggleProperties(true);
            fillColorPicker.setDisable(false);
            // deactivate drawing button until user is no longer trying to select
        });
        rectBtn.setOnMouseClicked(event -> {
            System.out.println("Controller: set button selected");
            model.tryingToSelect = false;
            model.actionButton = "draw-rect";
            model.tryingToFill = false;
            model.tryingToErase = false;
            model.tryingToDrag = false;
            model.tryingToCopy = false;
            fillColorPicker.setDisable(false);
            model.tryingToCut = false;
            model.tryingToPaste = false;
            toggleProperties(false);

        });
        circleBtn.setOnMouseClicked(event -> {
            System.out.println("Controller: set button selected");
            model.tryingToSelect = false;
            model.actionButton = "draw-circle";
            model.tryingToFill = false;
            model.tryingToErase = false;
            model.tryingToDrag = false;
            model.tryingToCopy = false;
            model.tryingToCut = false;
            fillColorPicker.setDisable(false);
            toggleProperties(false);
            model.tryingToPaste = false;
        });
        lineBtn.setOnMouseClicked(event -> {
            System.out.println("Controller: set button selected");
            model.tryingToSelect = false;
            model.actionButton = "draw-line";
            model.tryingToFill = false;
            model.tryingToDrag = false;
            model.tryingToErase = false;
            model.tryingToCopy = false;
            fillColorPicker.setDisable(true);
            model.tryingToCut = false;
            toggleProperties(false);
            model.tryingToPaste = false;
        });
        lineColorPicker.setOnAction(event -> {
            model.currentLineFill = lineColorPicker.getValue();
            if (model.indexSelectedShape != -1) {
                System.out.println("need to update selected shape's LINE color");
                model.setCurrShapeStroke();
            } else {
                System.out.println("NO SELECTED SHAPE");
            }
        });
        fillColorPicker.setOnAction(event -> {
            System.out.println("new color picked");
            model.currentFill = fillColorPicker.getValue();
            if (model.indexSelectedShape != -1) {
                System.out.println("need to update selected shape's color");
                model.setCurrShapeFill();
            } else {
                System.out.println("NO SELECTED SHAPE");
            }
        });
        HBox styleBox = new HBox();
        styleBox.getChildren().add(solid);
        styleBox.getChildren().add(dashedwide);
        styleBox.getChildren().add(dashedtight);
        styleBox.setSpacing(10);
        styleBox.setAlignment(Pos.CENTER);

        this.getChildren().add(cutBtn);
        this.getChildren().add(copyBtn);
        this.getChildren().add(pasteBtn);
        this.getChildren().add(selectionBtn);
        this.getChildren().add(eraseBtn);
        this.getChildren().add(lineBtn);
        this.getChildren().add(circleBtn);
        this.getChildren().add(rectBtn);
        this.getChildren().add(fillBtn);
        this.getChildren().add(fillColorLabel);
        this.getChildren().add(fillColorPicker);
        this.getChildren().add(lineColorLabel);
        this.getChildren().add(lineColorPicker);
        this.getChildren().add(lineThicknessLabel);
        this.getChildren().add(thickBox);
        this.getChildren().add(lineStyleLabel);
        this.getChildren().add(styleBox);



        this.setAlignment(Pos.CENTER);

        // register with the model when we're ready to start receiving data
        model.addView(this);
    }

    // When notified by the model that things have changed,
    // update to display the new value
    public void updateView() {
        System.out.println("Tools View: updateView");
        if (this.model.currentLineFill != lineColorPicker.getValue()) {
            lineColorPicker.setValue(this.model.currentLineFill);
        }
        if (this.model.currentFill != fillColorPicker.getValue()) {
            fillColorPicker.setValue(this.model.currentFill);
        }
        if (this.model.currentLineThickness != Double.parseDouble(thicknessGroup.getSelectedToggle().getUserData() + "")) {
            System.out.println("Update thickness");
            double val = this.model.currentLineThickness;
            if (val == 1.0) {
                thick1.setSelected(true);
            } else if (val == 3.0) {
                thick3.setSelected(true);
            } else if (val == 5.0) {
                thick5.setSelected(true);
            } else if (val == 7.0) {
                thick7.setSelected(true);
            } else {
                thick9.setSelected(true);
            }
        }
       if (this.model.lineStyleChoice[0] == 1) {
           solid.setSelected(true);
        } else if (this.model.lineStyleChoice[0] == 15) {
           dashedtight.setSelected(true);
        } else if (this.model.lineStyleChoice[0] == 30) {
           dashedwide.setSelected(true);
        }

       //
        if (model.copiedShape != null) {
            pasteBtn.setDisable(false);
        } else {
            pasteBtn.setDisable(true);
        }

        if (model.currentShape != null) {
            toggleProperties(false);

        }
        if (model.tryingToSelect == true && model.currentShape == null ) {
            toggleProperties(true);
        }
        if ( model.tryingToFill == true) {
            toggleProperties(true);
            fillColorPicker.setDisable(false);
        }

    }

    public void toggleProperties(boolean b) {
        lineColorPicker.setDisable(b);
        fillColorPicker.setDisable(b);
        thicknessGroup.getToggles().forEach(toggle -> {
            Node node = (Node) toggle;
            node.setDisable(b);
        });
        styleGroup.getToggles().forEach(toggle -> {
            Node node = (Node) toggle;
            node.setDisable(b);
        });

    }
    }