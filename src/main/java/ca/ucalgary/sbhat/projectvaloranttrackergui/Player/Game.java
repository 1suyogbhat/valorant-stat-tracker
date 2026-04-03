package ca.ucalgary.sbhat.projectvaloranttrackergui.Player;

import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Agents;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Maps;

import java.util.HashMap;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */


/**
 * This class is used for representing the single game that is played by the player
 *  This class contains information about the game, including the player who played the game, the map,
 *  the game mode, whether the game was won or lost, as well as performance statistics such as kills, deaths, and assists.
 *  It also stores the agent used by the player during the game.
 *  The Game class extends to hashmap, allowing the game to store additional attributes in a flexible manner.
 * The class provides getter and setter methods for each of the attributes, and methods to retrieve the win status
 * as a string ("WON" or "LOST") or as a boolean (true for win, false for loss).
 *
 */
public class Game extends HashMap<String, Object> {

    private Player player;
    private Maps map;
    private String gameMode;
    private boolean won;
    private int kills;
    private int deaths;
    private int assists;
    private Agents agent;

    public Game(Player player, Maps map, String gameMode, boolean won, int kills, int deaths, int assists, Agents agent) {
        this.player = player;
        this.map = map;
        this.gameMode = gameMode;
        this.won = won;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.agent = agent;
    }

    public Player getPlayer() {
        return player;
    }

    public Maps getMap() {
        return map;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getWonString() {
        if (won) {
            return "WON";
        } else {
            return "LOST";
        }
    }

    public boolean getWonBoolean() {
        return won;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public Agents getAgent() {
        return agent;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMap(Maps map) {
        this.map = map;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setAgent(Agents agent) {
        this.agent = agent;
    }

}
