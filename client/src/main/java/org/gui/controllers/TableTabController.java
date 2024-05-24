package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.client.ClientAppBackend;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class TableTabController implements Initializable{

    private ClientAppBackend appBackend;
    @FXML
    private TableView<HumanBeing> dataTable;




    // TABLE COLUMNS

    @FXML
    private TableColumn<HumanBeing, Integer> idColumn;

    @FXML
    private TableColumn<HumanBeing, String> nameColumn;

    @FXML
    private TableColumn<HumanBeing, Coordinates> coordsColumn;

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
    private TableColumn<HumanBeing, Car> modelCarColumn;

    //

    public void setAppBackend(ClientAppBackend appBackend) {
        this.appBackend = appBackend;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordsColumn.setCellValueFactory(new PropertyValueFactory<>("coords"));
        zonedDTColumn.setCellValueFactory(new PropertyValueFactory<>("zonedDT"));
        realHeroColumn.setCellValueFactory(new PropertyValueFactory<>("realHero"));
        hasToothpickColumn.setCellValueFactory(new PropertyValueFactory<>("hasToothpick"));
        impactSpeedColumn.setCellValueFactory(new PropertyValueFactory<>("impactSpeed"));
        minutesOfWaitingColumn.setCellValueFactory(new PropertyValueFactory<>("minutesOfWaiting"));
        weaponTypeColumn.setCellValueFactory(new PropertyValueFactory<>("weaponType"));
        moodColumn.setCellValueFactory(new PropertyValueFactory<>("mood"));
        modelCarColumn.setCellValueFactory(new PropertyValueFactory<>("modelCar"));

        dataTable.getItems().addAll();
    }
}
