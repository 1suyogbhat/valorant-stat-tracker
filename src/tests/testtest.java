package Tracker;

import Tracker.enums.Maps;
import Tracker.enums.Rank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class testtest {
    public static void main(String[] args) {
//        Data data = new Data();
//        data.storeNewPlayer("bob", Rank.UNRANKED);
//        String input = "bob\nunrated\n23\n23\n23\nhaven\nreyna\nyes\n";
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // converts the string to bytes
//        System.setIn(inputStream);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//
//        // Call the method under test
//        Menu.menuCreateNewGame();
//
//        System.setIn(System.in);
//        System.setOut(System.out);
//
//        String actualOutput = outputStream.toString().trim();
//        System.out.println(actualOutput);

        Maps[] maps = Maps.values();
        for (int i = 0; i < maps.length; i++) {
            System.out.printf("%d. %s%n", i + 1, maps[i].name());
        }
        System.out.print("Enter your game mode (unrated, competitive, etc.) or enter 1 to view all modes: ");
        Scanner sc = new Scanner(System.in);
        String gameModeInput = sc.nextLine().trim().toLowerCase();
    }
}
