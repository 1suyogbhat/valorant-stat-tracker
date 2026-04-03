package Tracker.enums;

public enum Gamemode {
    UNRATED, SPIKE_RUSH, DEATHMATCH, ESCALATION, COMPETITIVE, TEAM_DEATHMATCH, CUSTOM, REPLICATION;

    private String gamemode = "";

    Gamemode() {
        this.gamemode = gamemode;
    }

    public String getGamemode() {
        return gamemode;
    }
}
