package ca.ucalgary.sbhat.projectvaloranttrackergui.util;

import ca.ucalgary.sbhat.projectvaloranttrackergui.Data;
import ca.ucalgary.sbhat.projectvaloranttrackergui.Player.Game;
import ca.ucalgary.sbhat.projectvaloranttrackergui.enums.Rank;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Writer {

    public static void saveDataToFile(String filePath, List<Game> games) {
        if (games == null || games.isEmpty()) {
            System.out.println("No games to save.");
            return;
        }

        // Extract the username from the first game
        String username = games.getFirst().getPlayer().getUsername();
        Rank rank = games.getFirst().getPlayer().getRank();
        String fileName = username + "_games.csv";
        File file = new File(fileName);
        boolean hasContent = false;

        // Check if the file exists and has content
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                hasContent = (br.readLine() != null); // Check if the first line exists
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }

        // Write or append to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(rank.toString());
            bw.write("\n");

            for (Game game : games) {
                String result;
                if (game.getWonBoolean()) {
                    result = "WON";
                } else {
                    result = "LOST";
                }

                bw.write(game.getMap() + "," +
                        game.getAgent() + "," +
                        game.getGameMode() + "," +
                        game.getKills() + "," +
                        game.getDeaths() + "," +
                        game.getAssists() + "," +
                        result + "\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to write game/s to file: " + filePath);
            System.err.println(e.getMessage());
        }
    }

}
