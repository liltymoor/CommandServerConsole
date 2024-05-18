package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.client.ClientAppBackend;
import org.shared.network.User;

public class AuthorizationController {
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

    public AuthorizationController(ClientAppBackend backend, Stage mainStage) {
        this.backend = backend;
        this.mainStage = mainStage;
    }

//    @FXML
//    private void handleLogin() {
//        String username = usernameField.getText();
//        if (username.isEmpty()) return;
//        String password = passwordField.getText();
//        if (password.isEmpty()) return;
//
//        User userInfo = new User(username, password);
//        backend.invokeCommand("auth", userInfo);
//
//    }

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
        mainLabel.setText("Регистрация");
    }

    @FXML
    public void showAuthMenu(){
        authVBox.setVisible(true);
        regVBox.setVisible(false);
        mainLabel.setText("Авторизация");
    }

    @FXML
    public void register(){
        errorLabel.setText("");
        if (registerUsernameField.getText().isEmpty() || registerPasswordField1.getText().isEmpty() || registerPasswordField2.getText().isEmpty()) {
            errorLabel.setText("Заполните все поля и повторите попытку");
            return;
        }
        if (!registerPasswordField1.getText().equals(registerPasswordField2.getText())) {
            errorLabel.setText("Пароли не совпадают");
            return;
        }
//        User userInfo = new User(username, password);
//        backend.invokeCommand("auth", userInfo);
        Stage stage = (Stage) authVBox.getScene().getWindow();
        stage.close();
        mainStage.show();
    }

    @FXML
    public void auth(){
        errorLabel.setText("");
        if (registerUsernameField.getText().isEmpty() || registerPasswordField1.getText().isEmpty() || registerPasswordField2.getText().isEmpty()) {
            errorLabel.setText("Заполните все поля и повторите попытку");
            return;
        }
        // backend

    }
}
