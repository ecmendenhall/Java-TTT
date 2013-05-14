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
        System.setOut(outputStream);

        controller.newGame();
        String expected = newBoard.toString() +  "\n";
        assertEquals(expected, output.toString());

        System.setOut(stdout);

    }

    @Test
    public void controllerShouldCheckForGameOverStates() {
        controller.checkForGameOver();
    }

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void controllerShouldLoadGames() {
        exit.expectSystemExit();
        controller.loadGame(noWins);
        controller.playRound();
    }

    @Test
    public void controllerShouldEndGameOnRestartIfInputIsNo() {
        exit.expectSystemExit();
        System.setOut(outputStream);

        view.pushInput("n");

        controller.restartGame();
        assertEquals(playAgain, output.toString());

        System.setOut(stdout);
        view.clearInput();

    }

    @Test
    public void controllerShouldStartNewGameOnRestartIfInputIsYes() {
        System.setOut(outputStream);

        view.pushInput("y");

        controller.restartGame();

        String expected = playAgain + "\n" + newBoard.toString() + "\n";
        assertEquals(expected, output.toString());

        System.setOut(stdout);
        view.clearInput();
    }

    @Test
    public void controllerShouldHandleNextRound() {
        view.pushInput("middle center");
        view.pushInput("top right");
        view.pushInput("middle right");

        exit.expectSystemExit();

        controller.newGame();

        System.setOut(outputStream);

        controller.playRound();
        String expected = yourMove + "\n" + xInCenter.toString() + "\n";

        assertEquals(expected, output.toString());

        System.setOut(stdout);
        view.clearInput();

    }

    @Test
    public void controllerShouldPassErrorMessageToViewOnInvalidInput() {
        view.pushInput("invalid phrase");
        view.pushInput("middle center");

        exit.expectSystemExitWithStatus(2);
        controller.newGame();

        System.setOut(outputStream);

        controller.playRound();
        String expected = yourMove + "\n" + xInCenter.toString() + "\n";

        assertEquals(expected, output.toString());

        System.setOut(stdout);
        view.clearInput();
    }

    @Test
    public void controllerShouldPassErrorMessageToViewOnInvalidMove() {
        view.pushInput("middle center");
        view.pushInput("middle center");

        exit.expectSystemExitWithStatus(2);
        controller.newGame();

        System.setOut(outputStream);

        controller.playRound();
        String expected = yourMove + "\n" + xInCenter.toString() + "\n";

        assertEquals(expected, output.toString());

        System.setOut(stdout);
        view.clearInput();
    }


}
