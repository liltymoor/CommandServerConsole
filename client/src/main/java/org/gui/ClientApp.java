package org.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.client.ClientAppBackend;
import org.gui.controllers.AuthorizationController;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;
import org.gui.controllers.MainController;


public class ClientApp extends Application {
    private static final Logger appLogger = Logger.getLogger("ClientApp");
    private  static final ClientAppBackend appBackend = new ClientAppBackend();
    public static void main(String[] args) {
        appLogger.log(Level.INFO, "Starting ClientApp...");
        Application.launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        appLogger.log(Level.INFO, "Starting Root Stage");

        // main
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/org/client/fxml/main.fxml"));
        mainLoader.setResources(ResourceBundle.getBundle("org/client/messages", Locale.getDefault()));
        mainLoader.setController(new MainController());
        SplitPane mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot);
        Stage mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);

        // auth
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/client/fxml/auth.fxml"));
        loader.setResources(ResourceBundle.getBundle("org/client/messages", Locale.getDefault()));
        loader.setController(new AuthorizationController(appBackend, mainStage, Locale.getDefault()));
        Stage root = loader.load();
        root.setResizable(false);

        root.show();
    }
}
