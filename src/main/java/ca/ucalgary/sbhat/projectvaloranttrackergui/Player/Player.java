package ca.ucalgary.sbhat.projectvaloranttrackergui.Player;

import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

public class Player implements Comparable<Player> {

    private String username; // The username of the player
    private Rank rank; // The rank of the player
    private HashMap<String, Integer> stats; // The stats of the player
    private final List<Game> games = new ArrayList<>();

    /**
     * Constructor for the Tracker.enums.Player.Tracker.enums.Player class
     * @param username The username of the player
     * @param rank The rank of the player
     */
    public Player(String username, Rank rank) {
        this.username = username;
        this.rank = rank;
        this.stats = new HashMap<>();
        this.stats.put("totalGamesPlayed", 0);
    }

    /**
     * Getter for the username of the player
     * @return The username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the rank of the player
     * @return The rank of the player
     */
    public Rank getRank() {
        return rank;
    }


    /**
     * Compares two players based on their usernames
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Player other) {
        return this.username.compareTo(other.username);
    }

    /**
     * Ensures that two players are equal if they have the same username
     * @return int representing the hashcode of the player
     */
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    /**
     * Getter for the stats of the player
     * @return The stats of the player
     */
    public HashMap<String, Integer> getStats() {
        return stats;
    }

    /**
     * Updates the stats of the player
     * @param key The key of the stat to be updated
     * @param value The value to be updated to
     */
    public void updateStats(String key, int value) {
        this.stats.put(key, this.stats.getOrDefault(key, 0) + value);
    }

    /**
     * Turns the player details into a string
     * @return string representing the player and the rank
     */
    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }


    public double getKDA() {
        int kills = getTotalKills();
        int assists = getTotalAssists();
        int deaths = getTotalDeaths();

        return (kills + assists) / (double) Math.max(1, deaths); // KDA = (kills + assists) / max(1, deaths)
    }

    public double getPerformanceScore() {
        double KDA = getKDA();
        boolean playerWon = getTotalWins() > getTotalLosses(); // If wins are greater than losses, consider the player as having won
        double winBonus = playerWon ? 1.0 : 0.0;  // 1 if player won, else 0

        return (KDA * 0.9) + (winBonus * 0.1); // PerformanceScore = (KDA * 0.9) + (WinBonus * 0.1)
    }

    public int getTotalGamesPlayed() {
        return stats.getOrDefault("totalGamesPlayed", 0);
    }

    public int getTotalWins() {
        return stats.getOrDefault("totalWins", 0);
    }

    public int getTotalLosses() {
        return stats.getOrDefault("totalLosses", 0);
    }

    public int getTotalKills() {
        return stats.getOrDefault("totalKills", 0);
    }

    public int getTotalDeaths() {
        return stats.getOrDefault("totalDeaths", 0);
    }

    public int getTotalAssists() {
        return stats.getOrDefault("totalAssists", 0);
    }


    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        games.add(game);
    }
}
