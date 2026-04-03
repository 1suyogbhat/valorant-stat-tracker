package Tracker.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public final class Reader {
    /**
     * Reads data from a file and displays it to the console
     */
    public static void readDataFromFile() {
        // ask for the username
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the USERNAME: ");
        String username = sc.nextLine();

        // the file being read
        String filename = username + "_games.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Games played by " + username + ":");
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() || !line.startsWith("Saved games for")) {
                    String[] parts = line.split(",");
                    if (parts.length == 8) {
                        try {
                            int gameNumber = Integer.parseInt(parts[0]);
                            String mapPlayed = parts[1];
                            String agent = parts[2];
                            String gameMode = parts[3];
                            int kills = Integer.parseInt(parts[4]);
                            int deaths = Integer.parseInt(parts[5]);
                            int assists = Integer.parseInt(parts[6]);
                            String result = parts[7];
                            System.out.printf("%d. Map: %s | Agent: %s | Mode: %s | Kills: %d | Deaths: %d | Assists: %d | Result: %s%n",
                                    gameNumber, mapPlayed, agent, gameMode, kills, deaths, assists, result);
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing game data: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
