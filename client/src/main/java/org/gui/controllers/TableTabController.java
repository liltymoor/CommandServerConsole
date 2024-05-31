package org.gui.controllers;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.*;
import org.client.ClientAppBackend;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class TableTabController implements Initializable {

    public TableTabController(MainController parent, ClientAppBackend appBackend) {
        this.appBackend = appBackend;
        parentController = parent;
    }
    private final MainController parentController;
    private ClientAppBackend appBackend;

    @FXML
    private TableView<HumanBeing> dataTable;

    private boolean isEditing = false;

    public boolean isEditing() {
        return isEditing;
    }

    // TABLE COLUMNS

    @FXML
    private TableColumn<HumanBeing, Integer> idColumn;

    @FXML
    private TableColumn<HumanBeing, String> nameColumn;

    @FXML
    private TableColumn<HumanBeing, Float> xColumn;

    @FXML
    private TableColumn<HumanBeing, Long> yColumn;

    @FXML
    private TableColumn<HumanBeing, ZonedDateTime> zonedDTColumn;

    @FXML
    private TableColumn<HumanBeing, Boolean> realHeroColumn;

    @FXML
    private TableColumn<HumanBeing, Boolean> hasToothpickColumn;

    @FXML
    private TableColumn<HumanBeing, Long> impactSpeedColumn;

    @FXML
    private TableColumn<HumanBeing, Double> minutesOfWaitingColumn;

    @FXML
    private TableColumn<HumanBeing, WeaponType> weaponTypeColumn;

    @FXML
    private TableColumn<HumanBeing, Mood> moodColumn;

    @FXML
    private TableColumn<HumanBeing, String> modelCarColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setEditable(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.setName(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));


            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.setName(event.getOldValue());
            isEditing = false;
        });
        nameColumn.setOnEditStart(event -> isEditing = true);

        xColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getCoords().getX()).asObject());
        xColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        xColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.getCoords().setX(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.getCoords().setX(event.getOldValue());
            isEditing = false;
        });
        xColumn.setOnEditStart(event -> isEditing = true);

        yColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getCoords().getY()).asObject());
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        yColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())){
                isEditing = false;
                return;
            }

            HumanBeing being = event.getRowValue();
            try {
                being.getCoords().setY(event.getNewValue());
            } catch (IOException e) {
                System.out.println("[TABLE] The Y value can't be less then -190");
                isEditing = false;
                return;
            }

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) {
                try {
                    being.getCoords().setY(event.getOldValue());
                } catch (IOException ignored) {
                    System.out.println("Эта ошибка не должна возникнуть, если ты это видишь - у тебя все хуево.");
                }
            }
            isEditing = false;
        });
        yColumn.setOnEditStart(event -> isEditing = true);

        zonedDTColumn.setCellValueFactory(new PropertyValueFactory<>("zonedDT"));
        zonedDTColumn.setEditable(false);

        realHeroColumn.setCellValueFactory(new PropertyValueFactory<>("realHero"));
        realHeroColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new Boolean[] {true, false}));
        realHeroColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.setRealHero(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.setRealHero(event.getOldValue());
            isEditing = false;
        });
        realHeroColumn.setOnEditStart(event -> isEditing = true);


        hasToothpickColumn.setCellValueFactory(new PropertyValueFactory<>("hasToothpick"));
        hasToothpickColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new Boolean[] {true, false}));
        hasToothpickColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.setHasToothpick(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.setHasToothpick(event.getOldValue());
            isEditing = false;
        });
        hasToothpickColumn.setOnEditStart(event -> isEditing = true);


        impactSpeedColumn.setCellValueFactory(new PropertyValueFactory<>("impactSpeed"));
        impactSpeedColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        impactSpeedColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.setImpactSpeed(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.setImpactSpeed(event.getOldValue());
            isEditing = false;
        });
        impactSpeedColumn.setOnEditStart(event -> isEditing = true);

        minutesOfWaitingColumn.setCellValueFactory(new PropertyValueFactory<>("minutesOfWaiting"));
        minutesOfWaitingColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        minutesOfWaitingColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.setMinutesOfWaiting(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.setMinutesOfWaiting(event.getOldValue());
            isEditing = false;
        });
        minutesOfWaitingColumn.setOnEditStart(event -> isEditing = true);

        weaponTypeColumn.setCellValueFactory(new PropertyValueFactory<>("weaponType"));
        weaponTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(WeaponType.values()));
        weaponTypeColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.setWeaponType(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.setWeaponType(event.getOldValue());
            isEditing = false;
        });
        weaponTypeColumn.setOnEditStart(event -> isEditing = true);

        moodColumn.setCellValueFactory(new PropertyValueFactory<>("mood"));
        moodColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Mood.values()));
        moodColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.setMood(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.setMood(event.getOldValue());
            isEditing = false;
        });
        moodColumn.setOnEditStart(event -> isEditing = true);

        modelCarColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelCar().getName()));
        modelCarColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCarColumn.setOnEditCommit(event -> {
            if (event.getOldValue().equals(event.getNewValue())) {
                isEditing = false;
                return;
            };

            HumanBeing being = event.getRowValue();
            being.getModelCar().setName(event.getNewValue());

            System.out.println("[TABLE] New cell \"%s\" value: %s%n".formatted(event.getTableColumn().getText(), event.getNewValue()));

            CommandResult updateResult = appBackend.invokeCommand("update", being);
            if (updateResult.getCode() != ActionCode.OK) being.getModelCar().setName(event.getOldValue());
            isEditing = false;
        });
        modelCarColumn.setOnEditStart(event -> isEditing = true);

        dataTable.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                ObservableList<HumanBeing> selectedItems = dataTable.getSelectionModel().getSelectedItems();
                for (HumanBeing being : selectedItems) {
                    CommandResult deleteResult = appBackend.invokeCommand("remove", being.getId());
                    if (deleteResult.getCode() == ActionCode.OK)
                        appBackend.getLocalCollection().remove(being);
                }
            }
        });

        dataTable.setItems(appBackend.getLocalCollection());
    }

    @FXML
    public void syncAction() {
        appBackend.syncCollection();
    }

    @FXML
    public void clearAction() {
        CommandResult clearResult = appBackend.invokeCommand("clear");
        if (clearResult.getCode() != ActionCode.OK)
            appBackend.invokeCommand("sync");
    }


    // TODO Сделать адекватно снизу понос
    @FXML
    public void addAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/client/fxml/human_form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Human Being Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(dataTable.getScene().getWindow());
            dialogStage.setScene(scene);


            HumanFormController formController = loader.getController();
            formController.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (formController.isSubmitClicked()) {
                HumanBeing result = formController.getResult();
                CommandResult addResult = appBackend.invokeCommand("add", result);
                if (addResult.getCode() == ActionCode.OK)
                    appBackend.invokeCommand("sync", appBackend.getLocalCollection());
            }
        } catch (IOException e) {
            System.out.println("[MODAL] " + e.getMessage());
        }
    }

    @FXML
    public void addLowerAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/client/fxml/human_form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Human Being Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(dataTable.getScene().getWindow());
            dialogStage.setScene(scene);


            HumanFormController formController = loader.getController();
            formController.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (formController.isSubmitClicked()) {
                HumanBeing result = formController.getResult();
                CommandResult addResult = appBackend.invokeCommand("add_if_min", result);
                if (addResult.getCode() == ActionCode.OK)
                    appBackend.invokeCommand("sync", appBackend.getLocalCollection());
            }
        } catch (IOException e) {
            System.out.println("[MODAL] " + e.getMessage());
        }
    }

    @FXML
    public void removeLowerAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/client/fxml/human_form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Human Being Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(dataTable.getScene().getWindow());
            dialogStage.setScene(scene);


            HumanFormController formController = loader.getController();
            formController.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (formController.isSubmitClicked()) {
                HumanBeing result = formController.getResult();
                CommandResult addResult = appBackend.invokeCommand("remove_lower", result);
                if (addResult.getCode() == ActionCode.OK)
                    appBackend.invokeCommand("sync", appBackend.getLocalCollection());
            }
        } catch (IOException e) {
            System.out.println("[MODAL] " + e.getMessage());
        }
    }
}
