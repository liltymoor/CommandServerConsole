package org.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.client.ClientAppBackend;
import org.gui.controllers.AuthorizationController;
import org.gui.controllers.BackendControllerFactory;
import org.gui.controllers.MainController;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp extends Application {
    private static final Logger appLogger = Logger.getLogger("ClientApp");

    private  static final ClientAppBackend appBackend = new ClientAppBackend();
    private static final MainController mainController = new MainController(appBackend);
    private static final BackendControllerFactory controllerFactory = new BackendControllerFactory(mainController, appBackend);

    public static void main(String[] args) {
        appLogger.log(Level.INFO, "Starting ClientApp...");
        Application.launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        appLogger.log(Level.INFO, "Starting Root Stage");

        // main
        Stage mainStage = new Stage();

        // auth
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/client/fxml/auth.fxml"));
        loader.setResources(ResourceBundle.getBundle("org/client/messages", Locale.getDefault()));
        loader.setController(new AuthorizationController(appBackend, mainStage, Locale.getDefault(), controllerFactory, mainController));
        Stage root = loader.load();
        root.setResizable(false);

        root.show();
    }
}
