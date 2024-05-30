package org.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.client.ClientAppBackend;
import org.client.commands.properties.DataProvidedCommandResult;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RadarTabController implements Initializable {
    @FXML
    private Canvas radarCanvas;

    private Pane currentInfoPane;
    private final MainController parentController;
    private final ClientAppBackend appBackend;
    private LinkedHashSet<HumanBeing> humanBeings;
    private final Map<String, Color> userColors = new HashMap<>();

    public RadarTabController(MainController parent, ClientAppBackend appBackend) throws IOException {
        this.appBackend = appBackend;
        this.parentController = parent;
        DataProvidedCommandResult<LinkedHashSet<HumanBeing>> commandResult = appBackend.callCommand("get_humans");
        humanBeings = (LinkedHashSet<HumanBeing>) commandResult.getData();
        System.out.println(humanBeings);
        // Example data
        loadExampleData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawObjects();
        radarCanvas.setOnMouseClicked(this::handleClick);
    }

    private void handleClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        for (HumanBeing humanBeing : humanBeings) {
            if (isClickedOnObject(humanBeing, x, y)) {
                showObjectInfo(humanBeing, x, y);
                break;
            }
        }
    }

    private boolean isClickedOnObject(HumanBeing humanBeing, double x, double y) {
        double hbX = humanBeing.getCoords().getX();
        double hbY = humanBeing.getCoords().getY();
        System.out.println(hbX + " " + hbY + " " + x + " " +  y);
        //System.out.println(Math.abs(x - hbX) < 10 && Math.abs(y - hbY) < 10);
        return Math.abs(x - hbX) < 10 && Math.abs(y - hbY) < 10; // Adjust the click tolerance as needed
    }

    private void showObjectInfo(HumanBeing humanBeing, double x, double y) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("HumanBeing Information");
//        alert.setHeaderText(null);
//        alert.setContentText(humanBeing.toString());
//        alert.showAndWait();
        if (currentInfoPane != null) {
            ((AnchorPane) radarCanvas.getParent()).getChildren().remove(currentInfoPane);
        }

        currentInfoPane = new VBox();
        currentInfoPane.setStyle("-fx-background-color: white; -fx-border-color: black;");
        currentInfoPane.setPadding(new Insets(10));
//        currentInfoPane.setSpacing(5);

        Label idLabel = new Label("ID: " + humanBeing.getId());
        Label nameLabel = new Label("Name: " + humanBeing.getName());
        Label coordsLabel = new Label("Coordinates: " + humanBeing.getCoords());
        Label realHeroLabel = new Label("Real Hero: " + humanBeing.getRealHero());
        // Add more labels for other fields...

        currentInfoPane.getChildren().addAll(idLabel, nameLabel, coordsLabel, realHeroLabel);

        // Add random GIF image
        ImageView gifImageView = new ImageView(getRandomGifImage());
        currentInfoPane.getChildren().add(gifImageView);

        // Position the pane
        currentInfoPane.setLayoutX(x + 10);
        currentInfoPane.setLayoutY(y + 10);

        ((AnchorPane) radarCanvas.getParent()).getChildren().add(currentInfoPane);
    }

    private Image getRandomGifImage() {
        File folder = new File("./"); // Update with your actual path
        System.out.println(Arrays.toString(folder.listFiles()));
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".gif"));
        System.out.println(Arrays.toString(files));
        if (files == null || files.length == 0) {
            return null;
        }
        Random random = new Random();
        File randomGifFile = files[random.nextInt(files.length)];
        return new Image(randomGifFile.toURI().toString());
    }


    private void loadExampleData() throws IOException {
        // Add example HumanBeing objects to the list
        //humanBeings.add(new HumanBeing("Alice", new Coordinates(100F, 150L), true, false, 20L, 15.5, WeaponType.SWORD, Mood.HAPPY, new Car("BMW"), "user1"));
        //humanBeings.add(new HumanBeing("Bob", new Coordinates(200F, 250L), false, true, 30L, 20.0, WeaponType.GUN, Mood.SAD, new Car("Audi"), "user2"));
        // Assign colors to users
        userColors.put("grigory222", Color.RED);
        //userColors.put("user2", Color.BLUE);
    }

    private void drawObjects() {
        GraphicsContext gc = radarCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, radarCanvas.getWidth(), radarCanvas.getHeight());
        System.out.println("test"+humanBeings);
        for (HumanBeing humanBeing : humanBeings) {
            System.out.println("test");
            System.out.println(userColors);
            Color color = userColors.getOrDefault(humanBeing.getEntityOwner(), Color.BLACK);
            gc.setFill(color);
            gc.fillOval(humanBeing.getCoords().getX(), humanBeing.getCoords().getY(), 20, 20); // Adjust the size as needed
        }
        System.out.println("test_final");
    }

    // Method to add/update/remove HumanBeing objects
    public void updateHumanBeings(LinkedHashSet<HumanBeing> newHumanBeings) {
        humanBeings = newHumanBeings;
        drawObjects();
    }
}
