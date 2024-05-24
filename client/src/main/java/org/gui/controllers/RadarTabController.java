package org.gui.controllers;

import javafx.fxml.Initializable;
import org.client.ClientAppBackend;

import java.net.URL;
import java.util.ResourceBundle;

public class RadarTabController {

    private ClientAppBackend appBackend;

    public void setAppBackend(ClientAppBackend appBackend) {
        this.appBackend = appBackend;
    }

}
