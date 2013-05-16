package com.cmendenhall;

import junit.extensions.TestSetup;
import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class GameControllerTest extends TicTacToeTest {

    private MockTerminalView view = new MockTerminalView();
    private GameController controller = new GameController(view);
    private Board newBoard = new GameBoard();

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream outputStream;


    private String welcome;
    private String yourMove;
    private String playAgain;
    private String gameOverDraw;
    private String gameOverWin;
    private String xWins;
    private String oWins;

    private void loadViewStrings() {
        Properties viewstrings = new Properties();
        try {
            viewstrings.load(getClass().getResourceAsStream("viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        welcome = viewstrings.getProperty("welcome");
        yourMove = viewstrings.getProperty("yourmove");
        playAgain = viewstrings.getProperty("playagain");
        gameOverDraw  = viewstrings.getProperty("gameoverdraw");
        gameOverWin = viewstrings.getProperty("gameoverwin");
        xWins = viewstrings.getProperty("xwins");
        oWins = viewstrings.getProperty("owins");
    }

    @Before
    public void setUp() throws Exception {
        loadViewStrings();
        outputStream = new PrintStream(output, true, "UTF-8");
        Player playerOne = new HumanPlayer(X);
        Player playerTwo = new MinimaxPlayer(O);
        controller.setPlayerOne(playerOne);
        controller.setPlayerTwo(playerTwo);
    }

    @Test
    public void controllerShouldStartNewGame() {
        System.setOut(outputRecorder);

        controller.newGame();

        assertEquals(welcome, outputRecorder.popFirstOutput());
        assertEquals(divider, outputRecorder.popFirstOutput());
        assertEquals(emptyBoard.toString(), outputRecorder.popFirstOutput());

        System.setOut(stdout);
    }

    @Test
    public void controllerShouldCheckForGameOverStates() throws GameOverException {
        controller.checkForGameOver();
    }

    @Test(expected = GameOverException.class)
    public void controllerShouldLoadGames() throws GameOverException {
        controller.loadGame(noWins);
        controller.playRound();
    }

    @Test(expected = GameOverException.class)
    public void controllerShouldEndGameOnRestartIfInputIsNo() throws GameOverException {
        System.setOut(outputStream);

        view.pushInput("n");

        controller.restartGame();
        assertEquals(playAgain, output.toString());

        System.setOut(stdout);
        view.clearInput();

    }

    @Test
    public void controllerShouldStartNewGameOnRestartIfInputIsYes() throws GameOverException {
        System.setOut(outputRecorder);

        view.pushInput("y");

        controller.restartGame();

        assertEquals(playAgain, outputRecorder.popFirstOutput());
        assertEquals(welcome, outputRecorder.popFirstOutput());

        System.setOut(stdout);
        view.clearInput();
    }

    @Test
    public void controllerShouldHandleNextRound() throws GameOverException {
        view.pushInput("middle center");
        view.pushInput("top right");
        view.pushInput("middle right");

        controller.newGame();

        System.setOut(outputRecorder);

        try {
            controller.playRound();
        } catch (GameOverException e) {
            String expectedFirst = yourMove + " X.";
            String expectedNext = xInCenter.toString();

            String outputFirst = outputRecorder.popFirstOutput();
            String outputNext = outputRecorder.popFirstOutput();

            assertEquals(expectedFirst, outputFirst);
            assertEquals(expectedNext, outputNext);


        }

        System.setOut(stdout);
        view.clearInput();

    }

    @Test
    public void controllerShouldPassErrorMessageToViewOnInvalidInput() throws GameOverException {
        view.pushInput("invalid phrase");

        controller.newGame();

        System.setOut(outputRecorder);

        try {
            controller.playRound();
        } catch (NoSuchElementException e) {

            outputRecorder.discardFirstNStrings(1);
            String output = outputRecorder.popFirstOutput();

            assertEquals("That's not a valid board location.", output);

        }

        System.setOut(stdout);
        view.clearInput();
    }

    @Test
    public void controllerShouldPassErrorMessageToViewOnInvalidMove() throws GameOverException {
        view.pushInput("middle center");
        view.pushInput("middle center");

        controller.newGame();

        System.setOut(outputRecorder);

        try {
            controller.playRound();
        } catch (NoSuchElementException e) {

            outputRecorder.discardFirstNStrings(4);
            String output = outputRecorder.popFirstOutput();

            assertEquals("Square is already full.", output);
        }
        System.setOut(stdout);
        view.clearInput();
    }

    @Test
    public void controllerShouldPrintWinnerMessageAfterWin() throws GameOverException {
        controller.loadGame(playerXWins);

        System.setOut(outputRecorder);

        try {
            controller.checkForGameOver();
        } catch (GameOverException e) {
            String expected = gameOverWin + xWins;
            System.setOut(stdout);

            outputRecorder.discardFirstNStrings(1);
            String output = outputRecorder.popFirstOutput();

            assertEquals(expected, output);
        }

        System.setOut(stdout);
    }

    @Test
    public void controllerShouldLoadPlayers() {
        view.pushInput("c");
        view.pushInput("h");

        controller.setUp();

        Player playerOne = controller.getPlayerOne();
        Player playerTwo = controller.getPlayerTwo();

        assertEquals("MinimaxPlayer", playerOne.getClass().getSimpleName());
        assertEquals("HumanPlayer", playerTwo.getClass().getSimpleName());

    }


}
