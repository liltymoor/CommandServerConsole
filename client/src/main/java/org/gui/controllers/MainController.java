package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.client.ClientAppBackend;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableTabController tableController;

    @FXML
    private RadarTabController radarController;

    private ClientAppBackend appBackend;

    public MainController(ClientAppBackend appBackend) {
        this.appBackend = appBackend;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableController.setAppBackend(appBackend);
        radarController.setAppBackend(appBackend);
    }
}
