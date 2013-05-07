package com.cmendenhall;
import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class GameControllerTest extends TicTacToeTest {
    private GameController gameController;
    private TerminalView terminalView;
    private Board topLeftResponse;
    private Board topRightResponse;
    private Board playerXToLowerRight;

    @Before
    public void setUp() throws InvalidPlayerException {
        gameController = new GameController();
        terminalView = new TerminalView(true);

        topLeftResponse = new Board( new Row(X, _, _),
                                     new Row(_, O, _),
                                     new Row(_, _, _) );

        topRightResponse = new Board( new Row(_, _, X),
                                      new Row(_, O, _),
                                      new Row(_, _, _) );

        playerXToLowerRight = new Board( new Row(_, _, X),
                                         new Row(_, O, _),
                                         new Row(_, _, _) );
    }

    @Test
    public void gameControllerShouldStoreCurrentBoard() {
        Board startingBoard = gameController.getCurrentBoard();
        assertTrue(startingBoard.equals(emptyBoard));
    }

    @Test
    public void gameControllerShouldGetPlayerMoveFromView() throws Exception {
        assertTrue(gameController.getCurrentBoard().equals(emptyBoard));

        terminalView.io.setTestInput("top left");
        terminalView.passMoveToController(new BoardCoordinate("top left"), gameController, true);
        assertTrue(gameController.getCurrentBoard().equals(topLeftResponse));
    }

    @Test
    public void gameControllerShouldPassNewBoardToView() throws Exception {
        System.setOut(new PrintStream(output));

        gameController = new GameController();
        terminalView = new TerminalView(true);
        gameController.passNewBoardToView(topLeftResponse, terminalView, true);

        assertEquals("\n" + topLeftResponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldPassCorrectResponseToView() throws Exception {
        System.setOut(new PrintStream(output));

        gameController = new GameController();
        gameController.processMove(new BoardCoordinate("top right"), terminalView, true);
        assertTrue(topRightResponse.equals(gameController.getCurrentBoard()));

        gameController.passNewBoardToView(gameController.getCurrentBoard(), terminalView, true);
        assertEquals("\n" + topRightResponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldStartNewGame() throws Exception {
        System.setOut(new PrintStream(output));
        terminalView.io.setTestInput("middle center");

        gameController.newGame(terminalView, true);

        String expected = "Welcome to Tic-Tac-Toe.\n" +
                           HORIZONTAL_DIVIDER + "\n" +
                           "You are player X.\n\n" +
                           "Please enter your move as\n" +
                           "a natural phrase, like\n" +
                           "'Top left,' 'Lower right,'\n" +
                           "or 'Middle center.'\n\n" +
                           gameController.getCurrentBoard().toString();

        assertEquals(expected, output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldCatchInvalidMoves() throws Exception {

        terminalView.io.setTestInput("lower left");
        gameController.newGame(terminalView, true);
        BoardCoordinate nextMove = terminalView.prompt();
        terminalView.passMoveToController(nextMove, gameController, true);

        System.setOut(new PrintStream(output));

        gameController.processMove(new BoardCoordinate("lower left"),
                terminalView,
                true);

        assertEquals("Square is already full.\n\n" + gameController.getCurrentBoard(), output.toString());

        System.setOut(stdout);
    }

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void gameControllerShouldCheckForWins() throws Exception {
        exit.expectSystemExitWithStatus(0);

        terminalView.io.setTestInput("lower left");

        System.setOut(new PrintStream(output));
        gameController = new GameController(playerXCanWin);
        BoardCoordinate nextMove = terminalView.prompt();

        terminalView.io.setTestInput("n");
        terminalView.passMoveToController(nextMove, gameController);


        assertEquals(gameController.getCurrentBoard() +
                     "Game over: Player X wins.", output.toString());


        System.setOut(stdout);
    }

    @Rule
    public final ExpectedSystemExit drawExit = ExpectedSystemExit.none();

    @Test
    public void gameControllerShouldCheckForEndStates() throws Exception {

        exit.expectSystemExitWithStatus(0);

        terminalView.io.setTestInput("lower left");

        System.setOut(new PrintStream(output));
        gameController = new GameController(noWins);
        BoardCoordinate nextMove = terminalView.prompt();

        terminalView.io.setTestInput("n");
        terminalView.passMoveToController(nextMove, gameController);

        assertEquals(gameController.getCurrentBoard() +
                     "Game over: It's a draw.", output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldCatchInvalidBoardCoordinates() throws Exception {
        terminalView.io.setTestInput("THIS IS NOT A VALID MOVE");

        gameController.newGame(terminalView, true);
        System.setOut(new PrintStream(output));
        BoardCoordinate nextMove = terminalView.prompt(true);
        terminalView.passMoveToController(nextMove, gameController, true);

        assertEquals("That's not a valid board location.\n" +
                     "Invalid move coordinate.\n\n" +
                     gameController.getCurrentBoard(),
                     output.toString());

        System.setOut(stdout);
    }


    @Test
    public void gameControllerShouldPromptForRestartAfterEndGame() throws Exception {
        exit.expectSystemExitWithStatus(0);
        terminalView.io.setTestInput("lower left");

        gameController = new GameController(noWins);
        BoardCoordinate nextMove = terminalView.prompt();

        terminalView.io.setTestInput("n");

        System.setOut(new PrintStream(output));
        terminalView.passMoveToController(nextMove, gameController);

        assertEquals("Play again? (y/n): ", output.toString());

        System.setOut(stdout);

    }
}
