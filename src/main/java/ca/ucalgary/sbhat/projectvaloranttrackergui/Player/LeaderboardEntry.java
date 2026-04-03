package ca.ucalgary.sbhat.projectvaloranttrackergui.Player;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

public class LeaderboardEntry {
    private final String playerName; //name of the player
    private final String rank; //rank of the player
    private final double kda; //kda ratio
    private final double performanceScore; //performance score calculated for stats.


    /**
     * leaderboard entry constructor
     * @param rank rank of the player on th leaderboard
     * @param playerName name of the player
     * @param kda KDA of the player
     * @param performanceScore overall performance score of the player
     */
    public LeaderboardEntry(String rank, String playerName, double kda, double performanceScore) {
        this.rank=rank;
        this.playerName = playerName;
        this.kda = kda;
        this.performanceScore = performanceScore;
    }

    /**
     * Returns the rank of the player who is on the leaderboard
     * @return player's rank
     */
    public String getRank() {
        return rank;
    }


    /**
     * Returns the username of the player who is on the leaderboard
     * @return player's username
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the KDA ratio of the player who is on the leaderboard
     * @return a double representing KDA
     */
    public double getKda() {
        return Double.parseDouble(String.format("%.2f", kda));
    }


    /**
     * The performance score of the player is calculated and returned
     * @return performance score of the player
     */
    public double getPerformanceScore() {
        return Double.parseDouble(String.format("%.2f", performanceScore));
    }
}
