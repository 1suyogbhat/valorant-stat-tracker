package Tracker;
import Tracker.Player.Player;
import Tracker.enums.*;
import Tracker.util.Reader;
import Tracker.util.colorsExtra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */
public class Menu {
    // Outer hashmap:  HashMap<String, HashMap<String, Integer>> (used  to store the stats of the player, where the outer key is the player's name.)
    //inner hashmap: HashMap<String, Integer> ( key in this inner map represents a specific type of statistic for the player (e.g., "kills", "deaths", "KDA", etc.))
    static HashMap<String, HashMap<String, Integer>> playerStats = new HashMap<String, HashMap<String, Integer>>();

    //creating a scanner class to obtain input from the user
    private static final Scanner scanner=new Scanner(System.in);

    //list of options in our tracking menu will be displayed to the user (stored using the array list and every option that perform is added to the array.)
    private static final ArrayList<String> options=new ArrayList<>();
    //integer that tracks the index of the menu options that would be performed by the user
    private static int optionIndex = 0;

    //hashmap that is created to store any player specific data (like usernames and ranks)
    private final static HashMap<String,String> playerData = new HashMap<>();
    //making an array list of all the menu options
    //static initializer to display the entire list of options that are present in the menu
    static {
        options.add("Exit"); //array list, first option added is at index 0 (corresponds to the exit operation)
        //any other functionlity that would be incorported in the tracker is appended to the menu.
        //storing information based on which/who you would perform tracking operations on.
        options.add("Enter a new player in the game");
        options.add("Printing all the existing players in the game (usernames, rank)");
        options.add("Printing the information about a specific player (username and rank) and the total number of games played ");
        options.add("Total number of games played by the player");
        options.add("Total number of wins played by the player");
        options.add("Total number of losses played by the player");
        options.add("Create a new game");
        options.add("Displays all the saved games of the player");
        options.add("Displaying the stats of the best played game");
        options.add("Displaying the stats of the worst played game");
        options.add("Display the best map of the player");
        options.add("Display the worst map of the player");
        options.add("Display the best agent of the player");
        options.add("Display the worst agent of the player");
        options.add("Display the win rate based on the agent");
        options.add("Display the win rate based on the map");
        options.add("Save data to a File");
        options.add("Read File from Username");
        options.add("Winning streak tracker");
        options.add("Recommended agents for the best performance");
    }

    //a message that is introductory, displayed to the user whenever they would proceed to make a choice from the menu.
    private static String menuMessage= colorsExtra.CYAN + """
        ==============================================
                    🎮 GAME TRACKER MENU 🎮
        ==============================================
        """ + colorsExtra.RESET;
    static { //static initializer that is going to pin the original message (and dynamically generates the meny string from the list of options)
        //this block is executed when the class is loaded
        StringBuilder builder=new StringBuilder(menuMessage); //used to concatenate the strings
        for (int i = 0; i < options.size(); i++)
        {
            //formatting the list as a numbered option.
            builder.append(String.format("\t%d. %s\n", i, options.get(i)));
        }
        menuMessage=builder.toString();
        }
    private static final String PLAYER_FORMAT="%-20s\t%-15s\n";
    private static final String PLAYER_HEADER= String.format("%-20s\t%-15s%n","USERNAME", "RANK");
    private static String PLAYER_SEPARATOR = "";
    static{
        for (int i = 0; i < PLAYER_HEADER.length(); i++) {
            PLAYER_SEPARATOR+= "-";
        }
    }

    private static Data Data = new Data();

    public static void setPlayerStats(String username, String agent, double winRate) {
        if (playerStats == null) {
            playerStats = new HashMap<>();
        }
        playerStats.computeIfAbsent(username, k -> new HashMap<>()).put(agent, (int) winRate);
    }

    /**
     * menuLoop() function [the main function in the program whch calls all the other functions]
     * method that is used to control all the menu and the options (tracking functionality)
     * Accepts input from the user (integer value and performs the tracking opertion based on what was requested from the menu)
      */
    public static void menuLoop() {
        System.out.print(menuMessage); // prints the main menu message with all the options.

        // while loop that iterates through the menu until the user decides to exit.
        while (true) {
            System.out.print("Enter your choice: "); //message that is displayed to the users to enter their choice of tracking operation based on the menu.
            String input = scanner.nextLine(); //reads the input as a string

            //checks if the input is a valid integer, since it is being read as a string, you need to ensure that it corresponds to a valid numerical value within the range of all the switch cases.
            if (!isValidInteger(input)) {
                System.out.println("Invalid input! Please enter a valid number.");
                continue; // skipping the remaining part of the iteration and jumps to the next one
            }
            int menuOption = Integer.parseInt(input); //converting the input string to an integer

            //in case of a non-zero valid option (needs to be within the limits of the array which contains the menu options)
            if (menuOption > 0 && menuOption < options.size()) {
                System.out.printf("Option selected: %d. %s%n", menuOption, options.get(menuOption)); //message confirming that an option has been selected.
                System.out.print("Press Enter to continue...");
                scanner.nextLine(); //press enter key
            }

            //Using the switch statement to determine what happens when an option is selected by the user.
            switch (menuOption) {
                case 0:
                    // If option 0 is selected, break the loop and exit the program.
                    System.out.println("Thank you for using our tracker! Exiting...");
                    return; // Exit the loop and the method.
                case 1:
                    menuInputPlayerDetails();
                    break;
                case 2:
                    menuAllPlayers();
                    break;
                case 3:
                    menuPlayerInformation();
                    break;
                case 4:
                    menuTotalGamesPlayed();
                    break;
                case 5: //checks if the player has won any past games
                    menuTotalGamesWon();
                    break;
                case 6: //checks if the player has lost any past games
                   menuTotalGamesLost();
                    break;
                case 7:
                    menuCreateNewGame(); //calls the method for creating a new game
                    break;
                case 8:
                    menuDisplaySavedGames();
                    break;
                case 9:
                    menuBestGame();
                    break;
                case 10:
                    menuWorstGame();
                    break;
                case 11:
                    menuBestMap();
                    break;
                case 12:
                    menuWorstMap();
                    break;
                case 13:
                    menuBestAgent();
                    break;
                case 14:
                    menuWorstAgent();
                    break;
                case 15:
                    menuAgentWinRate();
                    break;
                case 16:
                    menuMapWinRate();
                    break;
                case 17:
                    Data.saveDataToFile();
                    break;
                case 18:
                    Reader.readDataFromFile();
                    break;
                default: //default case is executed when the value entered for testing (when prompted to select an option) is beyond the case bounds
                    System.out.printf(colorsExtra.RED + "Unrecognized %s option %n", menuOption + colorsExtra.RESET);
                    break;
            }
            // Continue to ask for input until user exits
            if (menuOption != 0) { //the condition continues to iterate as long as the user does not enter 0 (to exit the tracking application)
                System.out.print("Press Enter to continue...");
                scanner.nextLine(); // This ensures it waits for the next key press after any valid option
                System.out.println(menuMessage); // prints the menu message, as long as the if condition evaluates to true
            }
        }
    }


    // helper method to check if input is a valid integer (string input for a numerical value (checked if it's an integer value)
    private static boolean isValidInteger(String input) {
        if (input == null || input.isEmpty()) {
            return false; // empty input is invalid
        }

        // check if all characters are digits
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false; // return false if any character is not a digit
            }
        }

        return true; // all characters are digits, valid integer
    }

    /**
     * Prompts the user to enter a new player's details and stores them.
     * The user is asked to provide a username and rank. If a player with the same username already exists,
     * the user will be prompted to enter a new username until a unique one is provided.
     * Once the player is successfully stored, a confirmation message is displayed.
     */
    private static void menuInputPlayerDetails() {
        boolean storePlayer;
        do {
            System.out.println(colorsExtra.BLUE + "======PLAYER DETAILS======" + colorsExtra.RESET);
            String username= menuUsername();
            Rank rank = menuRank();
            storePlayer = Data.storeNewPlayer(username,rank);
            if (!storePlayer) {
                System.out.println("Player with the same username exists already.\nEnter a NEW player user name to be tracked.");
            }
        }
        while(!storePlayer);
        System.out.println(colorsExtra.GREEN + "Stored a new player successfully!" + colorsExtra.RESET);
    }

//    /**
//     * Helper function validRank() for menuUsernameAndRank()
//     * Checks for all the valid rank's in valorant
//     * @param scanner: The paramter is entered by the user (which is cross-checked with the list of valid rank names
//     * @return string: the rank in valorant (based on the input of the user)
//     */
//   private static String validRank(Scanner scanner) {
//      // List of valid main ranks
//      String[] validRanks = {"UNRANKED", "IRON", "BRONZE", "SILVER", "GOLD", "PLATINUM", "DIAMOND", "ASCENDANT", "IMMORTAL", "RADIANT"};
//
//        while (true) {
//            System.out.print("Enter RANK (or leave empty if UNRANKED): ");
//            String rank = scanner.nextLine().trim().toUpperCase();
//
//            // Allow empty input (no rank)
//            if (rank.isEmpty()) {
//                return "";
//            }
//
//            // Check if rank starts with a valid main rank
//            for (String validRank : validRanks) {
//                if (rank.startsWith(validRank)) {
//                    String suffix = rank.substring(validRank.length()).trim();
//
//                    // If no number is provided, return just the rank name
//                    if (suffix.isEmpty()) {
//                        return validRank;
//                    }
//
//                    // Ensure suffix contains only digits (1, 2, or 3)
//                    if (suffix.equals("1") || suffix.equals("2") || suffix.equals("3")) {
//                        return validRank + " " + suffix; // formats as "BRONZE 1"
//                    }
//                }
//            }
//            System.out.println(colorsExtra.RED + "Invalid rank. Please enter a valid rank." + colorsExtra.RESET);
//        }
//    }


    /**
     * function that allows the user to enter their username and also their rank
     * checks for a valid username (must atleast have 3 charcters and the rank is validated using the validRank() method
     */
    public static String menuUsername() {
        Scanner scanner = new Scanner(System.in);
        optionIndex = 1; // Option 1/case 1 in the menu

        String username;

        while (true) {
            System.out.print("Enter player USERNAME (must be at least 3 characters): ");
            username = scanner.nextLine().trim();

            // Check if username is empty
            if (username.isEmpty()) {
                System.out.println(colorsExtra.RED + "Invalid username. Username cannot be empty." + colorsExtra.RESET);
                continue;
            }

            // Check length requirements
            if (username.length() < 3 || username.length() > 20) {
                System.out.println(colorsExtra.RED + "Invalid username. Username must be between 3 and 20 characters long." + colorsExtra.RESET);
                continue;
            }

            // Ensure it contains at least one letter
            if (!username.matches(".*[a-zA-Z].*")) {
                System.out.println(colorsExtra.RED + "Invalid username. Username must contain at least one letter." + colorsExtra.RESET);
                continue;
            }

            // Allow only letters, numbers, and $
            if (!username.matches("[a-zA-Z0-9$]+")) {
                System.out.println(colorsExtra.RED + "Invalid username. Only letters, numbers, and '$' are allowed." + colorsExtra.RESET);
                continue;
            }

            // If we reach this point, the username is valid
            break;
        }

        addPlayerData(username); // Store the username
        System.out.println(colorsExtra.GREEN + "Username: " + colorsExtra.PURPLE + username + colorsExtra.RESET + colorsExtra.GREEN + " has been saved successfully." + colorsExtra.RESET);

        return username;
    }

    /**
     * function that allows the user to enter their rank
     * checks for a valid rank (using the validRank() method)
     */
    public static Rank menuRank() {
        System.out.println(colorsExtra.BLUE + "======RANK DETAILS======" + colorsExtra.RESET);
        Rank[] rank = Rank.values();
        int count = 0;
        for (Rank value : rank) {
            System.out.printf("%s | ", value.name());
            count++;
            if (count % 4 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.print("Enter player RANK (Press ENTER if none): ");
        String rankInput = scanner.nextLine().trim().toUpperCase().replace(" ", "").replace("_", "");

        Rank playerRank;
        try {
            playerRank = Rank.valueOf(rankInput); // Convert user input to a valid Rank enum
            System.out.println(colorsExtra.GREEN + "Rank: " + colorsExtra.PURPLE + playerRank + colorsExtra.RESET + colorsExtra.GREEN + " has been saved successfully." + colorsExtra.RESET);
        } catch (IllegalArgumentException e) {
            playerRank = Rank.UNRANKED; // Default rank if invalid input
            System.out.println("Rank set to: " + colorsExtra.PURPLE + "UNRANKED." + colorsExtra.RESET);
        }

        addPlayerData(playerRank); // Save the rank as an enum
        return playerRank;
    }

    /**
     * Stores the rank of a player.
     * The player's rank is saved in the data storage, and a confirmation message is displayed.
     */
    private static void addPlayerData(Rank rank) {
        // Store the player's rank (Modify based on your data storage structure)
        System.out.println("Player's rank stored as: " + colorsExtra.PURPLE + rank + colorsExtra.RESET);
    }

    /**
     * Displays a list of all players with their usernames and ranks.
     * The players are retrieved from the data and printed in a formatted table.
     */
    private static void menuAllPlayers() {
        System.out.println(PLAYER_HEADER);
        System.out.println(PLAYER_SEPARATOR);
        for(Player player: Data.getAllPlayers()){
            System.out.printf(PLAYER_FORMAT,player.getUsername(), player.getRank());
        }
    }

    /**
     * prints the total games of the player
     */
    public static int menuTotalGamesPlayed() {
        System.out.print("Enter the USERNAME to display details: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println(colorsExtra.RED + "Invalid input. Username cannot be empty." + colorsExtra.RESET);
            return -1;
        }

        int totalGamesPlayed = Data.getAndUpdateTotalGamesPlayed(username, false); // Retrieve only

        if (totalGamesPlayed == -1) {
            System.out.println("Player not found! Make sure the username is correct.");
            return -1;
        }

        System.out.println(colorsExtra.PURPLE + username + colorsExtra.RESET + colorsExtra.GREEN + " has played " + colorsExtra.RESET + colorsExtra.PURPLE + totalGamesPlayed + colorsExtra.RESET + colorsExtra.GREEN + " games!" + colorsExtra.RESET);
        return totalGamesPlayed;
    }


    /**
     * Displays information about a specific player based on their username.
     * First, it asks the user to enter a username and removes any extra spaces.
     * If the input is empty, it shows an error message and stops.
     * Then, it searches for the player in the stored data.
     * If the player is found, their details are displayed.
     * If the player does not exist, it shows an error message and asks the user to try again.
     */
    public static void menuPlayerInformation() {
        System.out.print("Enter the USERNAME to display details: ");
        String username = scanner.nextLine().trim(); // Reads user input properly
        if (username.isEmpty()) {
            System.out.println(colorsExtra.RED + "Invalid input. Username cannot be empty." + colorsExtra.RESET);
            return;
        }
        boolean found = Data.displayPlayerInfo(username);
        if (!found) {
            System.out.println(colorsExtra.RED + "Invalid input. Username does not exist." + colorsExtra.RESET);
        }
    }

    /**
     * Displays the total number of games won by a specific player.
     * First, it asks the user to enter a username and removes any extra spaces.
     * If the username does not exist in the data, it shows an error message and stops.
     * If the player exists, it retrieves their total wins and displays the count.
     */
    public static void menuTotalGamesWon() {
        System.out.print("Enter the USERNAME to display total wins: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println("Player not found. Please enter a valid username.");
            return;
        }

        int totalWins = Data.getTotalWins(username);
        System.out.println(colorsExtra.PURPLE + username + colorsExtra.RESET + colorsExtra.GREEN + " has won " + colorsExtra.RESET + colorsExtra.PURPLE + totalWins + colorsExtra.RESET + colorsExtra.GREEN + " games." + colorsExtra.RESET);
    }

    /**
     * Displays the total number of games lost by a specific player.
     * First, it asks the user to enter a username and removes any extra spaces.
     * If the username does not exist in the data, it shows an error message and stops.
     * If the player exists, it retrieves their total losses and displays the count.
     */
    public static void menuTotalGamesLost() {
        System.out.print("Enter the USERNAME to display total losses: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println("Player not found. Please enter a valid username.");
            return;
        }

        int totalLosses = Data.getTotalLosses(username);
        System.out.println(username + " has lost " + totalLosses + " games.");
    }

    /**
     * handles the process of creating a new game and recording stats
     */
    public static void menuCreateNewGame() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter player USERNAME: ");
        String username = sc.nextLine().trim(); //trims any white spaces that could be accidently entered by the player

        if (username.isEmpty()) {
            System.out.println("Invalid username. Enter a valid username.");
            return;
        }

        // Check if the player exists before proceeding
        if (!Data.checkExistPlayer(username)) {
            System.out.println("Player not found. Please add the player first.");
            return;
        }

        Gamemode gamemodePlayed = null;
        boolean validMode = false;

        // Validate game mode input with option to display all game modes
        while (!validMode) {
            System.out.println(colorsExtra.BLUE + "======GAMEMODES======" + colorsExtra.RESET);
            Gamemode[] gamemodes = Gamemode.values();
            for (int i = 0; i < gamemodes.length; i++) {
                System.out.printf("%d. %s%n", i + 1, gamemodes[i].name());
            }
            System.out.print("Enter name of your GAMEMODE: ");
            String gameModeInput = sc.nextLine().trim().toUpperCase();

            try {
                gamemodePlayed = Gamemode.valueOf(gameModeInput);
                validMode = true;
            } catch (IllegalArgumentException e) {
                System.out.println(colorsExtra.RED + "Invalid game mode. Please enter a valid one." + colorsExtra.RESET);
            }

        }

        int kills = -1, deaths = -1, assists = -1;
        // Get valid kill count
        while (kills < 0) {
            System.out.print("Enter number of KILLS: ");
            if (sc.hasNextInt()) {
                kills = sc.nextInt();
                if (kills < 0) System.out.println(colorsExtra.RED + "Invalid input, kills must be positive." + colorsExtra.RESET);
            } else {
                System.out.println(colorsExtra.RED + "Invalid input, please enter a number." + colorsExtra.RESET);
                sc.next();
            }
        }

        // Get valid death count
        while (deaths < 0) {
            System.out.print("Enter number of DEATHS: ");
            if (sc.hasNextInt()) {
                deaths = sc.nextInt();
                if (deaths < 0) System.out.println("Invalid input, deaths must be positive.");
            } else {
                System.out.println(colorsExtra.RED + "Invalid input, please enter a number." + colorsExtra.RESET);
                sc.next();
            }
        }

        // Get valid assist count
        while (assists < 0) {
            System.out.print("Enter number of ASSISTS: ");
            if (sc.hasNextInt()) {
                assists = sc.nextInt();
                if (assists < 0) System.out.println("Invalid input, assists must be positive.");
            } else {
                System.out.println(colorsExtra.RED + "Invalid input, please enter a number." + colorsExtra.RESET);
                sc.next();
            }
        }

        sc.nextLine(); // Consume newline

        Maps mapPlayed = null;
        boolean validMap = false;

        // Validate map input with option to display all maps
        while (!validMap) {
            System.out.println(colorsExtra.BLUE + "======MAPS======" + colorsExtra.RESET);
            Maps[] maps = Maps.values();
            for (int i = 0; i < maps.length; i++) {
                System.out.printf("%d. %s%n", i + 1, maps[i].name());
            }
            System.out.print("Enter number of the MAP played: ");
            String mapInput = sc.nextLine().trim().toUpperCase();

            try {
                mapPlayed = Maps.valueOf(mapInput);
                validMap = true;
            } catch (IllegalArgumentException e) {
                System.out.println(colorsExtra.RED + "Invalid map. Please enter a valid one." + colorsExtra.RESET);
            }
        }

        // List of valid agents
        Agents agentPlayed = null;
        boolean validAgent = false;

        // Validate agent input with option to display all agents
        while (!validAgent) {
            System.out.println(colorsExtra.BLUE + "======AGENTS======" + colorsExtra.RESET);
            Agents[] agents = Agents.values();
            int count = 0;
            for (Agents agent : agents) {
                System.out.printf("%s | ", agent.name());
                count++;
                if (count % 4 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.print("Enter the name of the AGENT played: ");
            String agentInput = sc.nextLine().trim().toUpperCase();

            try {
                agentPlayed = Agents.valueOf(agentInput);
                validAgent = true;
            } catch (IllegalArgumentException e) {
                System.out.println(colorsExtra.RED + "Invalid agent. Please enter a valid one." + colorsExtra.RESET);
            }

        }

        System.out.print("Did you win the game (yes/no): ");
        boolean gameWon = sc.nextLine().trim().equalsIgnoreCase("yes");

        // Store stats in Data class
        Data.storeNewGame(username, mapPlayed, gamemodePlayed, gameWon, kills, deaths, assists, agentPlayed);

        // Print success message
        System.out.println(colorsExtra.GREEN + "Game recorded in " + colorsExtra.RESET + colorsExtra.PURPLE + gamemodePlayed.name() + ": " + username + colorsExtra.RESET + colorsExtra.GREEN + " played on "
                + colorsExtra.PURPLE + mapPlayed.name() + colorsExtra.RESET + colorsExtra.GREEN + " with " + colorsExtra.RESET + colorsExtra.PURPLE + agentPlayed.name() + colorsExtra.RESET + colorsExtra.GREEN + " and "
                + colorsExtra.RESET + colorsExtra.PURPLE + (gameWon ? "won." : "lost.") + colorsExtra.RESET);

        //updating the total number of games played after creating a game
        Data.getAndUpdateTotalGamesPlayed(username, true);

        if(gameWon){
            Data.recordWin(username);
        }else if(!gameWon){
            Data.recordLoss(username);
        }

    }

    /**
     * Displays all saved games for a given player.
     * Prompts the user for a username, checks if the player exists,
     * and then retrieves and displays their saved games.
     */
    public static void menuDisplaySavedGames() {
        System.out.print("Enter the USERNAME to display saved games: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        Data.displaySavedGames(username);
    }
    /**
     * Displays the best game for a given player.
     * Asks for a username, verifies if the player exists,
     * and retrieves their best game if found.
     */
    public static void menuBestGame() {
        System.out.print("Enter the USERNAME to display the best game: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        Data.getBestGame(username);
    }

    /**
     * Displays the worst game for a given player.
     * Asks for a username, verifies if the player exists,
     * and retrieves their worst game if found.
     */
    public static void menuWorstGame() {
        System.out.print("Enter the USERNAME to display the worst game: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        Data.getWorstGame(username);
    }

    /**
     * Displays the best map for a given player.
     * Prompts for a username, checks if the player exists,
     * and retrieves their best map if available.
     */
    public static void menuBestMap() {
        System.out.print("Enter the USERNAME to display the best map: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        Data.getBestMap(username);
    }

    /**
     * Displays the worst map for a given player.
     * Prompts for a username, checks if the player exists,
     * and retrieves their worst map if available.
     */
    public static void menuWorstMap() {
        System.out.print("Enter the USERNAME to display the worst map: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        Data.getWorstMap(username);
    }

    /**
     * Displays the best agent for a given player.
     * Asks for a username, checks if the player exists,
     * and retrieves their best agent if found.
     */
    public static void menuBestAgent() {
        System.out.print("Enter the USERNAME to display the best agent: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        Data.getBestAgent(username);
    }

    /**
     * Displays the worst agent for a given player.
     * Asks for a username, checks if the player exists,
     * and retrieves their worst agent if found.
     */
    public static void menuWorstAgent() {
        System.out.print("Enter the USERNAME to display the worst agent: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        Data.getWorstAgent(username);
    }

    /**
     * Displays the win rate of a specific agent for a given player.
     * Prompts for a username and agent, checks if the player exists,
     * and retrieves the win rate of the specified agent for that player.
     */
    public static void menuAgentWinRate() {
        System.out.print("Enter the USERNAME: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        System.out.print("Enter the AGENT to check win rate: ");
        String agent = scanner.nextLine().trim().toUpperCase();

        Data.getAgentWinRate(username, agent);
    }

    /**
     * Displays the win rate of a specific map for a given player.
     * Prompts for a username and agent, checks if the player exists,
     * and retrieves the win rate of the specified map for that player.
     */
    public static void menuMapWinRate() {
        System.out.print("Enter the USERNAME: ");
        String username = scanner.nextLine().trim();

        if (!Data.checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET);
            return;
        }

        System.out.print("Enter the MAP to check win rate: ");
        String map = scanner.nextLine().trim().toUpperCase();

        Data.getMapWinRate(username, map);
    }

//    /**
//     * stores the stats for the player in a hashmap
//     * @param username the username of the player
//     * @param kills the number of kills
//     * @param deaths the number of deaths
//     * @param assists the number of assists
//     * @param gameWon whether the game was won or lost
//     */
//    private static void storePlayerStats(String username, int kills, int deaths, int assists, boolean gameWon){
//        HashMap<String, Integer> stats = new HashMap<>();
//        stats.put("kills", kills);
//        stats.put("deaths", deaths);
//        stats.put("assists", assists);
//
//        stats.put("totalGamesPlayed", stats.getOrDefault("totalGamesPlayed", 0) + 1);
//
//        //update win and loss count
//        if(gameWon){
//            stats.put("totalWins", stats.getOrDefault("totalWin", 0) + 1);
//        }else{
//            stats.put("totalLosses", stats.getOrDefault("totalLosses", 0) + 1);
//
//        }
//        playerStats.put(username, stats);
//
//    }

    /**
     * Gets the kill stats of a user
     *
     * @param username player username
     */
    public static void menuKillStatForNewGame(String username) {
        optionIndex = 6;
        if (playerStats.containsKey(username)) {
            int kills = playerStats.get(username).get("kills");
            System.out.println(username + " has " + kills + " kills.");
        } else {
            System.out.println("No kill stats found for " + colorsExtra.PURPLE + username + colorsExtra.RESET + ".");
        }
    }

    /**
     * Gets the death stats of a user
     *
     * @param username player username
     */
    public static void menuDeathStatForNewGame(String username) {
        optionIndex = 7;
        if (playerStats.containsKey(username)) {
            int deaths = playerStats.get(username).get("deaths");
            System.out.println(username + " has " + deaths + " deaths.");
        } else {
            System.out.println("No death stats found for " + colorsExtra.PURPLE + username + colorsExtra.RESET + ".");
        }
    }

    /**
     * Returns the total assists for a certain player
     *
     * @param username The username of the player
     */
    public static void menuAssistStatForNewGame(String username) {
        optionIndex = 8;
        if (playerStats.containsKey(username)) {
            int assists = playerStats.get(username).get("assists");
            System.out.println(username + " has " + assists + " assists.");
        } else {
            System.out.println("No assist stats found for " + colorsExtra.PURPLE + username + colorsExtra.RESET + ".");
        }
    }

    /**
     * prints the player's KDA
     * @param username the username of the player
     */
    public static void menuKillDeathRatio(String username) {
        if (playerStats.containsKey(username)) {
            // Get the stats for the player
            int kills = playerStats.get(username).getOrDefault("kills", 0);
            int deaths = playerStats.get(username).getOrDefault("deaths", 0);
            int assists = playerStats.get(username).getOrDefault("assists", 0);

            // Check if deaths are 0 to avoid division by zero
            if (deaths == 0) {
                System.out.println(username + " has an infinite KDA (no deaths)!");
            } else {
                // calculates kda ratio -> (kills + assists) / deaths
                double kda = (double)(kills + assists) / deaths;
                System.out.println(username + "'s KDA is " + kda + " (Kills: " + kills + ", Deaths: " + deaths + ", Assists: " + assists + ")");
            }
        } else {
            System.out.println("No game stats found for " + colorsExtra.PURPLE + username + colorsExtra.RESET + ".");
        }
    }

    /**
     * Adds a player to the data array for storing game/player data
     *
     * @param data Player data added (e.g. username, rank, etc.)
     */
    public static void addPlayerData(String data){
        //stores data to HashMap based on what user chose in the menu
        switch (optionIndex) {
            case 1 -> playerData.put("Username", data);
            case 2 -> playerData.put("Rank", data);
            default -> System.out.println("Unrecognized option");
        }
    }


}
