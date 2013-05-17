package com.cmendenhall;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class GameControllerTest extends TicTacToeTest {

    private MockTerminalView view = new MockTerminalView();
    private GameController controller = new GameController(view);

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream outputStream;
    private OutputRecorder outputRecorder;


    private String welcome;
    private String divider;
    private String yourMove;
    private String yourMoveThreeSquares;
    private String playAgain;
    private String gameOverWin;
    private String xWins;
    private String choosePlayerOne;
    private String boardSize;

    private void loadViewStrings() {
        Properties viewstrings = new Properties();
        try {
            viewstrings.load(getClass().getResourceAsStream("/viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        welcome = viewstrings.getProperty("welcome");
        divider = viewstrings.getProperty("divider");
        yourMove = viewstrings.getProperty("yourmove");
        yourMoveThreeSquares = viewstrings.getProperty("yourmovethreesquares");
        playAgain = viewstrings.getProperty("playagain");
        gameOverWin = viewstrings.getProperty("gameoverwin");
        xWins = viewstrings.getProperty("xwins");
        choosePlayerOne = viewstrings.getProperty("chooseplayerone");
        boardSize = viewstrings.getProperty("boardsize");
    }

    @Before
    public void setUp() throws Exception {
        loadViewStrings();
        outputStream = new PrintStream(output, true, "UTF-8");
        outputRecorder = new OutputRecorder(output, true, "UTF-8");
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

        System.setOut(stdout);
    }

    @Test
    public void controllerShouldCheckForGameOverStates() throws GameOverException {
        controller.checkForGameOver();
    }

    @Test(expected = GameOverException.class)
    public void controllerShouldLoadGames() throws GameOverException {
        view.enqueueInput("n");

        controller.loadGame(noWins);
        controller.playRound();
    }

    @Test(expected = GameOverException.class)
    public void controllerShouldEndGameOnRestartIfInputIsNo() throws GameOverException {
        System.setOut(outputStream);

        view.enqueueInput("n");

        controller.restartGame();
        assertEquals(playAgain, output.toString());

        System.setOut(stdout);
        view.clearInput();

    }

    @Test
    public void controllerShouldStartNewGameOnRestartIfInputIsYes() throws GameOverException {
        System.setOut(outputRecorder);

        view.enqueueInput("y");
        view.enqueueInput("3");
        view.enqueueInput("h");
        view.enqueueInput("h");

        view.enqueueInput("top left");
        view.enqueueInput("bottom left");
        view.enqueueInput("top middle");
        view.enqueueInput("bottom middle");
        view.enqueueInput("top right");

        view.enqueueInput("n");

        try {
            controller.restartGame();
        } catch (GameOverException e) {
            assertEquals(playAgain, outputRecorder.popFirstOutput());
        }

        System.setOut(stdout);
        view.clearInput();
    }

    @Test
    public void controllerShouldHandleNextRound() throws GameOverException {
        view.enqueueInput("middle center");
        view.enqueueInput("top right");
        view.enqueueInput("middle right");
        view.enqueueInput("n");

        controller.newGame();

        System.setOut(outputRecorder);

        try {
            controller.startGame();
        } catch (GameOverException e) {
            String expectedFirst = emptyBoard.toString();
            String expectedSecond = yourMoveThreeSquares + " X.";
            String expectedThird = xInCenter.toString();

            String outputFirst = outputRecorder.popFirstOutput();
            String outputSecond = outputRecorder.popFirstOutput();
            String outputThird = outputRecorder.popFirstOutput();

            assertEquals(expectedFirst, outputFirst);
            assertEquals(expectedSecond, outputSecond);
            assertEquals(expectedThird, outputThird);
        }

        System.setOut(stdout);
        view.clearInput();

    }

    @Test
    public void controllerShouldPassErrorMessageToViewOnInvalidInput() throws GameOverException {
        view.enqueueInput("invalid phrase");

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
        view.enqueueInput("middle center");
        view.enqueueInput("middle center");

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
        view.enqueueInput("n");
        controller.loadGame(playerXWins);

        System.setOut(outputRecorder);

        try {
            controller.checkForGameOver();
        } catch (GameOverException e) {
            String expected = gameOverWin + xWins;

            outputRecorder.discardFirstNStrings(1);
            String output = outputRecorder.popFirstOutput();

            assertEquals(expected, output);
        }

        System.setOut(stdout);
        view.clearInput();
    }

    @Test
    public void controllerShouldLoadPlayers() {
        view.enqueueInput("2");
        view.enqueueInput("c");
        view.enqueueInput("h");

        controller.setUp();

        Player playerOne = controller.getPlayerOne();
        Player playerTwo = controller.getPlayerTwo();

        assertEquals("MinimaxPlayer", playerOne.getClass().getSimpleName());
        assertEquals("HumanPlayer", playerTwo.getClass().getSimpleName());

    }

    @Test(expected = GameOverException.class)
    public void gameShouldEndOnWin() throws GameOverException {

        controller.newGame();

        view.enqueueInput("3");
        view.enqueueInput("h");
        view.enqueueInput("h");

        controller.setUp();

        view.enqueueInput("middle center");
        view.enqueueInput("top left");
        view.enqueueInput("top right");
        view.enqueueInput("middle left");
        view.enqueueInput("lower right");
        view.enqueueInput("lower left");

        view.enqueueInput("n");

        controller.startGame();
    }

    @Test
    public void playersShouldBeSettable() {
        controller = new GameController(view);

        controller.setPlayerOne(new HumanPlayer(X));
        controller.setPlayerTwo(new HumanPlayer(O));

        Player playerOne = controller.getPlayerOne();
        Player playerTwo = controller.getPlayerTwo();

        assertEquals("HumanPlayer", playerOne.getClass().getSimpleName());
        assertEquals("HumanPlayer", playerTwo.getClass().getSimpleName());
    }

    @Test
    public void setUpShouldSetPlayers() {
        controller = new GameController(view);

        view.enqueueInput("3");
        view.enqueueInput("h");
        view.enqueueInput("c");

        controller.setUp();

        Player playerOne = controller.getPlayerOne();
        Player playerTwo = controller.getPlayerTwo();

        assertEquals("HumanPlayer", playerOne.getClass().getSimpleName());
        assertEquals("MinimaxPlayer", playerTwo.getClass().getSimpleName());

        view.clearInput();
    }

    @Test
    public void setUpRepromptsForInputIfPlayerTypeIsInvalid() {
        controller = new GameController(view);

        view.enqueueInput("3");
        view.enqueueInput("z");
        view.enqueueInput("x");
        view.enqueueInput("f");

        System.setOut(outputRecorder);

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {
            String output = outputRecorder.popLastOutput();
            assertEquals(choosePlayerOne, output);
        }
    }

    @Test
    public void controllerShouldPromptForBoardSizeDuringSetup() {
        controller = new GameController(view);

        view.enqueueInput("4");

        System.setOut(outputRecorder);

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {
            String output = outputRecorder.popFirstOutput();
            assertEquals(boardSize, output);
        }
    }

    @Test
    public void controllerShouldRepromptForBoardSizeAfterInvalidInput() {
        controller = new GameController(view);

        view.enqueueInput("Kindly create a board with five squares and five columns.");

        System.setOut(outputRecorder);

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {
            String output = outputRecorder.popLastOutput();
            assertEquals(boardSize, output);
        }
    }

    @Test
    public void movePromptShouldHaveDifferentMessageBasedOnBoardSize() throws GameOverException {
        controller = new GameController(view);

        view.enqueueInput("3");
        view.enqueueInput("h");
        view.enqueueInput("h");

        System.setOut(outputRecorder);

        try {
            controller.setUp();
            controller.startGame();
        } catch (NoSuchElementException e) {
            String output = outputRecorder.popLastOutput();
            assertEquals(yourMoveThreeSquares + " X.", output);
        }

        view.clearInput();

        controller = new GameController(view);

        view.enqueueInput("4");
        view.enqueueInput("h");
        view.enqueueInput("h");

        System.setOut(outputRecorder);

        try {
            controller.setUp();
            controller.startGame();
        } catch (NoSuchElementException e) {
            String output = outputRecorder.popLastOutput();
             assertEquals(yourMove + " X.", output);
        }

        view.clearInput();
        System.setOut(stdout);
    }

}
