package ca.ucalgary.sbhat.projectvaloranttrackergui.enums;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */


//enum for game mode in valorant
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
