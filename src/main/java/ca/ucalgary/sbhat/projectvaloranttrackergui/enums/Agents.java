package ca.ucalgary.sbhat.projectvaloranttrackergui.enums;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */
//enum for agents in valorant
public enum Agents {
    BRIMSTONE, VIPER, OMEN, CYBER, PHOENIX, SOVA, KILLJOY,
    RAZE, BREACH, SKYE, REYNA, SAGE, KO, CYPHER, YORU, ASTRA,
    VOLT, NEON, ECHO, CHAMBER, FADE, VYSE, TEJO, WAYLAY,
    JETT, KAYO, GEKKO, HARBOR, DEADLOCK, ISO, CLOVE;

    private String agent = "";

    Agents() {
        this.agent = agent;
    }

    public String getAgent() {
        return agent;
    }
}
