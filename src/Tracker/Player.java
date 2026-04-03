package Tracker.Player;

import Tracker.enums.Rank;

import java.util.HashMap;
import java.util.Objects;


public class Player implements Comparable<Player> {

    private String username; // The username of the player
    private Rank rank; // The rank of the player
    private HashMap<String, Integer> stats; // The stats of the player

    /**
     * Constructor for the Player class
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
        return "Player {" +
                "USERNAME = '" + username + '\'' +
                ", RANK = '" + rank + '\'' +
                '}';
    }

}
