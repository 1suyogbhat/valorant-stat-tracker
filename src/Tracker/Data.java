package Tracker;

import Tracker.Player.Game;
import Tracker.Player.Player;
import Tracker.enums.Agents;
import Tracker.enums.Gamemode;
import Tracker.enums.Maps;
import Tracker.enums.Rank;
import Tracker.util.colorsExtra;

import java.io.*;
import java.util.*;

public class Data {

    private final ArrayList<Player> players; //list to store all the players
    private final HashMap<String, Player> usernames; //hashmap to store the players based on their username
    private final HashMap<String, HashMap<String, Integer>> playerStats;

    // Stores cumulative stats AND individual game history
    //hashmap to store that stats of the player
    //outer hashmap: username of the player under which you would be storing the stats
    //inner hashmap: type of the statistic
    private final HashMap<String, List<Game>> savedGames = new HashMap<>();

    /**
     * Data class
     * It is used for recording all the player statistics and stores data related to all the games that are played.
     */
    public Data(){
        players = new ArrayList<>(); //initializing the array list
        usernames = new HashMap<>(); //initizializing the hashmaps in which the usernames are stored, also storing the corresponding objects of the player
        playerStats = new HashMap<>(); //hashmap initizalied to store the stats of the player
    }

    /**
     * storeNewPlayer() function
     * It is used to add a new player and inputs the unique username with which all the data is tracked.
     * If the user already exists, it does not create a duplicated user again.
     * @param username The username of the player is the unique key for identification
     * @param rank the current rank of teh player
     * @return true is returned if the player is added successfully. False is returned if the player cannot be added (one reason being it already exists)
     */
    public boolean storeNewPlayer(String username, Rank rank) {
        if (!checkExistPlayer(username)) { //checks if the player already doesn't exist
            Player player= new Player(username, rank);
            players.add(player);
            usernames.put(username, player);
            System.out.println("Stored successfully");
            return true;
        } else {
            return false; //if the player already exists it does not add it to the list of player
        }
    }

    /**
     * checkExistPlayer() method
     * This method is used to check the existance of a player in the game.
     * @param username The unique username, which is the key for every player (enter the username which you want to find)
     * @return true is returned if the player's username exists in the game, or it returns false.
     */
    public boolean checkExistPlayer(String username) {
        return usernames.containsKey(username);
    }

    /**
     * All the players stored in the game can be retrieved using this array list
     * @return A list of all players (that includes their unique username and the rank is returned)
     */
    public ArrayList<Player> getAllPlayers() {
        return players; //obatin the list of all the players present in the game (username and rank is printed)
    }

//    /**
//     * getPlayerInfo()
//     * @param username This is the unique username
//     * @return returns the player object if found
//     */
//    public Player getPlayerInfo(String username) {
//        return usernames.get(username);
//    }

    /**
     * displayPlayerInfo() method
     * This method is used for displaying the stats of a particular player
     * @param username unique key that is used for accessing the player
     * @return the details of teh player (username/rank/total games played) is returned.
     */
    public boolean displayPlayerInfo(String username) {
        if (checkExistPlayer(username)) { //only executes if the player exists already in the game (identified using the username)
            Player player = usernames.get(username);
            Rank rank = player.getRank();

            // fetch total games played using getTotalGamesPlayed()
            int totalGamesPlayed = getAndUpdateTotalGamesPlayed(username, false);

            System.out.println(colorsExtra.BLUE + "======PLAYER DETAILS======" + colorsExtra.RESET);
            System.out.println("USERNAME: " + username); //details of the player is printed
            System.out.println("RANK: " + rank);
            System.out.println("TOTAL GAMES PLAYED: " + totalGamesPlayed);
            return true;
        }
        System.out.println("Player not found."); //executes when the player is not found
        return false;
    }

    /**
     *getAndUpdateTotalGamesPlayed() method
     * the total number of games played by the player is updated in this function
     * @param username unique key of the user is validated
     * @param increment if the increment evaluates to true (whenever menuCreateGame() is called)
     * @return the total number of games played is returned/ returns -1 is the player is not found.
     */
    public int getAndUpdateTotalGamesPlayed(String username, boolean increment) {
        // ensure the player exists in the system, if it does not then no total games are found/initialized
        if (!usernames.containsKey(username)) {
            System.out.println(colorsExtra.RED + "Error: Player not found in system." + colorsExtra.RESET);
            return -1; // Indicate player does not exist
        }

        // If stats do not exist, initialize them
        if (!playerStats.containsKey(username)) {
            HashMap<String, Integer> stats = new HashMap<>(); //stores the stats of the player (total games played)
            stats.put("totalGamesPlayed", 0);
            playerStats.put(username, stats);
        }

        // Retrieve the current total games played
        int totalGames = playerStats.get(username).getOrDefault("totalGamesPlayed", 0);
        //if the key "totalGamesPlayed" does not exist but the player does, the number of games played is initialized to 0.

        // If increment flag is true, update the total games played
        if (increment) {
            totalGames++;
            playerStats.get(username).put("totalGamesPlayed", totalGames);
        }

        return totalGames;
    }

    /**
     * storeNewGame() method
     * @param username unique username of the player
     * @param map map stored where the new game is played
     * @param gameMode the mode in which the game was played
     * @param won stores if the game was won/lost
     * @param kills stores the number of kills of the player in the game
     * @param deaths stores the number of deaths of the player in the game
     * @param assists stores the number of assists of the  player in the game
     * @param agent stores which agent the game was played
     */
    public void storeNewGame(String username, Maps map, Gamemode gameMode, boolean won, int kills, int deaths, int assists, Agents agent) {
        // Ensure player exists
        if (!checkExistPlayer(username)) {
            System.out.println(colorsExtra.RED + "Error: Player not found. Cannot record game." + colorsExtra.RESET);
            return;
        }

        Player player = usernames.get(username);
        Game game = new Game(player, map, gameMode, won, kills, deaths, assists, agent);

        savedGames.putIfAbsent(username, new ArrayList<>());
        savedGames.get(username).add(game);

        player.updateStats("totalGamesPlayed", +1);
        player.updateStats("kills", kills);
        player.updateStats("deaths", deaths);
        player.updateStats("assists", assists);
        if (won) {
            player.updateStats("totalWins", +1);
        } else {
            player.updateStats("totalLosses", +1);
        }
    }

    /**
     * clearAllData() method
     * This method is used to clear all the data of the game (all the players and their stats)
     */
    public void clearAllData() {
        players.clear(); // Clear the list of players
        usernames.clear(); // Clear the usernames map
        playerStats.clear(); // Clear the player stats map
        savedGames.clear(); // Clear the saved games map
    }

    /**
     * getTotalWins()
     * this method is used for obtaining the count of the total number of wins per user in the game.
     * @param username unqiue user of the game
     * @return returns the total number of wins of the player
     */
    public int getTotalWins(String username) {
        if (!playerStats.containsKey(username)) {
            return 0;
        }
        return playerStats.get(username).getOrDefault("totalWins", 0);
    }


    public void recordWin(String username) {
        if (!playerStats.containsKey(username)) {
            playerStats.put(username, new HashMap<>());
        }

        int wins = playerStats.get(username).getOrDefault("totalWins", 0);
        playerStats.get(username).put("totalWins", wins + 1);
    }

    /**
     * getTotalLosses()
     * this method is used for obtaining the count of the total number of losses per user in the game.
     * @param username unqiue user of the game
     * @return returns the total number of losses of the player
     */
    public int getTotalLosses(String username) {
        if (!playerStats.containsKey(username)) {
            return 0;
        }
        return playerStats.get(username).getOrDefault("totalLosses", 0);
    }

    public void recordLoss(String username) {
        if (!playerStats.containsKey(username)) {
            playerStats.put(username, new HashMap<>());
        }

        int losses = playerStats.get(username).getOrDefault("totalLosses", 0);
        playerStats.get(username).put("totalLosses", losses + 1);
    }


    /**
     * displaySavedGames()
     * Prints all the games that were ever created for the user
     * @param username unqiue key: username using which all the games are retrieved.
     */
    public void displaySavedGames(String username) {
        // Check if the player has any saved games
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        System.out.println("Saved games for " + colorsExtra.PURPLE + username + colorsExtra.RESET + ":");
        int gameNumber = 1;

        for (Game game : savedGames.get(username)) {
            System.out.printf("%d. Mode: %s | Kills: %d | Deaths: %d | Assists: %d | Map: %s | Agent: %s | Result: %s%n",
                    gameNumber,
                    game.getGameMode(),
                    game.getKills(),
                    game.getDeaths(),
                    game.getAssists(),
                    game.getMap(),
                    game.getAgent(),
                    game.getWonString()
            );
            gameNumber++;
        }
    }

    /**
     * getBestGame()
     * Best game of the player is determined based on the performance score (highest score) that is calculated using a formula.
     * @param username unique key of each player using which the data is retrived.
     */
    public void getBestGame(String username) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        double bestScore = Double.NEGATIVE_INFINITY;
        Game bestGame = null;

        for (Game game : savedGames.get(username)) {
            int kills = game.getKills();
            int deaths = game.getDeaths();
            int assists = game.getAssists();

            // Calculate performance score
            double performanceScore = kills + (assists * 0.5) - (deaths * 0.75);

            if (performanceScore > bestScore) {
                bestScore = performanceScore;
                bestGame = game;
            }
        }

        if (bestGame != null) {
            System.out.println("Best game for " + colorsExtra.PURPLE + username + colorsExtra.RESET + ":");
            System.out.printf("Mode: %s | Kills: %d | Deaths: %d | Assists: %d | Map: %s | Result: %s | Performance Score: %.2f%n",
                    bestGame.getGameMode(),
                    bestGame.getKills(),
                    bestGame.getDeaths(),
                    bestGame.getAssists(),
                    bestGame.getMap(),
                    bestGame.getWonString(),
                    bestScore
            );
        }
    }

    /**
     * getWorstGame()
     * Worst game of the player is determined based on the performance score (lowest score) that is calculated using a formula.
     * @param username unique key of each player using which the data is retrived.
     */
    public void getWorstGame(String username) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        Game worstGame = null;
        double worstKDA = Double.MAX_VALUE;
        int highestDeaths = 0;

        for (Game game : savedGames.get(username)) {
            int kills = game.getKills();
            int deaths = game.getDeaths();
            int assists = game.getAssists();

            // Avoid division by zero (if deaths = 0, assume a very high KDA)
            double kda = (deaths == 0) ? (kills + assists) : ((double) (kills + assists) / deaths);

            // Check if this game is worse
            if (kda < worstKDA || (kda == worstKDA && deaths > highestDeaths)) {
                worstKDA = kda;
                highestDeaths = deaths;
                worstGame = game;
            }
        }

        if (worstGame != null) {
            System.out.println("Worst game for " + colorsExtra.PURPLE + username + colorsExtra.RESET + ":");
            System.out.printf("Mode: %s | Map: %s | Agent: %s | Kills: %d | Deaths: %d | Assists: %d | KDA: %.2f | Result: %s%n",
                    worstGame.getGameMode(),
                    worstGame.getMap(),
                    worstGame.getAgent(),
                    worstGame.getKills(),
                    worstGame.getDeaths(),
                    worstGame.getAssists(),
                    worstKDA,
                    worstGame.getWonString());
        } else {
            System.out.println("No worst game found.");
        }
    }

    /**
     * getBestMap()
     * Best map of the player is obtained using the number of wins in per map ( the one which has the higest performance score)
     * @param username unique key of each player using which the data is retrived.
     */
    public void getBestMap(String username) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        // Initialize map-based performance tracking
        HashMap<Maps, Double> totalScores = new HashMap<>();
        HashMap<Maps, Integer> gameCounts = new HashMap<>();

        // Iterate through all games played by the user
        for (Game game : savedGames.get(username)) {
            Maps map = game.getMap();

            // Ensure map is valid before processing
            if (!Arrays.toString(Maps.values()).contains(map.name())) {
                continue;
            }

            int kills = game.getKills();
            int deaths = game.getDeaths();
            int assists = game.getAssists();

            // Calculate performance score
            double performanceScore = kills + (assists * 0.5) - (deaths * 0.75);

            // Accumulate scores and count games per map
            totalScores.put(map, totalScores.getOrDefault(map, 0.0) + performanceScore);
            gameCounts.put(map, gameCounts.getOrDefault(map, 0) + 1);
        }

        // Find the map with the highest average performance score
        Maps bestMap = null;
        double bestAverageScore = Double.NEGATIVE_INFINITY;

        for (Maps map : totalScores.keySet()) {
            double avgScore = totalScores.get(map) / gameCounts.get(map);
            if (avgScore > bestAverageScore) {
                bestAverageScore = avgScore;
                bestMap = map;
            }
        }

        if (bestMap != null) {
            System.out.printf("Best map for %s: %s (Average Performance Score: %.2f)%n",
                    username, bestMap.name(), bestAverageScore);
        } else {
            System.out.println(username + " has not played enough valid games to determine the best map.");
        }
    }

    /**
     * getWorstMap()
     * Worst map of the player is obtained using the number of losses per map ( the one which has the higest loss score)
     * @param username unique key of each player using which the data is retrived.
     */
    public void getWorstMap(String username) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        // Initialize tracking for worst performance
        HashMap<Maps, Double> totalScores = new HashMap<>();
        HashMap<Maps, Integer> gameCounts = new HashMap<>();

        // Iterate through all saved games
        for (Game game : savedGames.get(username)) {
            Maps map = game.getMap();

            // Ensure map is valid
            if (!Arrays.toString(Maps.values()).contains(map.name())) {
                continue;
            }

            int kills = game.getKills();
            int deaths = game.getDeaths();
            int assists = game.getAssists();

            // Calculate performance score
            double performanceScore = kills + (assists * 0.5) - (deaths * 0.75);

            // Accumulate scores and count games per map
            totalScores.put(map, totalScores.getOrDefault(map, 0.0) + performanceScore);
            gameCounts.put(map, gameCounts.getOrDefault(map, 0) + 1);
        }

        // Find the worst map based on lowest average score
        Maps worstMap = null;
        double worstAverageScore = Double.POSITIVE_INFINITY;

        for (Maps map : totalScores.keySet()) {
            double avgScore = totalScores.get(map) / gameCounts.get(map);
            if (avgScore < worstAverageScore) {
                worstAverageScore = avgScore;
                worstMap = map;
            }
        }

        if (worstMap != null) {
            System.out.printf("Worst map for %s: %s (Average Performance Score: %.2f)%n",
                    username, worstMap.name(), worstAverageScore);
        } else {
            System.out.println(colorsExtra.PURPLE + username + colorsExtra.RESET + " has not played enough valid games to determine the worst map.");
        }
    }

    /**
     * getBestAgent()
     * Best agent of the player is obtained using the number of wins in per agent ( the one which has the higest win score)
     * @param username unique key of each player using which the data is retrived.
     */
    public void getBestAgent(String username) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        // Initialize tracking for best agent performance
        HashMap<Agents, Double> totalScores = new HashMap<>();
        HashMap<Agents, Integer> gameCounts = new HashMap<>();

        // Iterate through all saved games
        for (Game game : savedGames.get(username)) {
            Agents agent = game.getAgent();

            // Ensure agent is valid
            if (!Arrays.toString(Agents.values()).contains(agent.name())) {
                continue;
            }

            int kills = game.getKills();
            int deaths = game.getDeaths();
            int assists = game.getAssists();

            // Calculate performance score
            double performanceScore = kills + (assists * 0.5) - (deaths * 0.75);

            // Accumulate scores and count games per agent
            totalScores.put(agent, totalScores.getOrDefault(agent, 0.0) + performanceScore);
            gameCounts.put(agent, gameCounts.getOrDefault(agent, 0) + 1);
        }

        // Find the best agent based on highest average score
        Agents bestAgent = null;
        double bestAverageScore = Double.NEGATIVE_INFINITY;

        for (Agents agent : totalScores.keySet()) {
            double avgScore = totalScores.get(agent) / gameCounts.get(agent);
            if (avgScore > bestAverageScore) {
                bestAverageScore = avgScore;
                bestAgent = agent;
            }
        }

        if (bestAgent != null) {
            System.out.printf("Best agent for %s: %s (Average Performance Score: %.2f)%n",
                    username, bestAgent.name(), bestAverageScore);
        } else {
            System.out.println(colorsExtra.PURPLE + username + colorsExtra.RESET + " has not played enough valid games to determine the best agent.");
        }
    }

    /**
     * getWorstAgent()
     * Worst agent of the player is obtained using the number of losses in per agent ( the one which has the lowest performance score)
     * @param username unique key of each player using which the data is retrived.
     */
    public void getWorstAgent(String username) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        // Initialize tracking for worst agent performance
        HashMap<Agents, Double> totalScores = new HashMap<>();
        HashMap<Agents, Integer> gameCounts = new HashMap<>();

        // Iterate through all saved games
        for (Game game : savedGames.get(username)) {
            Agents agent = game.getAgent();

            // Ensure agent is valid
            if (!Arrays.toString(Agents.values()).contains(agent.name())) {
                continue;
            }

            int kills = game.getKills();
            int deaths = game.getDeaths();
            int assists = game.getAssists();

            // Calculate performance score
            double performanceScore = kills + (assists * 0.5) - (deaths * 0.75);

            // Accumulate scores and count games per agent
            totalScores.put(agent, totalScores.getOrDefault(agent, 0.0) + performanceScore);
            gameCounts.put(agent, gameCounts.getOrDefault(agent, 0) + 1);
        }

        // Find the worst agent based on lowest average score
        Agents worstAgent = null;
        double worstAverageScore = Double.POSITIVE_INFINITY;

        for (Agents agent : totalScores.keySet()) {
            double avgScore = totalScores.get(agent) / gameCounts.get(agent);
            if (avgScore < worstAverageScore) {
                worstAverageScore = avgScore;
                worstAgent = agent;
            }
        }

        if (worstAgent != null) {
            System.out.printf("Worst agent for %s: %s (Average Performance Score: %.2f)%n",
                    username, worstAgent.name(), worstAverageScore);
        } else {
            System.out.println(colorsExtra.PURPLE + username + colorsExtra.RESET + " has not played enough valid games to determine the worst agent.");
        }
    }

    /**
     * The specific win rate of an agent is returned
     * @param username unique key: username is obtained to calculate the stats for the user
     * @param agent agent for which the win rate needs to be calculated
     */
    public void getAgentWinRate(String username, String agent) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        // Validate agent input
        if (!Arrays.toString(Agents.values()).contains(agent)) {
            System.out.println("Invalid agent name. Please enter a valid agent.");
            return;
        }

        int totalGamesWithAgent = 0;
        int totalWinsWithAgent = 0;

        // Iterate through all saved games
        for (Game game : savedGames.get(username)) {
            Agents playedAgent = game.getAgent();

            if (playedAgent.equals(Agents.valueOf(agent))) {
                totalGamesWithAgent++;
                if ((boolean) game.get("won")) {
                    totalWinsWithAgent++;
                }
            }
        }

        // If no games found with the agent, return
        if (totalGamesWithAgent == 0) {
            System.out.println(colorsExtra.PURPLE + username + colorsExtra.RESET + " has not played any games with " + colorsExtra.PURPLE + agent + colorsExtra.RESET + ".");
            return;
        }

        // Calculate win rate
        double winRate = ((double) totalWinsWithAgent / totalGamesWithAgent) * 100;

        System.out.printf("%s's win rate with %s: %.2f%%%n", username, agent, winRate);
    }

    /**
     * The specific win rate of a map is returned
     * @param username unique key: username is obtained to calculate the stats for the user
     * @param map map for which the win rate needs to be calculated
     */
    public void getMapWinRate(String username, String map) {
        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

        // Validate map input
        if (!Arrays.toString(Maps.values()).contains(map)) {
            System.out.println("Invalid map name. Please enter a valid map.");
            return;
        }

        int totalGamesOnMap = 0;
        int totalWinsOnMap = 0;

        // Iterate through all saved games
        for (Game game : savedGames.get(username)) {
            Maps playedMap = game.getMap();

            if (playedMap.equals(Maps.valueOf(map))) {
                totalGamesOnMap++;
                if (Boolean.TRUE.equals(game.get("won"))) {
                    totalWinsOnMap++;
                }
            }
        }

        // If no games found on the map, return
        if (totalGamesOnMap == 0) {
            System.out.println(colorsExtra.PURPLE + username + colorsExtra.RESET + " has not played any games on " + colorsExtra.PURPLE + map + colorsExtra.RESET + ".");
            return;
        }

        // Calculate win rate
        double winRate = ((double) totalWinsOnMap / totalGamesOnMap) * 100;

        System.out.printf("%s's win rate on %s: %.2f%%%n", username, map, winRate);
    }

    /**
     * Saves the player's game data to a file
     */
    public void saveDataToFile() {
        // ask for the username
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the USERNAME for games to save: ");
        String username = sc.nextLine();

        if (!savedGames.containsKey(username) || savedGames.get(username).isEmpty()) {
            System.out.println("No saved games found for " + username + ".");
            return;
        }

//        // Debugging: Print all saved games for the user
//        System.out.println("Saved games for " + username + ":");
//        for (Game game : savedGames.get(username)) {
//            System.out.println(game);
//        }

        String filename = username + "_games.csv";

        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("Saved games for " + username + ":");
            int gameNumber = 1;

            // Loop through all saved games for the player and write them to the file
            for (Game game : savedGames.get(username)) {
                writer.printf("%d,%s,%s,%s,%d,%d,%d,%s\n",
                        gameNumber,
                        game.getMap(),
                        game.getAgent(),
                        game.getGameMode(),
                        game.getKills(),
                        game.getDeaths(),
                        game.getAssists(),
                        game.getWonString()
                );
                gameNumber++;
            }

            System.out.println("Games saved to " + filename + ".");
        } catch (FileNotFoundException e) {
            System.out.println("Error saving games to file.");
        }
    }


}



