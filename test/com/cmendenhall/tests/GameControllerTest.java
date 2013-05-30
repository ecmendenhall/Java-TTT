package com.cmendenhall.tests;

import com.cmendenhall.controllers.GameController;
import com.cmendenhall.utils.StringLoader;
import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.players.HumanPlayer;
import com.cmendenhall.players.MinimaxPlayer;
import com.cmendenhall.players.Player;
import com.cmendenhall.mocks.MockTerminalView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static com.cmendenhall.TicTacToeSymbols.*;

@RunWith(JUnit4.class)
public class GameControllerTest extends TicTacToeTest {
    private HashMap<String, String> viewStrings = new StringLoader().getViewStrings("/viewstrings.properties");

    private MockTerminalView view = new MockTerminalView();
    private GameController controller = new GameController(view);

    @Before
    public void setUp() throws Exception {
        Player playerOne = new HumanPlayer(X);
        Player playerTwo = new MinimaxPlayer(O);
        controller.setPlayerOne(playerOne);
        controller.setPlayerTwo(playerTwo);
    }

    @Test
    public void controllerShouldStartNewGame() {
        startRecorder();

        controller.newGame();

        assertEquals(viewStrings.get("welcome"),
                     recorder.popFirstOutput());

        assertEquals(viewStrings.get("divider"),
                     recorder.popFirstOutput());
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
        assertEquals(viewStrings.get("playagain"), output);
    }

    @Test
    public void controllerShouldStartNewGameOnRestartIfInputIsYes() throws GameOverException {
        startRecorder();

        view.enqueueInput("y",
                          "3",
                          "h",
                          "h",

                          "top left",
                          "bottom left",
                          "top middle",
                          "bottom middle",
                          "top right",

                          "n");

        try {
            controller.restartGame();
        } catch (GameOverException e) {
            assertEquals(viewStrings.get("playagain"),
                         recorder.popFirstOutput());
        }
    }

    @Test
    public void controllerShouldHandleNextRound() throws GameOverException {
        view.enqueueInput("middle center",
                          "top right",
                          "middle right",
                          "n");

        controller.newGame();

        startRecorder();

        try {
            controller.startGame();
        } catch (Exception e) {
            String expectedFirst = TicTacToeTestHelper.emptyBoard.toString();
            String expectedSecond = MessageFormat.format(viewStrings.get("yourmovethreesquares"), 2) + " X.";
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
        view.enqueueInput("middle center", "middle center");

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

        startRecorder();

        try {
            controller.checkForGameOver();
        } catch (GameOverException e) {
            String expected = viewStrings.get("gameoverwin") + viewStrings.get("xwins");

            recorder.discardFirstNStrings(1);
            String output = recorder.popFirstOutput();

            assertEquals(expected, output);
        }
    }

    @Test
    public void controllerShouldLoadPlayers() {
        view.enqueueInput("3", "c", "h");

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

        view.enqueueInput("3", "h", "h");

        controller.setUp();

        view.enqueueInput("middle center",
                          "top left",
                          "top right",
                          "middle left",
                          "lower right",
                          "lower left",

                          "n");

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

        view.enqueueInput("3", "h", "c");

        controller.setUp();

        Player playerOne = controller.getPlayerOne();
        Player playerTwo = controller.getPlayerTwo();

        assertEquals("HumanPlayer", playerOne.getClass().getSimpleName());
        assertEquals("MinimaxPlayer", playerTwo.getClass().getSimpleName());

    }

    @Test
    public void setUpRepromptsForInputIfPlayerTypeIsInvalid() {
        controller = new GameController(view);

        view.enqueueInput("3", "z", "x", "f");

        startRecorder();

        try {
            controller.setUp();
        } catch (NoSuchElementException e) {
            String output = recorder.popLastOutput();
            assertEquals(viewStrings.get("chooseplayerone"), output);
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
            assertEquals(viewStrings.get("boardsize"), output);
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
            assertEquals(viewStrings.get("boardsize"), output);
        }
    }

    @Test
    public void movePromptShouldHaveDifferentMessageBasedOnBoardSize() throws GameOverException {
        controller = new GameController(view);

        view.enqueueInput("3", "h", "h");

        startRecorder();

        try {
            controller.setUp();
            controller.startGame();
        } catch (NoSuchElementException e) {
            String output = recorder.popLastOutput();
            String expected = MessageFormat.format(viewStrings.get("yourmovethreesquares"), 2) + " X.";
            assertEquals(expected, output);
        }

        view.clearInput();

        controller = new GameController(view);

        view.enqueueInput("4", "h", "h");

        startRecorder();

        try {
            controller.setUp();
            controller.startGame();
        } catch (NoSuchElementException e) {
            String output = recorder.popLastOutput();
            String expected = MessageFormat.format(viewStrings.get("yourmove"), 3) + " X.";
             assertEquals(expected, output);
        }

    }

}
