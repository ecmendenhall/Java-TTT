package com.ecmendenhall;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

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

        assertEquals(topLeftResponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldPassCorrectResponseToView() throws InvalidPlayerException, InvalidMoveException, IOException {
        System.setOut(new PrintStream(output));

        gameController = new GameController();
        gameController.processMove(new BoardCoordinate("top right"), terminalView, true);
        assertTrue(topRightResponse.equals(gameController.getCurrentBoard()));

        gameController.passNewBoardToView(gameController.getCurrentBoard(), terminalView, true);
        assertEquals(topRightResponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldStartNewGame() throws Exception {
        System.setOut(new PrintStream(output));
        terminalView.io.setTestInput("middle center");

        gameController.newGame(terminalView, true);
        assertEquals(gameController.getCurrentBoard().toString(), output.toString());

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

        assertEquals("Square is already full.\n" + gameController.getCurrentBoard(), output.toString());

        System.setOut(stdout);
    }
}
