package ca.ucalgary.sbhat.projectvaloranttrackergui.enums;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

//enum for maps in valorant
public enum Maps {
    BIND, HAVEN, SPLIT, ASCENT, ICEBOX, BREEZE, FRACTURE, PEARL, LOTUS, SUNSET, ABYSS;

    private String map = "";

    Maps() {
        this.map = map;
    }

    public String getMap() {
        return map;
    }
}
