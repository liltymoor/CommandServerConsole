package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.client.ClientAppBackend;
import org.shared.network.User;

public class AuthorizationController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    private ClientAppBackend backend;

    public AuthorizationController(ClientAppBackend backend) {
        this.backend = backend;
    }
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        if (username.isEmpty()) return;
        String password = passwordField.getText();
        if (password.isEmpty()) return;

        User userInfo = new User(username, password);
        backend.invokeCommand("auth", userInfo);
    }
}
