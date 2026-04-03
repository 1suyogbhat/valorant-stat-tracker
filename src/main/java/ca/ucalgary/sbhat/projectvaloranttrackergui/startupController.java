package ca.ucalgary.sbhat.projectvaloranttrackergui;

import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.Game;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

public class startupController {

    @FXML
    private VBox proceedBox;
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private StackPane rootPane;

    @FXML
    private Text startupText;

    @FXML
    public void initialize() {

        // Bind the ImageView's fitWidth and fitHeight to the StackPane's width and height
        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());
        // First delay: Fade in the welcome text after 1 second
        PauseTransition delayText = new PauseTransition(Duration.seconds(1));
        FadeTransition fadeInText = new FadeTransition(Duration.seconds(1), startupText);
        fadeInText.setFromValue(0);
        fadeInText.setToValue(1);

        // Second delay: After text appears, wait 2 seconds before showing proceed box
        PauseTransition delayBox = new PauseTransition(Duration.seconds(2));
        FadeTransition fadeInBox = new FadeTransition(Duration.seconds(1), proceedBox);
        fadeInBox.setFromValue(0);
        fadeInBox.setToValue(1);

        delayText.setOnFinished(e -> fadeInText.play());
        fadeInText.setOnFinished(e -> delayBox.play());
        delayBox.setOnFinished(e -> fadeInBox.play());

        delayText.play(); // Start the chain
    }

    private List<Game> games;

    public void setGames(List<Game> games) {
        this.games = games;
    }

    // This method is triggered when the Proceed button is clicked.
    @FXML
    private void handleProceed(ActionEvent event) {
        try {
            // Load the HelloView FXML (main screen after the startup screen).
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/ucalgary/sbhat/projectvaloranttrackergui/hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            // Retrieve the controller for the HelloView
            HelloController helloController = loader.getController();
            if (games == null) {
                games = new ArrayList<>(); // Initialize to avoid null
            }
            helloController.loadGames(games);
            // Apply CSS AFTER loading font
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/styles/styles.css")).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Valorant Game Tracker");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
