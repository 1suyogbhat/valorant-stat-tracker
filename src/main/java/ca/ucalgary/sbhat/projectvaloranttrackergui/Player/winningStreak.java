package ca.ucalgary.sbhat.projectvaloranttrackergui.Player;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Winning Streak Tracker
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

public class winningStreak {
    private final String username; // username of the player
    private final String rank; // rank of the player (as a String)
    private final int currentStreak; // current streak count
    private final int maxStreak; // maximum streak count
    private final int minStreak; // minimum streak count

    /**
     * Constructor for WinningStreak
     * @param username Username of the player
     * @param rank Rank of the player
     * @param currentStreak Current winning streak of the player
     * @param maxStreak Maximum winning streak of the player
     * @param minStreak Minimum winning streak (or "None" if no non-zero streak)
     */
    public winningStreak(String username, String rank, int currentStreak, int maxStreak, int minStreak) {
        this.username = username;
        this.rank = rank;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
        this.minStreak = minStreak;
    }

    /**
     * Returns the username of the player
     * @return player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the rank of the player
     * @return player's rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the current winning streak of the player
     * @return current streak
     */
    public int getCurrentStreak() {
        return currentStreak;
    }

    /**
     * Returns the maximum winning streak of the player
     * @return maximum streak
     */
    public int getMaxStreak() {
        return maxStreak;
    }

    /**
     * Returns the minimum winning streak (if any)
     * @return minimum streak, or "None" if no non-zero streak
     */
    public int getMinStreak() {
        return minStreak;
    }
}
