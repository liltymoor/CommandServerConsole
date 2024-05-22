package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.client.ClientAppBackend;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.shared.network.User;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable {
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
    @FXML
    private ChoiceBox<String> langChoiceBox;

    private final HashMap<String, Locale> langMap = new HashMap<>();
    private final Stage mainStage;
    private final ClientAppBackend backend;
    private ResourceBundle messages;
    private ResourceBundle errors;
    private Locale locale;

    private void initializeLangMap() {
        langMap.put("English", new Locale("en", "US"));
        langMap.put("Русский", new Locale("ru", "RU"));
    }

    private String getLangByLocale(Locale locale) {
        for (Map.Entry<String, Locale> entry : langMap.entrySet()) {
            if (entry.getValue().equals(locale)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public AuthorizationController(ClientAppBackend backend, Stage mainStage, Locale locale) {
        this.backend = backend;
        this.mainStage = mainStage;
        this.locale = locale;
        this.errors = ResourceBundle.getBundle("org/client/errors", locale);
        this.messages = ResourceBundle.getBundle("org/client/messages", locale);
        initializeLangMap();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(locale);
        System.out.println(getLangByLocale(locale));
        langChoiceBox.setValue(getLangByLocale(locale));
        langChoiceBox.getItems().addAll(langMap.keySet());
        langChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Locale selectedLocale = langMap.get(newValue);
            if (selectedLocale != null) {
                switchLocale(selectedLocale);
            }
        });

    }

    private void switchLocale(Locale newLocale) {
        // Загрузка новых ресурсов для выбранной локали
        messages = ResourceBundle.getBundle("org/client/messages", newLocale);
        errors = ResourceBundle.getBundle("org/client/errors", newLocale);
        locale = newLocale;
        System.out.println("SWITCHED LANGUAGE");

        updateUI();

        // Установка новой локали в backend (если требуется)
        //backend.setLocale(newLocale);
    }

    @FXML
    public void underlineRegisterLabel() {
        registerLabel.setStyle("-fx-underline: true");
    }

    @FXML
    public void undoUnderlineRegisterLabel() {
        registerLabel.setStyle("-fx-underline: false");
    }

    @FXML
    public void undoUnderlineAuthLabel() {
        authLabel.setStyle("-fx-underline: false");
    }

    @FXML
    public void underlineAuthLabel() {
        authLabel.setStyle("-fx-underline: true");
    }

    @FXML
    public void showRegisterMenu() {
        regVBox.setVisible(true);
        authVBox.setVisible(false);
    }

    @FXML
    public void showAuthMenu() {
        authVBox.setVisible(true);
        regVBox.setVisible(false);
    }

    @FXML
    public void register() {
        errorLabel.setText("");
        String username = registerUsernameField.getText();
        String password = registerPasswordField1.getText();
        String passwordConfirm = registerPasswordField2.getText();
        if (username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            errorLabel.setText(errors.getString("empty_field_error"));
            return;
        }
        if (!password.equals(passwordConfirm)) {
            errorLabel.setText(errors.getString("password_mismatch_error"));
            return;
        }

        CommandResult regResult = backend.invokeCommand("reg", new User(username, password));
        if (regResult.getCode() != ActionCode.OK) {
            errorLabel.setText(errors.getString("reg_error"));
            return;
        }

        Stage stage = (Stage) authVBox.getScene().getWindow();
        stage.close();
        mainStage.show();
    }

    @FXML
    public void auth() {
        errorLabel.setText("");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText(errors.getString("empty_field_error"));
            return;
        }

        CommandResult authResult = backend.invokeCommand("auth", new User(username, password));
        if (authResult.getCode() != ActionCode.OK) {
            errorLabel.setText(errors.getString("auth_error"));
            return;
        }
        Stage stage = (Stage) authVBox.getScene().getWindow();
        stage.close();
        mainStage.show();
    }

    private void updateUI() {
        mainLabel.setText(messages.getString("auth_main_label"));
        usernameField.setPromptText(messages.getString("username"));
        passwordField.setPromptText(messages.getString("password"));
        authButton.setText(messages.getString("login_button"));
        registerLabel.setText(messages.getString("switch_auth2reg"));
        registerUsernameField.setPromptText(messages.getString("username"));
        registerPasswordField1.setPromptText(messages.getString("password"));
        registerPasswordField2.setPromptText(messages.getString("repeat_password"));
        registerButton.setText(messages.getString("register_button"));
        authLabel.setText(messages.getString("switch_reg2auth"));
    }

}
