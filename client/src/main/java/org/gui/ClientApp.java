package org.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.client.ClientAppBackend;
import org.gui.controllers.AuthorizationController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp extends Application {
    private static Logger appLogger = Logger.getLogger("ClientApp");
    private  static ClientAppBackend appBackend = new ClientAppBackend();
    public static void main(String[] args) {
        appLogger.log(Level.INFO, "Starting ClientApp...");
        Application.launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        appLogger.log(Level.INFO, "Starting Root Stage");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/client/authorization.fxml"));
        loader.setController(new AuthorizationController(appBackend));
        AnchorPane root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Authorization");

        stage.show();
    }
}
