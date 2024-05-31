package org.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HumanFormController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private CheckBox realHeroField;
    @FXML
    private CheckBox toothpickField;
    @FXML
    private TextField impactField;
    @FXML
    private TextField minutesField;
    @FXML
    private ComboBox<WeaponType> weaponField;
    @FXML
    private ComboBox<Mood> moodField;
    @FXML
    private TextField carField;


    private Stage dialogStage;
    private boolean submitClicked = false;
    private HumanBeing result;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSubmitClicked() {
        return submitClicked;
    }

    public HumanBeing getResult() {
        return result;
    }

    @FXML
    private void handleSubmit() {
        try {
            result = new HumanBeing(
                    nameField.getText(),
                    new Coordinates(Float.parseFloat(xField.getText()), Long.parseLong(yField.getText())),
                    realHeroField.isSelected(),
                    toothpickField.isSelected(),
                    Long.parseLong(impactField.getText()),
                    Double.parseDouble(minutesField.getText()),
                    weaponField.getValue(),
                    moodField.getValue(),
                    new Car(carField.getText()));
        } catch (IOException e) {
            result = null;
            System.out.println("[MODAL] Bad input | " + e.getMessage());
            dialogStage.close();
        }
        submitClicked = true;
        dialogStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weaponField.setItems(FXCollections.observableArrayList(WeaponType.values()));
        moodField.setItems(FXCollections.observableArrayList(Mood.values()));
    }
}
