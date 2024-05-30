package org.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.client.ClientAppBackend;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TableView humanTableView;
    public Label currentUserLabel;
    public ChoiceBox langChoiceBox;
    public TableColumn zonedDTColumn;
    public TableColumn coordsColumn;
    public TableColumn nameColumn;
    public TableColumn idColumn;
    public TableColumn hasToothpickColumn;
    public TableColumn realHeroColumn;
    public TableColumn impactSpeedColumn;
    public TableColumn minutesOfWaitingColumn;
    public TableColumn weaponTypeColumn;
    public TableColumn moodColumn;
    public TableColumn carModelColumn;
    public Canvas visualizationCanvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addObject(ActionEvent actionEvent) {
    }

    public void editObject(ActionEvent actionEvent) {
    }

    public void removeObject(ActionEvent actionEvent) {
    }
}
