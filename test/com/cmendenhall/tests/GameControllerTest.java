package com.cmendenhall.tests;

import com.cmendenhall.controllers.GameController;
import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.players.HumanPlayer;
import com.cmendenhall.players.MinimaxPlayer;
import com.cmendenhall.players.Player;
import com.cmendenhall.mocks.MockTerminalView;
import com.cmendenhall.utils.OutputRecorder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.NoSuchElementException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static com.cmendenhall.TicTacToeSymbols.*;

@RunWith(JUnit4.class)
public class GameControllerTest {

    private MockTerminalView view = new MockTerminalView();
    private GameController controller = new GameController(view);

    private PrintStream stdout;
    private OutputRecorder recorder;


    private String welcome;
    private String divider;
    private String yourMove;
    private String yourMoveThreeSquares;
    private String playAgain;
    private String gameOverWin;
    private String xWins;
    private String choosePlayerOne;
    private String boardSize;

    private void setUpRecorder() throws UnsupportedEncodingException {
        stdout = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        recorder = new OutputRecorder(output, true, "UTF-8");
    }

    private void startRecorder() {
        System.setOut(recorder);
    }

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

        setUpRecorder();

        Player playerOne = new HumanPlayer(X);
        Player playerTwo = new MinimaxPlayer(O);
        controller.setPlayerOne(playerOne);
        controller.setPlayerTwo(playerTwo);
    }

    @Test
    public void controllerShouldStartNewGame() {
        startRecorder();

        controller.newGame();

        assertEquals(welcome, recorder.popFirstOutput());
        assertEquals(divider, recorder.popFirstOutput());

    }

    @Test
    public void controllerShouldCheckForGameOverStates() throws GameOverException {
        controller.checkForGameOver();
    }

    @Test(expected = GameOverException.class)
    public void controllerShouldLoadGames() throws GameOverException {
        view.enqueueInput("n");

        controller.loadGame(TicTacToeTestHelper.noWins);
        controller.playRound();
    }

    @Test(expected = GameOverException.class)
    public void controllerShouldEndGameOnRestartIfInputIsNo() throws GameOverException {
        startRecorder();

        view.enqueueInput("n");

        controller.restartGame();

        String output = recorder.popLastOutput();
        assertEquals(playAgain, output);

        System.setOut(stdout);
    }

    @Test
    public void controllerShouldStartNewGameOnRestartIfInputIsYes() throws GameOverException {
        startRecorder();

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
            assertEquals(playAgain, recorder.popFirstOutput());
        }
    }

    @Test
    public void controllerShouldHandleNextRound() throws GameOverException {
        view.enqueueInput("middle center");
        view.enqueueInput("top right");
        view.enqueueInput("middle right");
        view.enqueueInput("n");

        controller.newGame();

        startRecorder();

        try {
            controller.startGame();
        } catch (Exception e) {
            String expectedFirst = TicTacToeTestHelper.emptyBoard.toString();
            String expectedSecond = MessageFormat.format(yourMoveThreeSquares, 2) + " X.";
            String expectedThird = TicTacToeTestHelper.xInCenter.toString();

            String outputFirst = recorder.popFirstOutput();
            String outputSecond = recorder.popFirstOutput();
            String outputThird = recorder.popFirstOutput();

            assertEquals(expectedFirst, outputFirst);
            assertEquals(expectedSecond, outputSecond);
            assertEquals(expectedThird, outputThird);
        }
    }

    @Test
    public void controllerShouldPassErrorMessageToViewOnInvalidInput() throws GameOverException {
        view.enqueueInput("invalid phrase");

        controller.newGame();

        startRecorder();

        try {
            controller.playRound();
        } catch (NoSuchElementException e) {

            recorder.discardFirstNStrings(1);
            String output = recorder.popFirstOutput();

            assertEquals("That's not a valid board location.", output);

        }


    }

    @Test
    public void controllerShouldPassErrorMessageToViewOnInvalidMove() throws GameOverException {
        view.enqueueInput("middle center");
        view.enqueueInput("middle center");

        controller.newGame();

        startRecorder();

        try {
            controller.playRound();
        } catch (NoSuchElementException e) {

              recorder.discardFirstNStrings(4);
              String output = recorder.popFirstOutput();

              assertEquals("Square is already full.", output);
        }
    }

    @Test
    public void controllerShouldPrintWinnerMessageAfterWin() throws GameOverException {
        view.enqueueInput("n");
        controller.loadGame(TicTacToeTestHelper.playerXWins);

        System.setOut(recorder);

        try {
            controller.checkForGameOver();
        } catch (GameOverException e) {
            String expected = gameOverWin + xWins;

            recorder.discardFirstNStrings(1);
            String output = recorder.popFirstOutput();

            assertEquals(expected, output);
        }
    }

    @Test
    public void controllerShouldLoadPlayers() {
        view.enqueueInput("3");
        view.enqueueInput("c");
        view.enqueueInput("h");

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {

            Player playerOne = controller.getPlayerOne();
            Player playerTwo = controller.getPlayerTwo();

            assertEquals("MinimaxPlayer", playerOne.getClass().getSimpleName());
            assertEquals("HumanPlayer", playerTwo.getClass().getSimpleName());
        }
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

    }

    @Test
    public void setUpRepromptsForInputIfPlayerTypeIsInvalid() {
        controller = new GameController(view);

        view.enqueueInput("3");
        view.enqueueInput("z");
        view.enqueueInput("x");
        view.enqueueInput("f");

        startRecorder();

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {
            String output = recorder.popLastOutput();
            assertEquals(choosePlayerOne, output);
        }
    }

    @Test
    public void controllerShouldPromptForBoardSizeDuringSetup() {
        controller = new GameController(view);

        view.enqueueInput("4");

        startRecorder();

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {
            String output = recorder.popFirstOutput();
            assertEquals(boardSize, output);
        }
    }

    @Test
    public void controllerShouldRepromptForBoardSizeAfterInvalidInput() {
        controller = new GameController(view);

        view.enqueueInput("Kindly create a board with five squares and five columns.");

        startRecorder();

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {
            String output = recorder.popLastOutput();
            assertEquals(boardSize, output);
        }
    }

    @Test
    public void movePromptShouldHaveDifferentMessageBasedOnBoardSize() throws GameOverException {
        controller = new GameController(view);

        view.enqueueInput("3");
        view.enqueueInput("h");
        view.enqueueInput("h");

        startRecorder();

        try {
            controller.setUp();
            controller.startGame();
        } catch (NoSuchElementException e) {
            String output = recorder.popLastOutput();
            String expected = MessageFormat.format(yourMoveThreeSquares, 2) + " X.";
            assertEquals(expected, output);
        }

        view.clearInput();

        controller = new GameController(view);

        view.enqueueInput("4");
        view.enqueueInput("h");
        view.enqueueInput("h");

        startRecorder();

        try {
            controller.setUp();
            controller.startGame();
        } catch (NoSuchElementException e) {
            String output = recorder.popLastOutput();
            String expected = MessageFormat.format(yourMove, 3) + " X.";
             assertEquals(expected, output);
        }

    }

}
