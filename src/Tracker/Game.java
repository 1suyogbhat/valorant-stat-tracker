package Tracker.Player;

import Tracker.enums.Agents;
import Tracker.enums.Gamemode;
import Tracker.enums.Maps;

import java.util.HashMap;

public class Game extends HashMap<String, Object> {

    private Player player;
    private Maps map;
    private Gamemode gameMode;
    private boolean won;
    private int kills;
    private int deaths;
    private int assists;
    private Agents agent;

    public Game(Player player, Maps map, Gamemode gameMode, boolean won, int kills, int deaths, int assists, Agents agent) {
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

    public Gamemode getGameMode() {
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

    public void setGameMode(Gamemode gameMode) {
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
