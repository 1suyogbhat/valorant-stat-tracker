package ca.ucalgary.sbhat.projectvaloranttrackergui;

import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.Game;
import ca.ucalgary.sbhat.projectvaloranttrackergui.util.Reader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

/**
 * Main JavaFX application's class for Valorant Game Tracker
 * This part is responsible for launching the application with the startup screen.
 */
public class HelloApplication extends Application {
    //List that holds all the data from the csv file.
    private List<Game> games = new ArrayList<>();

    /**
     * Entry point for the JavaFX application
     * Method is automatically called when the application is launched
     * @param primaryStage primary stage of the application
     * @throws Exception throws exception if the FXML/ file loader fails.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parameters params = getParameters(); //command line args
        if (!params.getRaw().isEmpty()) {
            String fileName = params.getRaw().getFirst(); // Get the first argument
            System.out.println("File passed: " + fileName);
            File file = new File(fileName);
            games = Reader.readDataFromFile(file);
        } else {
            System.out.println("No file passed as an argument.");
        }

        Font.loadFont(getClass().getResourceAsStream("/fonts/ValorantFont.ttf"), 12);
        // load the Startup screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("startup-view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        startupController controller = loader.getController();
        // Pass the games to the controller if they were loaded
        if (games != null) {
            controller.setGames(games);
        }
        // Apply CSS AFTER loading font
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/styles/styles.css")).toExternalForm());
        //setting up the primary stage.
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome to Valorant Tracker");
        primaryStage.show();
    }


    /**
     * Main method for the JavaFX application
     * Launches the JavaFX application using the command line arguments
     * @param args optional args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
