package org.gui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
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
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.client.ClientAppBackend;
import org.client.commands.properties.DataProvidedCommandResult;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

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

    public RadarTabController(MainController parent, ClientAppBackend appBackend) {
        this.appBackend = appBackend;
        this.parentController = parent;


        //        // dbg
        try {
            appBackend.invokeCommand("add", new HumanBeing("asdest", new Coordinates(550F, 200L), false, false, 100L, 100.0, WeaponType.AXE, Mood.APATHY, new Car("123")));
            System.out.println("add test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        DataProvidedCommandResult<LinkedHashSet<HumanBeing>> commandResult = appBackend.callCommand("get_humans");
        humanBeings = commandResult.getData();
        System.out.println(humanBeings);
        // Example data
        initColorMap();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawObjects();
        radarCanvas.setOnMouseClicked(this::handleClick);
    }

    private void initColorMap() {
        for (HumanBeing human : humanBeings) {
            String owner = human.getEntityOwner();
            if (!userColors.containsKey(owner)) {
                userColors.put(owner, generateUniqueColor(owner));
            }
        }
    }

    private Color generateUniqueColor(String owner) {
        int hash = owner.hashCode();
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = hash & 0x0000FF;
        return Color.rgb(r, g, b);
    }

    private void handleClick(MouseEvent event) {
        System.out.println("clicked");
        double x = event.getX();
        double y = event.getY();

        for (HumanBeing humanBeing : humanBeings) {
            if (isClickedOnObject(humanBeing, x, y)) {
                showObjectInfo(humanBeing, x, y);
                return;
            }
        }

        if (currentInfoPane != null) {
            applyExitAnimation(currentInfoPane);
            //((AnchorPane) radarCanvas.getParent()).getChildren().remove(currentInfoPane);
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
        if (currentInfoPane != null) {
            applyExitAnimation(currentInfoPane);
            //((AnchorPane) radarCanvas.getParent()).getChildren().remove(currentInfoPane);
        }

        currentInfoPane = new VBox();
        currentInfoPane.getStyleClass().add("info-pane");
        currentInfoPane.setPadding(new Insets(10));

        Label idLabel = new Label("ID: " + humanBeing.getId());
        Label nameLabel = new Label("Name: " + humanBeing.getName());
        Label coordsLabel = new Label("Coordinates: (" + humanBeing.getCoords().getX() + ", " + humanBeing.getCoords().getY() + ")");
        Label realHeroLabel = new Label("Real Hero: " + humanBeing.getRealHero());

        currentInfoPane.getChildren().addAll(idLabel, nameLabel, coordsLabel, realHeroLabel);

        // Add random GIF image
        ImageView gifImageView = getRandomGifImageView();
        currentInfoPane.getChildren().add(gifImageView);

        System.out.println("info pane" + x + " "+  y);
        // Position the pane
        currentInfoPane.setLayoutX(x + 10);
        currentInfoPane.setLayoutY(y + 10);

        ((AnchorPane) radarCanvas.getParent()).getChildren().add(currentInfoPane);
        applyEnterAnimation(currentInfoPane);

    }
    private void applyEnterAnimation(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), pane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), pane);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        fadeTransition.play();
        scaleTransition.play();
    }

    private void applyExitAnimation(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), pane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), pane);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.5);
        scaleTransition.setToY(0.5);

        fadeTransition.setOnFinished(e -> ((AnchorPane) radarCanvas.getParent()).getChildren().remove(pane));

        fadeTransition.play();
        scaleTransition.play();
    }

    private ImageView getRandomGifImageView() {
        File folder = new File("./gif");
        System.out.println(Arrays.toString(folder.listFiles()));
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".gif"));
        System.out.println(Arrays.toString(files));
        if (files == null || files.length == 0) {
            return null;
        }
        Random random = new Random();
        File randomGifFile = files[random.nextInt(files.length)];
        Image gifImage = new Image(randomGifFile.toURI().toString());
        ImageView gifView = new ImageView(gifImage);

        // Set size for ImageView
        double maxSize = 100;  // Example size
        gifView.setFitWidth(maxSize);
        gifView.setFitHeight(maxSize);
        gifView.setPreserveRatio(true);

        return gifView;
    }


    private void drawObjects() {
        for (HumanBeing humanBeing : humanBeings) {
            double x = humanBeing.getCoords().getX();
            double y = humanBeing.getCoords().getY();
            String owner = humanBeing.getEntityOwner();
            Color color = userColors.get(owner);
            drawCircle(x, y, color, humanBeing);
        }
    }

    private void drawCircle(double x, double y, Color color, HumanBeing humanBeing) {
        double radius = 10;

        Circle circle = new Circle(x, y, radius);
        circle.setFill(color);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);

        circle.setOnMouseEntered(event -> applyMouseEnterAnimation(circle));
        circle.setOnMouseExited(event -> applyMouseExitAnimation(circle));
        circle.setOnMouseClicked(event -> showObjectInfo(humanBeing, x, y));

        AnchorPane parent = (AnchorPane) radarCanvas.getParent();
        parent.getChildren().add(circle);
    }

    private void applyMouseEnterAnimation(Circle circle) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), circle);
        st.setToX(1.5);
        st.setToY(1.5);
        st.play();
    }

    private void applyMouseExitAnimation(javafx.scene.shape.Circle circle) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), circle);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    // Method to add/update/remove HumanBeing objects
    public void updateHumanBeings(LinkedHashSet<HumanBeing> newHumanBeings) {
        humanBeings = newHumanBeings;
        drawObjects();
    }
}
