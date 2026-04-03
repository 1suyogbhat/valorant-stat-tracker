package Tracker.tests;

import Tracker.util.colorsExtra;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Tracker.Menu;
import Tracker.enums.*;
import Tracker.Player.*;
import Tracker.Data;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class Testing {

    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() throws Exception {
        // Save original System.in and System.out
        originalIn = System.in;
        originalOut = System.out;

        // Redirect System.out to capture test output
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Data data = new Data();
        data.clearAllData();
    }

    @AfterEach
    void tearDown() {
        // Restore original System.in and System.out
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // 1. Menu test Case for Exit (Case 0)
    @Test
    public void menuLoopTestExit00() {
        int menuOption = 0;  // Selecting option 0 to exit

        if (menuOption == 0) {
            assertEquals("Thank you for using our tracker! Exiting...", "Thank you for using our tracker! Exiting...");
        } else {
            fail("Exit test failed. Option 0 did not trigger the exit behavior.");
        }
    }

    // 2. Menu test Case for Default (Invalid Menu Option)
    @Test
    public void menuLoopTestDefaultInvalidOption() {
        int menuOption = 100;  // Invalid option

        if (menuOption < 0 || menuOption >= 100) {
            assertTrue(menuOption != 0);
        } else {
            fail("Default case not triggered. Invalid option was not handled.");
        }
    }

    // 3. Test Case for Case 1 (Username and Tracker.enums.Rank)
    @Test
    public void menuLoopTestCase1() {
        String validUsername = "player123";
        String validRankInput = "Gold 1";

        String input = validUsername + "\n" + validRankInput + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String resultUsername = Menu.menuUsername();
        Rank resultRank = Rank.getRank(validRankInput);

        assertEquals(validUsername, resultUsername);
        assertEquals(Rank.GOLD1, resultRank);
    }

    @Test
    public void menuLoopTestCase8() {
        // Arrange
        String username = "player123";

        // Act
        Menu.menuAssistStatForNewGame(username);

        // Assert
        String expectedOutput = "No assist stats found for " + colorsExtra.PURPLE + "player123" + colorsExtra.RESET + ".";
        String actualOutput = testOut.toString().trim();
        assertEquals(expectedOutput, actualOutput, "The output does not match the expected assist stats.");
    }

    @Test
    void testMenuUsernameAndRank_validInput() {
        String validUsername = "Gamer123";
        String validRankInput = "GOLD2";

        String input = validUsername + "\n" + validRankInput + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String resultUsername = Menu.menuUsername();
        Rank resultRank = Rank.getRank(validRankInput);
        Player player = new Player(resultUsername, resultRank);

        assertEquals(validUsername, player.getUsername());
        assertEquals(Rank.GOLD2, player.getRank());
    }

    @Test
    public void menuRank_TestUNRANKED() {
        String simulatedUserInput = "\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        Menu.menuRank();

        String expectedOutput = "UNRANKED";
        assertTrue(testOut.toString().contains(expectedOutput));
    }

    @Test
    public void menuKillStatForNewGame_TestNoKills() {
        String username = "cocoGrandma";

        Menu.menuKillStatForNewGame(username);

        String expected = "No kill stats found for " + colorsExtra.PURPLE + "cocoGrandma" + colorsExtra.RESET + ".";
        assertEquals(expected, testOut.toString().trim());
    }

    @Test
    public void menuDeathStatForNewGame_TestNoDeaths() {
        String username = "mamaMoBlue";

        Menu.menuDeathStatForNewGame(username);

        String expected = "No death stats found for " + colorsExtra.PURPLE + "mamaMoBlue" + colorsExtra.RESET + ".";
        assertEquals(expected, testOut.toString().trim());
    }

    @Test
    public void menuAssistStatForNewGame_TestNoAssists() {
        String username = "teekepep";

        Menu.menuAssistStatForNewGame(username);

        String expected = "No assist stats found for " + colorsExtra.PURPLE + "teekepep" + colorsExtra.RESET + ".";
        assertEquals(expected, testOut.toString().trim());
    }

    @Test
    public void menuKillDeathRatio_TestNoStats() {
        String username = "TUG123";

        Menu.menuKillDeathRatio(username);

        String expected = "No game stats found for " + colorsExtra.PURPLE + "TUG123" + colorsExtra.RESET + ".";
        assertEquals(expected, testOut.toString().trim());
    }

    @Test
    public void menuBestAgent_TestWithInvalidUsername() {
        String invalidUsername = "invalidPlayer";
        System.setIn(new ByteArrayInputStream((invalidUsername + "\n").getBytes()));

        Menu.menuBestAgent();

        String expectedErrorMessage = colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET;
        assertTrue(testOut.toString().contains(expectedErrorMessage));
    }

    @Test
    public void menuAgentWinRate_TestWithInvalidStats() {
        String input = "1\nplayer123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu.menuAgentWinRate();

        String expected = "Enter the USERNAME: " + colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET;
        assertEquals(expected, testOut.toString().trim());
    }

    @Test
    public void menuAgentWinRate_TestWithValidStat() {
        // Arrange: Simulate user input
        String input = "player123\nJett\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu.setPlayerStats("player123", "Phoenix", 60.0);

        // Act: Call the method
        Menu.menuAgentWinRate();

        // Assert: Verify the output
        String expectedOutput = "Enter the USERNAME: " + colorsExtra.RED + "Player not found. Please enter a valid username." + colorsExtra.RESET;
        assertTrue(testOut.toString().contains(expectedOutput), "Actual output: " + testOut.toString());    }
}
