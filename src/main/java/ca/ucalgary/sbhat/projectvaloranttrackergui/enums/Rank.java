package ca.ucalgary.sbhat.projectvaloranttrackergui.enums;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

//enum for ranks in valorant
public enum Rank {
    IRON, IRON1, IRON2, IRON3,
    BRONZE, BRONZE1, BRONZE2, BRONZE3,
    SILVER, SILVER1, SILVER2, SILVER3,
    GOLD, GOLD1, GOLD2, GOLD3,
    PLATINUM, PLATINUM1, PLATINUM2, PLATINUM3,
    DIAMOND, DIAMOND1, DIAMOND2, DIAMOND3,
    IMMORTAL, IMMORTAL1, IMMORTAL2, IMMORTAL3,
    RADIANT, UNRANKED;

    /**
     * Converts a given string to a Rank enum, handling variations like spacing and case differences.
     * @param rank the input rank as a string
     * @return the corresponding Rank enum, or UNRANKED if invalid
     */
    public static Rank getRank(String rank) {
        if (rank == null || rank.trim().isEmpty()) {
            return UNRANKED; // Default rank if input is empty
        }

        // Normalize input: remove spaces and convert to uppercase
        String formattedRank = rank.trim().toUpperCase().replace(" ", "");

        // Loop through all Rank values and compare formatted strings
        for (Rank r : Rank.values()) {
            if (r.name().equalsIgnoreCase(formattedRank)) {
                return r;
            }
        }

        return UNRANKED; // Default to UNRANKED if no match is found
    }
}
