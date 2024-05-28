package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import org.client.ClientAppBackend;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private TableTabController tableController;

    private RadarTabController radarController;

    @FXML
    private Tab radarTab;

    @FXML
    private Tab tableTab;

    private ClientAppBackend appBackend;

    public MainController(ClientAppBackend appBackend) {
        this.appBackend = appBackend;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
