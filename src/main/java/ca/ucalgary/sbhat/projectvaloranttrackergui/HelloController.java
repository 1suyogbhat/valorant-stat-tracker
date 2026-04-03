package ca.ucalgary.sbhat.projectvaloranttrackergui;

import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.Game;
import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.LeaderboardEntry;
import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.winningStreak;
import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.Player;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Agents;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Gamemode;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Maps;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Rank;
import ca.ucalgary.sbhat.projectvaloranttrackergui.util.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.awt.*;
import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 * Hello Controller class that is called in main, in order to perform all the operations of the javaFX application
 * This class is responsible for handling all the UI interactions,
 * loading of the data, creation of a new player/game, displaying
 * individual player stats and collective leader board
 */

public class HelloController {
    @FXML
    private Label mainLabel;

    @FXML
    private Button loadPlayersButton; //button that loads the player data from a file

    //button that is used for creating a new player
    @FXML
    private Button createPlayerButton;

    //pane that shows the list of existing players in the game
    @FXML
    private AnchorPane existingPlayersPane;

    //stores all the loaded/created games
    private final List<Game> playerGames = new ArrayList<>();
    //stores all the players that are registered in the javaFX application; observable list that is used for displaying the players in the table view
    private final ObservableList<Player> players = FXCollections.observableArrayList();

    //stores the name of the player
    private String playerName;
    //Item trigger to load data
    public MenuItem loadMenuItem;

    //drop down for selecting a map in the new game
    @FXML
    private ComboBox<String> mapComboBox;

    //this is used for mapping the usernames of the player to player objects, so that they can be looked up quickly
    private HashMap<String, Player> playerStats;

    //dropdown combo box that is used for selecting if someone lost/won the game
    @FXML
    private ComboBox<String> createGameWinLossComboBox;

    //references back to the main application
    @FXML
    private Stage stage;

    //dropdown that is used for selecting the modes of the game
    @FXML
    private ComboBox<String> gameModeComboBox;

    //dropdown that is used for selecting the agents in the game
    @FXML
    private ComboBox<String> agentComboBox;


    //displays the instructions of valid rank
    @FXML
    private Label validRanksLabel;

    //button that is used for handling the creation player logic
    @FXML
    private Button handleCreatePlayer;

    //label which is at the top of the UI to display a welcome message
    @FXML
    private Label welcomeText;
    @FXML
    private Label createGameStatusLabel;

    @FXML
    private ImageView ironPNG;

    //menu item that is used for quitting the application
    @FXML
    public MenuItem quitMenuItem;

    //the pane in which the controls of the creating new game are present
    @FXML
    private AnchorPane createGamePane;

    //the controls in which everything associated with the leaderboard function is present
    @FXML
    private AnchorPane leaderboardPane;

    //the pane in which all the controls associated with stats are present
    @FXML
    private AnchorPane statSearchPane;

    //the pane in which all the controls for adding a new player are present
    @FXML
    private AnchorPane addPlayerPane;

    @FXML
    private AnchorPane addGamePane;

    //Radio button that is used for displaying create game pane
    @FXML
    private RadioButton createGameRadio;

    //radio button that is used for displaying create player pane
    @FXML
    private RadioButton createPlayerRadio;

    //radio button that is used for displaying player stats pane
    @FXML
    private RadioButton statSearchRadio;

    //radio button that is used for displaying all the current players in the application
    @FXML
    private RadioButton printPlayersRadio;

    //radio button that is used for displaying the best/worst agent
    @FXML
    private RadioButton bestWorstAgentRadio;

    //radio button that is used for displaying the worst agent
    @FXML
    private RadioButton worstAgentRadioButton;

    //radio button that is used for displaying the best agent
    @FXML
    private RadioButton bestAgentRadioButton;

    //radio button that is used for displaying the leaderboard
    @FXML
    private RadioButton leaderBoardRadio;

    //text field for entering the username
    @FXML
    private TextField usernameTextField;

    //text field for entering kill count while creating game
    @FXML
    private TextField createGameKillsTextField;

    //text field for entering assist count while creating game
    @FXML
    private TextField createGameAssistsTextField;

    //text field for entering death count while creating game
    @FXML
    private TextField createGameDeathsTextField;

    //text field where the player's username is entered while creating a new game
    @FXML
    private TextField createGameUsername;

    //text field where the player's rank is entered while creating a new game
    @FXML
    private TextField rankTextField;

    //the area in which all the panes are present
    @FXML
    private AnchorPane mainContentArea;

    //the table that is used for displaying all the players who are currently in the game
    @FXML
    private TableView<Player> playersTableView;

    //the pane that contains the functions for displaying the best or the worst agent
    @FXML
    private AnchorPane bestWorstAgentPane;


    @FXML
    private TableColumn<Player, String> usernameColumn;

    @FXML
    private TableColumn<Player, String> rankColumn;

    @FXML
    private TextField playerUsernameTextField;

    @FXML
    private TextArea statSearchTextArea;

    //username field which in which data is entered to search statistics
    @FXML
    private TextField statSearchUsernameTextField;

    //the radio button that is used to display the best map
    @FXML
    private RadioButton bestMapRadioButton;

    @FXML
    private RadioButton winningStreakRadio;

    //the radio button that is used to display the worst map
    @FXML
    private RadioButton worstMapRadioButton;

    //the pane that contains the functions for displaying the best or the worst map
    @FXML
    private AnchorPane bestWorstMapPane;

    //menu radio button that is used for displaying the worst or best map pane
    @FXML
    private RadioButton bestWorstMapRadio;

    //table that is used for display the leaderboard rankings
    @FXML
    private TableView<LeaderboardEntry> leaderboardTable;
    //column in the table of leaderboard to display the rank of the player
    @FXML
    private TableColumn<LeaderboardEntry, String> rankColumnLeaderBoard;
    //column in the table of leaderboard to display the name of the player
    @FXML
    private TableColumn<LeaderboardEntry, String> nameColumn;

    //column in the table of leaderboard to display the cumulative KDA of the player
    @FXML
    private TableColumn<LeaderboardEntry, Number> kdaColumn;
    //column in the table of leaderboard to display the cumulative performance score of the player
    @FXML
    private TableColumn<LeaderboardEntry, Number> scoreColumn;

    //table that is used for displaying the winning streaks of the player
    @FXML
    private TableView<winningStreak> winningStreakTable;

    //column that is used for displaying the username of the player in the winning streak table
    @FXML
    private TableColumn<winningStreak, String> usernameColWinningStreak;

    //column that is used for displaying the rank of the player in the winning streak table
    @FXML
    private TableColumn<winningStreak, String> rankColWinningStreak;

    //column that is used for displaying the max streak value for the player in the winning streak table
    @FXML
    private TableColumn<winningStreak, Integer> maxStreakCol;

    //column that is used for displaying the min streak value for the player in the winning streak table
    @FXML
    private TableColumn<winningStreak, Integer> minStreakCol;

    //column that is used for displaying the current streak value for the player in the winning streak table
    @FXML
    private TableColumn<winningStreak, Integer> currentStreakCol;


    //text field to enter the username of the player while selecting the best/worst agent for displaying.
    @FXML
    private TextField playerUsernameTextFieldBWAgent;

    //label that is used for displaying the menu option
    @FXML
    private Label whichMenuOptionChosen;

    //grid pane layout for the menu options
    @FXML
    private GridPane menuGrid;

    @FXML
    private Line mainLine;

    @FXML
    private ComboBox<String> savePlayerComboBox;

    //pane for the save game section in the UI
    @FXML
    private AnchorPane saveGamePane;

    //pane for the winning streak in the UI
    @FXML
    private AnchorPane winningStreakPane;

    //menuItem that is used for saving a game
    @FXML
    private MenuItem saveGame;

    //combo box for sorting in the UI
    @FXML
    private ComboBox<String> sortOrderComboBox;

    //combobox that is used for selecting the username while creating a new game.
    @FXML
    private ComboBox<String> createGameUsernameComboBox;

    /**
     * This method sets the current player statistic map for use
     * It is ideally called before anything in the application is initialized/stored.
     */
    public void setPlayerStats(HashMap<String, Player> playerStats) {
        this.playerStats = playerStats;
    }
    /**
     * This method is responsible for setting up the primary stage for the java application
     * @param stage The stage to set up
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sample action that is triggered by the hello welcome UI button
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * Initialize() function initializes all the javaFX controllers when the FXML elements are injected
     */
    @FXML
    public void initialize() {

        // set up the menu items
        //quit action for menu is defined
        quitMenuItem.setOnAction(event -> closeProgram());
        whichMenuOptionChosen.setText("Select a menu option");
        menuGrid.setVisible(true);

        //creating a toggle group for all the radio buttons so that only one of them is selected at once
        ToggleGroup toggleGroup = new ToggleGroup();
        createGameRadio.setToggleGroup(toggleGroup);
        createPlayerRadio.setToggleGroup(toggleGroup);
        printPlayersRadio.setToggleGroup(toggleGroup);
        statSearchRadio.setToggleGroup(toggleGroup);
        bestWorstMapRadio.setToggleGroup(toggleGroup);
        bestWorstAgentRadio.setToggleGroup(toggleGroup);
        leaderBoardRadio.setToggleGroup(toggleGroup);
        bestMapRadioButton.setToggleGroup(toggleGroup);
        worstMapRadioButton.setToggleGroup(toggleGroup);
        bestAgentRadioButton.setToggleGroup(toggleGroup);
        worstAgentRadioButton.setToggleGroup(toggleGroup);
        winningStreakRadio.setToggleGroup(toggleGroup);

        // Initially hiding all the panes to represent a clean UI
        addGamePane.setVisible(false);
        addPlayerPane.setVisible(false);
        existingPlayersPane.setVisible(false);
        bestWorstMapPane.setVisible(false);
        statSearchPane.setVisible(false);
        leaderboardPane.setVisible(false);
        bestWorstAgentPane.setVisible(false);
        saveGamePane.setVisible(false);
        winningStreakPane.setVisible(false);

        //show only create game pane when the respective radio button is selected
        createGameRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("Create Game");
            addGamePane.setVisible(true);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(false);
            bestWorstMapPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(false);
            saveGamePane.setVisible(false);
            winningStreakPane.setVisible(false);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        //show only create player pane when the respective radio button is selected
        createPlayerRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("Create Player");
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(true);
            existingPlayersPane.setVisible(false);
            bestWorstMapPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(false);
            saveGamePane.setVisible(false);
            winningStreakPane.setVisible(false);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        //show only display all players pane when the respective radio button is selected
        printPlayersRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("Existing Players");
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(true);
            bestWorstMapPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(false);
            saveGamePane.setVisible(false);
            winningStreakPane.setVisible(false);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        //show only worst/best map  pane when the respective radio button is selected
        bestWorstMapRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("Best/Worst Map");
            bestWorstMapPane.setVisible(true);
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(false);
            saveGamePane.setVisible(false);
            winningStreakPane.setVisible(false);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        //show only Player stats pane when the respective radio button is selected
        statSearchRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("Player Statistics");
            bestWorstMapPane.setVisible(false);
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(false);
            statSearchPane.setVisible(true);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(false);
            saveGamePane.setVisible(false);
            winningStreakPane.setVisible(false);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        //show only leaderboard pane when the respective radio button is selected
        leaderBoardRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("LEADERBOARD");
            bestWorstMapPane.setVisible(false);
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(true);
            updateLeaderboard(null);
            bestWorstAgentPane.setVisible(false);
            winningStreakPane.setVisible(false);
            saveGamePane.setVisible(false);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        //show only best/worst agent pane when the respective radio button is selected
        bestWorstAgentRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("Best/Worst Agent");
            bestWorstMapPane.setVisible(false);
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(true);
            saveGamePane.setVisible(false);
            winningStreakPane.setVisible(false);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        //show only save game pane when the respective radio button is selected
        saveGame.setOnAction(event -> {
            whichMenuOptionChosen.setText("Save Game");
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(false);
            bestWorstMapPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(false);
            winningStreakPane.setVisible(false);
            createGameStatusLabel.setText("");
            saveMenuItem();
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        winningStreakRadio.setOnAction(event -> {
            whichMenuOptionChosen.setText("Winning Streak");
            bestWorstMapPane.setVisible(false);
            addGamePane.setVisible(false);
            addPlayerPane.setVisible(false);
            existingPlayersPane.setVisible(false);
            statSearchPane.setVisible(false);
            leaderboardPane.setVisible(false);
            bestWorstAgentPane.setVisible(false);
            saveGamePane.setVisible(false);
            winningStreakPane.setVisible(true);
            updateWinningStreak(null);
            createGameStatusLabel.setText("");
            playerGamesListView.getItems().clear();
            handleRadioButtonClick();
            refreshCreateGameUsernameComboBox();
        });

        createGameUsernameComboBox.getItems().addAll(
                players.stream()
                        .map(Player::getUsername)
                        .toList()
        );

        //populating the options of the map combo box
        mapComboBox.getItems().addAll(
                "ASCENT",
                "BIND",
                "HAVEN",
                "SPLIT",
                "LOTUS",
                "SUNSET",
                "ICEBOX",
                "BREEZE",
                "FRACTURE"
        );
        //populating the options of the game mode combo box
        gameModeComboBox.getItems().addAll(
                "UNRATED",
                "SPIKE_RUSH",
                "DEATHMATCH",
                "ESCALATION",
                "COMPETITIVE",
                "TEAM_DEATHMATCH",
                "CUSTOM",
                "REPLICATION"
        );
        //populating the options of the agent combo box
        agentComboBox.getItems().addAll(
                "BRIMSTONE", "VIPER", "OMEN", "CYPHER",
                "PHOENIX", "SOVA", "KILLJOY", "RAZE",
                "BREACH", "SKYE", "REYNA", "SAGE",
                "KO", "CYPHER", "YORU", "ASTRA",
                "VOLT", "NEON", "ECHO", "CHAMBER",
                "FADE", "VYSE", "TEJO", "WAYLAY",
                "JETT", "KAYO", "GEKKO", "HARBOR",
                "DEADLOCK", "ISO", "CLOVE"
        );

        sortOrderComboBox.getItems().addAll(
                "Rank Ascending Order",
                "Rank Descending Order"
        );
        //populating the options of the win/lost combo box
        createGameWinLossComboBox.getItems().addAll(
                "WIN", "LOSS"
        );

        // the tables for displaying the player information are binded
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        rankColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRank().name()));

        //checks for null issues, in case any with the playerTableView.
        if (playersTableView == null) {
            System.out.println("playersTableView is null. Check FXML file.");
        }
        // Optional: providing a placeholder text for the table
        assert playersTableView != null;
        playersTableView.setPlaceholder(new Label("No players available."));

        //initially hiding the best and the worst map/agent toggle buttons
        bestMapRadioButton.setVisible(false);
        worstMapRadioButton.setVisible(false);
        bestAgentRadioButton.setVisible(false);
        worstAgentRadioButton.setVisible(false);
        worstMapRadioButton.setVisible(false);
        bestMapRadioButton.setVisible(false);
        // set up columns for leaderboard (using getter methods for Player and LeaderboardEntry)
        rankColumnLeaderBoard.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRank()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlayerName()));


        // KDA calculation in leaderboard
        kdaColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getKda()));
        scoreColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPerformanceScore()));


        //winning streak tabular values
        usernameColWinningStreak.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        rankColWinningStreak.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRank()));
        maxStreakCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaxStreak()).asObject());
        minStreakCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMinStreak()).asObject());
        currentStreakCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCurrentStreak()).asObject());
        // Add hover listener to display games
        playersTableView.setRowFactory(tv -> {
            TableRow<Player> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
                Player hoveredPlayer = row.getItem();
                if (hoveredPlayer != null) {
                    displayPlayerGames(hoveredPlayer);
                }
            });
            return row;
        });
    }

    /**
     * This method is used for handling the "Load" menu item, when selected.
     * It opens the file chooser dialogue box in order to select the csv file, which contains the previously saved data
     * It is responsible for parsing the files and populating the game lists and array lists by checking that each username is considered only once
     * and the stats are recorded.
     */
    @FXML
    public void loadMenuItem() {
        FileChooser fileChooser = new FileChooser(); // create file chooser

        // when selecting a file, the file chooser will only show csv files
        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        // show the open dialog and get the selected file
        File file = fileChooser.showOpenDialog(stage);

        // if a file is selected, load the world and update GUI
        if (file != null) {
            try {
                // load the games from the file
                playerGames.addAll(Reader.readDataFromFile(file));
                for (Game game : playerGames) {
                    Player existingPlayer = game.getPlayer();

                    // Check for duplicates before adding
                    boolean isDuplicate = players.stream()
                            .anyMatch(player -> player.getUsername().equalsIgnoreCase(existingPlayer.getUsername()));

                    if (!isDuplicate) {
                        players.add(existingPlayer);
                    }
                }
                refreshCreateGameUsernameComboBox();
                createGameStatusLabel.setStyle("-fx-text-fill: green;");
                createGameStatusLabel.setText("Loaded games from " + file.getName());
            } catch (Exception e) {
                // shows error message if the file cannot be loaded
                createGameStatusLabel.setStyle("-fx-text-fill: red;");
                createGameStatusLabel.setText("Error loading game from file.");
            }
        } else {
            // shows error message if no file is selected
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            createGameStatusLabel.setText("Error loading game from file.");
        }
    }

    /**
     * Save the battle to existing file when the menu option is selected
     * The file chooser is opened to save the current game in a csv file (existing/new
     * This is used for writing the data in the writer class
     */
    private File savedFile = null; // class-level variable

    @FXML
    private Button savePlayerButton;

    @FXML
    private void saveMenuItem() {
        if (players.isEmpty()) {
            createGameStatusLabel.setText("No players available to save.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            whichMenuOptionChosen.setText("Select a menu option");
            saveGamePane.setVisible(false);
            return;
        }
        saveGamePane.setVisible(true);

        // Populate the ComboBox with player usernames
        savePlayerComboBox.getItems().clear();
        savePlayerComboBox.getItems().addAll(
                players.stream()
                        .map(Player::getUsername)
                        .toList()
        );

        // Add action listener to the Save button
        savePlayerButton.setOnAction(event -> {
            String selectedPlayer = savePlayerComboBox.getValue();
            if (selectedPlayer != null) {
                // Filter games for the selected player
                List<Game> selectedPlayerGames = playerGames.stream()
                        .filter(game -> game.getPlayer().getUsername().equals(selectedPlayer))
                        .toList();

                // Default file name
                String defaultFileName = selectedPlayer + "_games.csv";
                File defaultFile = new File(defaultFileName);

                if (defaultFile.exists()) {
                    // Overwrite the existing file
                    Writer.saveDataToFile(defaultFile.getAbsolutePath(), selectedPlayerGames);
                    createGameStatusLabel.setText("Games saved for player: " + selectedPlayer + " to " + defaultFile.getName());
                    createGameStatusLabel.setStyle("-fx-text-fill: green;");
                } else {
                    // Open FileChooser to let the user select the save location
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save As");
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
                    fileChooser.setInitialFileName(defaultFileName);

                    // Show the Save dialog
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null) {
                        // Save the games to the selected file
                        Writer.saveDataToFile(file.getAbsolutePath(), selectedPlayerGames);
                        createGameStatusLabel.setText("Games saved for player: " + selectedPlayer + " to " + file.getName());
                        createGameStatusLabel.setStyle("-fx-text-fill: green;");
                    } else {
                        createGameStatusLabel.setText("Save operation canceled.");
                        createGameStatusLabel.setStyle("-fx-text-fill: red;");
                    }
                }
            } else {
                createGameStatusLabel.setText("Please select a player to save.");
                createGameStatusLabel.setStyle("-fx-text-fill: red;");
            }
        });
    }

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private void handleDeleteMenuItem() {
        // Check if there are any players to delete
        if (players.isEmpty()) {
            createGameStatusLabel.setText("No players available to delete.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Create a dialog to select a player to delete
        ChoiceDialog<String> dialog = new ChoiceDialog<>(
                players.getFirst().getUsername(),
                players.stream().map(Player::getUsername).toList()
        );
        dialog.setTitle("Delete Player");
        dialog.setHeaderText("Select a player to delete");
        dialog.setContentText("Player:");

        // Show the dialog and get the result
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(selectedPlayer -> {
            // Find and remove the player
            players.removeIf(player -> player.getUsername().equals(selectedPlayer));
            playerGames.removeIf(game -> game.getPlayer().getUsername().equals(selectedPlayer));

            // Update the UI
            playersTableView.setItems(FXCollections.observableArrayList(players));
            createGameStatusLabel.setText("Player " + selectedPlayer + " deleted successfully.");
            createGameStatusLabel.setStyle("-fx-text-fill: green;");
        });
    }

    /**
     * A confirmation dialogue box is shown before closing the application
     * The user is prompted to confirm the action
     * Exits the application when the user confirms.
     */
    @FXML
    private void closeProgram() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //creates an alert confirmation dialogue box here
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Any unsaved changes will be lost.");

        //sets up the yes and no options here
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        //shows the dialogue box that waits for the input of the user
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        //If the user confirms YES, close the application
        if (result.isPresent() && result.get() == yesButton) {
            Platform.exit();
            System.exit(0);
        }
    }

    /**handles the radio button selection for various UI options
     * This method initially hides all the panes and then shows only the ones that are needed
     *
     */
    @FXML
    private void handleRadioSelect() {
        // hidden center view
        addPlayerPane.setVisible(false);
        addGamePane.setVisible(false);


        // Show the form only when "Create Player" radio button is selected
        if (createPlayerRadio.isSelected()) {
            addPlayerPane.setVisible(true);
        } else {
            addPlayerPane.setVisible(false); // Hide it if not selected
        }
        //Show the form only when "Create Game" radio button is selected
        if (createGameRadio.isSelected()) {
            addGamePane.setVisible(true);
        } else {
            addGamePane.setVisible(false); // Hide it if not selected
        }
        //Show the form only when "Player Statistics" radio button is selected
        if (statSearchRadio.isSelected()) {
            statSearchPane.setVisible(true);
        } else {
            statSearchPane.setVisible(false); // Hide it if not selected
        }
        //Show the form only when "Leaderboard" radio button is selected
        if (leaderBoardRadio.isSelected()) {
            leaderboardPane.setVisible(true);  // Make leaderboard visible
        } else {
            leaderboardPane.setVisible(false);  // Hide it if not selected
        }
        //Show the form only when "Best/Worst agent" radio button is selected
        if (bestWorstAgentRadio.isSelected()) {
            bestWorstAgentPane.setVisible(true);
        }
        else {
            bestWorstAgentPane.setVisible(false);
        }
        //Show the form only when "Best/Worst map" radio button is selected
        if (bestWorstMapRadio.isSelected()) {
            bestWorstMapPane.setVisible(true);
        }else{
            bestWorstMapPane.setVisible(false);
        }
        if (winningStreakRadio.isSelected()) {
            winningStreakPane.setVisible(true);
        }else {
            winningStreakPane.setVisible(false);
        }
    }

    /**
     * Handles the creation of a new player
     * This method is used for retrieving the username and the rank of the user
     * Adds the player to the list of players
     * Informs the user if the player addition/ deletion was successful
     */
    @FXML
    private void handleRadioButtonClick() {
        // Reset elements in addGamePane
        resetPaneElements(addGamePane);

        // Reset elements in addPlayerPane
        resetPaneElements(addPlayerPane);

        // Reset elements in existingPlayersPane
        resetPaneElements(existingPlayersPane);

        // Reset elements in bestWorstMapPane
        resetPaneElements(bestWorstMapPane);

        // Reset elements in statSearchPane
        resetPaneElements(statSearchPane);

        // Reset elements in leaderboardPane
        resetPaneElements(leaderboardPane);

        // Reset elements in bestWorstAgentPane
        resetPaneElements(bestWorstAgentPane);

        //resets elements in the winning streak pane
        resetPaneElements(winningStreakPane);
    }

    private void resetPaneElements(AnchorPane pane) {
        for (Node node : pane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear(); // Clear the text field
            } else if (node instanceof ComboBox) {
                ((ComboBox<?>) node).setValue(null); // Reset the combo box
            }
        }
    }


    @FXML
    private void handleCreatePlayer(ActionEvent event) {
        // Retrieve the username and rank from the text fields
        String username = usernameTextField.getText().trim();
        String rankInput = rankTextField.getText().trim().toUpperCase();

        // Validate the username
        if (username.length() < 3) {
            createGameStatusLabel.setText("Invalid username. Must be at least 3 characters.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Check if the username already exists
        boolean usernameExists = players.stream()
                .anyMatch(player -> player.getUsername().equalsIgnoreCase(username));
        if (usernameExists) {
            createGameStatusLabel.setText("Username already exists. Please choose a different username.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validate the rank
        Rank rank;
        try {
            rank = Rank.valueOf(rankInput); // Check if the rank exists in the Rank enum
        } catch (IllegalArgumentException e) {
            createGameStatusLabel.setText("Invalid rank. Please enter a valid rank.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Use the rank variable to create a Player object
        Player newPlayer = new Player(username, rank);
        players.add(newPlayer);

        refreshCreateGameUsernameComboBox();

        // Provide feedback to the user
        createGameStatusLabel.setText("Player created successfully!");
        createGameStatusLabel.setStyle("-fx-text-fill: green;");

        // Debug: Print the player details
        System.out.println("Player created: " + newPlayer);
    }

    @FXML
    private ListView<String> playerGamesListView;

    /**
     * Displays the games played by the selected player in the ListView
     * @param player The player whose games are to be displayed
     */
    private void displayPlayerGames(Player player) {
        List<String> gamesForPlayer = new ArrayList<>();
        for (Game game : playerGames) {
            if (game.getPlayer().equals(player)) {
                gamesForPlayer.add(String.format("Map: %s | Agent: %s | Mode: %s | K/D/A: %d/%d/%d | Result: %s",
                        game.getMap(), game.getAgent(), game.getGameMode(),
                        game.getKills(), game.getDeaths(), game.getAssists(),
                        game.getWonBoolean() ? "WON" : "LOST"));
            }
        }
        playerGamesListView.setItems(FXCollections.observableArrayList(gamesForPlayer));
    }

    /**
     * Handles the process of displaying the existing players in table view
     * It checks if the players list is empty and provides feedback
     * If the players are found then the table view will be populated with data
     */
    private void refreshCreateGameUsernameComboBox() {
        createGameUsernameComboBox.getItems().clear(); // Clear existing items
        createGameUsernameComboBox.getItems().addAll(
                players.stream()
                        .map(Player::getUsername)
                        .toList()
        ); // Add all usernames from the players list
    }

    /**
     * Handles the create game button when clicked
     * This method is used for collecting all the data needed for creating a new game
     */
    @FXML
    private void createGameButtonClicked() {
        String username = createGameUsernameComboBox.getValue().trim();

        if (username.isEmpty()) {
            createGameStatusLabel.setText("Please select a valid player.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            if (mapComboBox.getValue() == null ||
                    agentComboBox.getValue() == null ||
                    gameModeComboBox.getValue() == null) {
                createGameStatusLabel.setText("Please select a map, agent, and mode.");
                createGameStatusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            String selectedMap = mapComboBox.getValue();
            String selectedAgent = agentComboBox.getValue();
            String selectedMode = gameModeComboBox.getValue();
            int kills = Integer.parseInt(createGameKillsTextField.getText().trim());
            int deaths = Integer.parseInt(createGameDeathsTextField.getText().trim());
            int assists = Integer.parseInt(createGameAssistsTextField.getText().trim());

            // Find the player by username
            Player existingPlayer = players.stream()
                    .filter(player -> player.getUsername().equalsIgnoreCase(username))
                    .findFirst()
                    .orElse(null);

            if (existingPlayer == null) {
                createGameStatusLabel.setText("Player not found. Please create the player first.");
                createGameStatusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Create a new game object
            Game game = new Game(existingPlayer,
                    Maps.valueOf(selectedMap.toUpperCase()),
                    Gamemode.valueOf(selectedMode.toUpperCase()).name(),
                    createGameWinLossComboBox.getValue().equals("WIN"),
                    kills, deaths, assists,
                    Agents.valueOf(selectedAgent.toUpperCase())
            );
            playerGames.add(game);
            createGameStatusLabel.setText(String.format("Created %s game for %s on %s with K/D/A: %d/%d/%d (%s)",
                    game.getGameMode(), existingPlayer.getUsername(), game.getMap(),
                    game.getKills(), game.getDeaths(), game.getAssists(), createGameWinLossComboBox.getValue()));
            createGameStatusLabel.setStyle("-fx-text-fill: green;");
        } catch (NumberFormatException e) {
            createGameStatusLabel.setText("Please enter valid numbers for kills, deaths, and assists.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            createGameStatusLabel.setText("Something went wrong while creating the game.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Handles the process of displaying the existing players in table view
     * It checks if the players list is empty and provides feedback
     * If the players are found then the table view will be populated with data
     */
    @FXML
    private void handleExistingPlayers() {
        if (players.isEmpty()) {
            createGameStatusLabel.setText("No players found. Please create a player first.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Get the selected sort order
        String sortOrder = sortOrderComboBox.getValue();

        // Sort players based on the selected order
        if ("Rank Descending Order".equals(sortOrder)) {
            players.sort(Comparator.comparing(Player::getRank).reversed());
        } else if ("Rank Ascending Order".equals(sortOrder)) {
            players.sort(Comparator.comparing(Player::getRank));
        }

        // Populate the TableView with the sorted players list
        playersTableView.setItems(players);

        // Update the status label
        createGameStatusLabel.setText("Players loaded successfully!");
        createGameStatusLabel.setStyle("-fx-text-fill: green;");
    }


    /**

     Loads the games into the playerGames list and updates the players list
     This method is used for loading the games from a file
     It checks if the games are null or empty and provides feedback
     If the games are found then the table view will be populated with data
     */

    public  void loadGames(List<Game> games) {
        if (games == null || games.isEmpty()) {
            System.out.println("No games to load.");
            return;
        }
        Set<Player> uniquePlayers = new HashSet<>();
        for (Game game : games) {
            Player player = game.getPlayer();
            uniquePlayers.add(player);
            player.addGame(game); // Associate the game with the player
        }
        playerGames.addAll(games); // Ensure playerGames is updated
        players.setAll(uniquePlayers); // Update the ObservableList with unique players
        refreshCreateGameUsernameComboBox(); // Refresh the ComboBox
    }

    /**
     * Handles the logic to determine and display the best and the worst map of a specific player
     * User needs to enter their username to retrieve the data of the specific player
     * Filters to display only for the worst or the best map at once.
     * Displays the result that is in accordance with the radio button that is selected
     */
    @FXML
    private void handleBestWorstMapOfPlayer() {
        String username = playerUsernameTextField.getText().trim();
        bestMapRadioButton.setVisible(false);
        worstMapRadioButton.setVisible(false);

        if (username.isEmpty()) {
            createGameStatusLabel.setText("Please enter a username.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Player selectedPlayer = null;
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                selectedPlayer = player;
                break;
            }
        }

        if (selectedPlayer == null) {
            createGameStatusLabel.setText("Player not found.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Filter games for the selected player
        List<Game> playerGamesForUser = new ArrayList<>();
        for (Game game : playerGames) {
            if (game.getPlayer().equals(selectedPlayer)) {
                playerGamesForUser.add(game);
            }
        }
        if (playerGamesForUser.isEmpty()) {
            createGameStatusLabel.setText("No games found for the player.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Calculate best or worst map based on the selected radio button
        Map<Maps, List<Game>> mapGameList = new HashMap<>();
        for (Game game : playerGamesForUser) {
            mapGameList.computeIfAbsent(game.getMap(), k -> new ArrayList<>()).add(game);
        }

        Map<Maps, Double> mapKdaAverages = new HashMap<>();
        for (Map.Entry<Maps, List<Game>> entry : mapGameList.entrySet()) {
            Maps map = entry.getKey();
            List<Game> gamesOnMap = entry.getValue();

            double totalKda = 0;
            for (Game game : gamesOnMap) {
                int kills = game.getKills();
                int assists = game.getAssists();
                int deaths = game.getDeaths();
                double kda = (kills + assists) / Math.max(1.0, deaths);
                totalKda += kda;
            }

            double averageKda = totalKda / gamesOnMap.size();
            mapKdaAverages.put(map, averageKda);
        }

        // Determine best and worst map based on KDA
        Maps bestMap = null;
        Maps worstMap = null;
        double bestKda = Double.NEGATIVE_INFINITY;
        double worstKda = Double.POSITIVE_INFINITY;

        for (Map.Entry<Maps, Double> entry : mapKdaAverages.entrySet()) {
            Maps map = entry.getKey();
            double kda = entry.getValue();

            if (kda > bestKda) {
                bestKda = kda;
                bestMap = map;
            }
            if (kda < worstKda) {
                worstKda = kda;
                worstMap = map;
            }
        }

        if (mapKdaAverages.isEmpty()) {
            createGameStatusLabel.setText("No valid map data found.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Show the radio buttons if the username is valid
        bestMapRadioButton.setVisible(true);
        worstMapRadioButton.setVisible(true);
        createGameStatusLabel.setText("Select Best or Worst Map.");
        createGameStatusLabel.setStyle("-fx-text-fill: green;");

        Maps finalBestMap = bestMap;
        double finalBestKda = bestKda;
        bestMapRadioButton.setOnAction(event -> {
            if (finalBestMap != null && bestMapRadioButton.isSelected()) {
                createGameStatusLabel.setText("Best Map: " + finalBestMap + " with average KDA of " + String.format("%.2f", finalBestKda));
                createGameStatusLabel.setStyle("-fx-text-fill: green;");
            }
        });

        Maps finalWorstMap = worstMap;
        double finalWorstKda = worstKda;
        worstMapRadioButton.setOnAction(event -> {
            if (finalWorstMap != null && worstMapRadioButton.isSelected()) {
                createGameStatusLabel.setText("Worst Map: " + finalWorstMap + " with average KDA of " + String.format("%.2f", finalWorstKda));
                createGameStatusLabel.setStyle("-fx-text-fill: red;");
            }
        });
    }

    /**
     * Handles the logic for displaying the best/worst agent
     * Similar to the best/worst map this also takes the username of the player to retrieve all the player data
     */
    @FXML
    private void handleBestWorstAgentOfPlayer() {
        //enter the username of the player
        String username = playerUsernameTextFieldBWAgent.getText().trim();
        worstAgentRadioButton.setVisible(false);
        bestAgentRadioButton.setVisible(false);

        //validate the username
        if (username.isEmpty()) {
            createGameStatusLabel.setText("Please enter a username.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        //search for the player
        Player selectedPlayer = null;
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                selectedPlayer = player;
                break;
            }
        }
        //handles the case where the player is not found
        if (selectedPlayer == null) {
            createGameStatusLabel.setText("Player not found.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // filter games for the selected player
        List<Game> playerGamesForUser = new ArrayList<>();
        for (Game game : playerGames) {
            if (game.getPlayer().equals(selectedPlayer)) {
                playerGamesForUser.add(game);
            }
        }
        if (playerGamesForUser.isEmpty()) {
            createGameStatusLabel.setText("No games found for the player.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Group games by agent
        Map<Agents, List<Game>> agentGameList = new HashMap<>();
        for (Game game : playerGamesForUser) {
            agentGameList.computeIfAbsent(game.getAgent(), k -> new ArrayList<>()).add(game);
        }

        // Calculate average KDA for each agent
        Map<Agents, Double> agentKdaAverages = new HashMap<>();
        for (Map.Entry<Agents, List<Game>> entry : agentGameList.entrySet()) {
            Agents agent = entry.getKey();
            List<Game> games = entry.getValue();

            double totalKda = 0;
            for (Game game : games) {
                int kills = game.getKills();
                int assists = game.getAssists();
                int deaths = game.getDeaths();
                double kda = (kills + assists) / Math.max(1.0, deaths);
                totalKda += kda;
            }

            double averageKda = totalKda / games.size();
            agentKdaAverages.put(agent, averageKda);
        }

        // Determine best and worst agent based on KDA
        Agents bestAgent = null;
        Agents worstAgent = null;
        double bestKda = Double.NEGATIVE_INFINITY;
        double worstKda = Double.POSITIVE_INFINITY;

        for (Map.Entry<Agents, Double> entry : agentKdaAverages.entrySet()) {
            Agents agent = entry.getKey();
            double kda = entry.getValue();

            if (kda > bestKda) {
                bestKda = kda;
                bestAgent = agent;
            }
            if (kda < worstKda) {
                worstKda = kda;
                worstAgent = agent;
            }
        }

        if (agentKdaAverages.isEmpty()) {
            createGameStatusLabel.setText("No valid agent data found.");
            createGameStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        bestAgentRadioButton.setVisible(true);
        worstAgentRadioButton.setVisible(true);
        createGameStatusLabel.setText("Select Best or Worst Agent.");
        createGameStatusLabel.setStyle("-fx-text-fill: green;");

        Agents finalBestAgent = bestAgent;
        double finalBestKda = bestKda;
        bestAgentRadioButton.setOnAction(event -> {
            if (finalBestAgent != null && bestAgentRadioButton.isSelected()) {
                createGameStatusLabel.setText("Best Agent: " + finalBestAgent + " with average KDA of " + String.format("%.2f", finalBestKda));
                createGameStatusLabel.setStyle("-fx-text-fill: green;");
            }
        });

        double finalWorstKda = worstKda;
        Agents finalWorstAgent = worstAgent;
        worstAgentRadioButton.setOnAction(event -> {
            if (finalWorstAgent != null && worstAgentRadioButton.isSelected()) {
                createGameStatusLabel.setText("Worst Agent: " + finalWorstAgent + " with average KDA of " + String.format("%.2f", finalWorstKda));
                createGameStatusLabel.setStyle("-fx-text-fill: red;");
            }
        });
    }


    /**
     * The method which is used for handling the stats search and displaying the stats of the player
     * Calculates and displays all the games played by the player
     */
    @FXML
    private void handleStatSearch() {
        // 1. username entered
        String username = statSearchUsernameTextField.getText().trim();

        // 2. Validate input
        if (username.length() < 3) {
            statSearchTextArea.setText("Please enter a valid username (at least 3 characters).");
            return;
        }

        // 3. Search for the player
        Player selectedPlayer = null;
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                selectedPlayer = player;
                break;
            }
        }

        if (selectedPlayer == null) {
            statSearchTextArea.setText("Player not found.");
            return;
        }

        int totalKills = 0;
        int totalDeaths = 0;
        int totalAssists = 0;
        int totalWins = 0;
        int totalLosses = 0;
        for (Game game : playerGames) {
            if (game.getPlayer().equals(selectedPlayer)) {
                totalKills += game.getKills();
                totalDeaths += game.getDeaths();
                totalAssists += game.getAssists();
                if (game.getWonBoolean()) {
                    totalWins++;
                } else {
                    totalLosses++;
                }
            }
        }

        // 5. Display the stats
        String statsMessage = String.format(
                "Player: %s\nTotal Kills: %d\nTotal Deaths: %d\nTotal Assists: %d\n" +
                        "Total Wins: %d\nTotal Losses: %d\n",
                selectedPlayer.getUsername(), totalKills, totalDeaths, totalAssists, totalWins, totalLosses
        );

        statSearchTextArea.setText(statsMessage);
    }

    /**
     * Leader board functionality that is used for displaying the players in order of their performance score
     * @param event
     */
    @FXML
    void updateLeaderboard(ActionEvent event) {
        //a list of leaderboard entries, from the highest to the least performance score
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();


        // Iterate through the players list
        for (Player player : players) {
            int totalKills = 0;
            int totalDeaths = 0;
            int totalAssists = 0;
            int totalWins = 0;
            int totalGames = 0;


            // Accumulate all stats across all games for this player
            for (Game game : playerGames) {
                if (game.getPlayer().equals(player)) {
                    totalKills += game.getKills();
                    totalDeaths += game.getDeaths();
                    totalAssists += game.getAssists();
                    if (game.getWonBoolean()) {
                        totalWins++;
                    }
                    totalGames++;
                }
            }

            if (totalGames == 0) continue; // Skip if player has no games

            // Calculate KDA and performance score based on cumulative stats
            double kda = calculateKDA(totalKills, totalDeaths, totalAssists);
            boolean playerWonMoreThanLost = totalWins > (totalGames - totalWins);
            double performanceScore = calculatePerformanceScore(kda, playerWonMoreThanLost);

            String rankString = player.getRank().name();

            // Add one LeaderboardEntry per player
            leaderboardEntries.add(new LeaderboardEntry(rankString, player.getUsername(), kda, performanceScore));
        }

        // Sort by performance score descending
        leaderboardEntries.sort(Comparator.comparingDouble(LeaderboardEntry::getPerformanceScore).reversed());

        // update leaderboard TableView
        ObservableList<LeaderboardEntry> leaderboardData = FXCollections.observableArrayList(leaderboardEntries);
        leaderboardTable.setItems(leaderboardData);
    }

    /**
     * Helper function to calculate KDA
     * @param kills kills of the game
     * @param deaths deaths of the game
     * @param assists assists of the game
     * @return KDA ratio
     */
    private double calculateKDA(int kills, int deaths, int assists) {
        return (kills + assists) / (double) Math.max(1, deaths); // KDA formula
    }

    /**
     * helper function to determine the performance score
     * @param kda computed kda from the previous function
     * @param playerWon the number of times the player won
     * @return performance score
     */
    private double calculatePerformanceScore(double kda, boolean playerWon) {
        double winBonus = playerWon ? 1.0 : 0.0;
        return (kda * 0.9) + (winBonus * 0.1); // Performance score formula
    }

    /**
     * Winning streak stats for all the players that exist in the game
     * Iterates through the players and the games played in order to analyze their win streak
     * The streak starts once a game has been won, and continues as long as there is a continuous sequence of games that have been won
     * Maximum streak: all time longest win streak of the player
     * Minimum streak: all time shortest win streak of the player
     *
     * If no games are played/no wins then all the streak values are 0.
     * the table is sorted with in the descending order based on the max streak.
     * @param event action event that is triggered.
     */
    @FXML
    void updateWinningStreak(ActionEvent event) {
        List<winningStreak> winningStreakEntries = new ArrayList<>();

        for (Player player : players) {
            int currentStreak = 0;
            int maxStreak = 0;
            int minStreak = Integer.MAX_VALUE;
            int ongoingStreak = 0;
            boolean isCurrentStreakActive = false;
            boolean hasGames = false;
            boolean hasAtLeastOneWin = false;

            //iterates through all the games that are played.
            for (Game game : playerGames) {
                if (game.getPlayer().equals(player)) {
                    hasGames = true;

                    if (game.getWonBoolean()) {
                        hasAtLeastOneWin = true;

                        if (isCurrentStreakActive) {
                            currentStreak++; //continues the current streak
                        } else {
                            currentStreak = 1;
                            isCurrentStreakActive = true;
                        }
                    } else {
                        if (isCurrentStreakActive) {
                            //end of the win streak, updating the max and the min fields
                            maxStreak = Math.max(maxStreak, currentStreak);
                            minStreak = Math.min(minStreak, currentStreak);
                            currentStreak = 0;
                            isCurrentStreakActive = false;
                        }
                    }
                }
            }

            // if last game was a win, check the last streak for max/min
            if (isCurrentStreakActive) {
                maxStreak = Math.max(maxStreak, currentStreak);
                minStreak = Math.min(minStreak, currentStreak);
                ongoingStreak = currentStreak;
            }

            // Handle case: no games at all or no wins at all
            if (!hasGames || !hasAtLeastOneWin) {
                minStreak = 0;
                maxStreak = 0;
                ongoingStreak = 0;
            }

            //creating a new streak entry for the player
            winningStreakEntries.add(
                    new winningStreak(player.getUsername(), player.getRank().name(), ongoingStreak, maxStreak, minStreak)
            );
        }

        // sort by max streak descending
        winningStreakEntries.sort(Comparator.comparingInt(winningStreak::getMaxStreak).reversed());

        //convert the list to observable list and update the table view.
        ObservableList<winningStreak> winningStreakData = FXCollections.observableArrayList(winningStreakEntries);
        winningStreakTable.setItems(winningStreakData);
    }

    @FXML
    private MenuItem aboutMenuItem;

    /**Handles the about new menu item
     * Displays the information that describes the state about the application and also the version and credits
     */
    @FXML
    private void handleAboutMenuItem() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Valorant Game Tracker");
        alert.setContentText("""
               This application enables comprehensive tracking of Valorant game statistics by allowing users to manage multiple player profiles, load historical match data from saved files, and persist newly added games for future analysis.
               ------------
               Developed by: Jasnoor Kaur, Suyog Bhat and Apphia Ferrer
               Emails: jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
               Version: 1.0
               Git Repository for JavaFX: https://csgit.ucalgary.ca/suyog.bhat/valoranttrackergui
               \s""");

        alert.showAndWait();
    }

}