package ca.ucalgary.sbhat.projectvaloranttrackergui.util;

import ca.ucalgary.sbhat.projectvaloranttrackergui.Data;
import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.Game;
import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.Player;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Agents;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Maps;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Rank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CPSC 233 PROJECT: TRACKER APP
 * Topic: Valorant KDA
 * @author Suyog Bhat, Jasnoor Kaur, Apphia Ferrer
 * @email jasnoor.kaur1@ucalgary.ca, suyog.bhat@ucalgary.ca, apphia.ferrer@ucalgary.ca
 */

public class Reader {
    /**
     * Reads data from a file and displays it to the console
     */
    public static List<Game> readDataFromFile(File file) {
        String filename = file.getName();
        String username = filename.substring(0, filename.lastIndexOf("_"));
        Player newPlayer = new Player(username, Rank.getRank(username));
        List<Game> games = new ArrayList<>();
        Data data = new Data();
        boolean won = false;
        int gameNumber = 1;
        Rank rank = Rank.getRank(username);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("GAMES PLAYED BY: " + username);
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                line = line.trim();
                String[] parts = line.split(",");
                if (parts.length == 1) {
                    String rankString = parts[0].trim();
                    rank = Rank.valueOf(rankString.toUpperCase());
                    newPlayer.setRank(rank);
                    System.out.println("RANK: " + rank);
                }
             if (parts.length == 7) {
                    try {
                        Maps mapPlayed = Maps.valueOf(parts[0].toUpperCase());
                        Agents agent = Agents.valueOf(parts[1].toUpperCase());
                        String gameMode = parts[2];
                        int kills = Integer.parseInt(parts[3]);
                        int deaths = Integer.parseInt(parts[4]);
                        int assists = Integer.parseInt(parts[5]);
                        String result = parts[6];
                        // Create a new Game object with the parsed data
                        Game game = new Game(newPlayer, mapPlayed, gameMode, result.equals("WON"), kills, deaths, assists, agent);
                        games.add(game);

                        if (result.equalsIgnoreCase("WON")) {
                            won = true;
                        }
//                        data.storeNewGame(username, mapPlayed, gameMode, won, kills, deaths, assists, agent);
                        System.out.printf("%d. Map: %s | Agent: %s | Mode: %s | Kills: %d | Deaths: %d | Assists: %d | Result: %s%n",
                                gameNumber, mapPlayed, agent, gameMode, kills, deaths, assists, result);

                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing game data: " + e.getMessage());
                    }
                    gameNumber++;
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return games;
    }
}
