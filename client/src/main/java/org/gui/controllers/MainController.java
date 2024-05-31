package org.gui.controllers;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.util.Duration;
import org.client.ClientAppBackend;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private TableTabController tableController;

    private RadarTabController radarController;

    @FXML
    private Tab radarTab;

    @FXML
    private Tab tableTab;

    private ClientAppBackend appBackend;
    private SyncService syncService;

    protected class SyncService extends ScheduledService<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() {
                    syncCollection();
                    return null;
                }
            };
        }

        protected void syncCollection() {
            appBackend.syncCollection();
        }
    }

    public MainController(ClientAppBackend appBackend) {
        this.appBackend = appBackend;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.appBackend.syncCollection();
        syncService = new SyncService();
        syncService.setPeriod(Duration.seconds(5));
        syncService.start();
    }
}
