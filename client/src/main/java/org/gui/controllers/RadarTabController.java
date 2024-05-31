package org.gui.controllers;

import org.client.ClientAppBackend;


public class RadarTabController {
    public RadarTabController(MainController parent, ClientAppBackend appBackend) {
        this.appBackend = appBackend;
        parentController = parent;
    }

    private final MainController parentController;
    private ClientAppBackend appBackend;
}
