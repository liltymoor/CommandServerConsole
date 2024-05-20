package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.client.ClientAppBackend;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class AuthorizationController {
    @FXML
    private AnchorPane pane;
    @FXML
    private Label registerLabel;
    @FXML
    private Label authLabel;
    @FXML
    private Label mainLabel;
    @FXML
    private Button authButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField registerUsernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField registerPasswordField1;
    @FXML
    private PasswordField registerPasswordField2;
    @FXML
    private Button loginButton;
    @FXML
    private VBox authVBox;
    @FXML
    private VBox regVBox;
    @FXML
    private Label errorLabel;

    private final Stage mainStage;
    private final ClientAppBackend backend;
    private final ResourceBundle messages;
    private final ResourceBundle errors;

    public AuthorizationController(ClientAppBackend backend, Stage mainStage, Locale locale) {
        this.backend = backend;
        this.mainStage = mainStage;
        this.errors = ResourceBundle.getBundle("org/client/errors", Locale.getDefault());
        System.out.println(errors.getBaseBundleName());
        System.out.println(errors.getLocale());
        System.out.println(errors.keySet());
        this.messages = ResourceBundle.getBundle("org/client/messages", Locale.getDefault());
    }

    @FXML
    public void underlineRegisterLabel(){
        registerLabel.setStyle("-fx-underline: true");
    }

    @FXML
    public void undoUnderlineRegisterLabel(){
        registerLabel.setStyle("-fx-underline: false");
    }

    @FXML
    public void undoUnderlineAuthLabel(){
        authLabel.setStyle("-fx-underline: false");
    }
    @FXML
    public void underlineAuthLabel(){
        authLabel.setStyle("-fx-underline: true");
    }

    @FXML
    public void showRegisterMenu(){
        regVBox.setVisible(true);
        authVBox.setVisible(false);
    }

    @FXML
    public void showAuthMenu(){
        authVBox.setVisible(true);
        regVBox.setVisible(false);
    }

    @FXML
    public void register(){
        errorLabel.setText("");
        if (registerUsernameField.getText().isEmpty() || registerPasswordField1.getText().isEmpty() || registerPasswordField2.getText().isEmpty()) {
            errorLabel.setText(errors.getString("empty_field_error"));
            return;
        }
        if (!registerPasswordField1.getText().equals(registerPasswordField2.getText())) {
            errorLabel.setText(errors.getString("password_mismatch_error"));
            return;
        }

        // backend

        Stage stage = (Stage) authVBox.getScene().getWindow();
        stage.close();
        mainStage.show();
    }

    @FXML
    public void auth(){
        errorLabel.setText("");
        if (registerUsernameField.getText().isEmpty() || registerPasswordField1.getText().isEmpty() || registerPasswordField2.getText().isEmpty()) {
            errorLabel.setText(errors.getString("empty_field_error"));
            return;
        }
        // backend
        // + обработать ошибки от сервера
        // ... errorLabel.setText(errors.getString("auth_error"));

        Stage stage = (Stage) authVBox.getScene().getWindow();
        stage.close();
        mainStage.show();
    }
}
