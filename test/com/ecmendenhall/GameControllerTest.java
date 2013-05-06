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
    private GameController gamecontroller;
    private TerminalView terminalview;
    private Board topleftresponse;
    private Board toprightresponse;
    private Board playerxtolowerright;

    @Before
    public void setUp() throws InvalidPlayerException {
        gamecontroller = new GameController();
        terminalview = new TerminalView(true);

        topleftresponse = new Board( new Row(X, _, _),
                                     new Row(_, O, _),
                                     new Row(_, _, _) );

        toprightresponse = new Board( new Row(_, _, X),
                                      new Row(_, O, _),
                                      new Row(_, _, _) );

        playerxtolowerright = new Board( new Row(_, _, X),
                                         new Row(_, O, _),
                                         new Row(_, _, _) );
    }

    @Test
    public void gameControllerShouldStoreCurrentBoard() {
        Board startingboard = gamecontroller.getCurrentBoard();
        assertTrue(startingboard.equals(emptyboard));
    }

    @Test
    public void gameControllerShouldGetPlayerMoveFromView() throws Exception {
        assertTrue(gamecontroller.getCurrentBoard().equals(emptyboard));

        terminalview.io.setTestInput("top left");
        terminalview.passMoveToController(new BoardCoordinate("top left"), gamecontroller, true);
        assertTrue(gamecontroller.getCurrentBoard().equals(topleftresponse));
    }

    @Test
    public void gameControllerShouldPassNewBoardToView() throws Exception {
        System.setOut(new PrintStream(output));

        gamecontroller = new GameController();
        terminalview = new TerminalView(true);
        gamecontroller.passNewBoardToView(topleftresponse, terminalview, true);

        assertEquals(topleftresponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldPassCorrectResponseToView() throws InvalidPlayerException, InvalidMoveException, IOException {
        System.setOut(new PrintStream(output));

        gamecontroller = new GameController();
        gamecontroller.processMove(new BoardCoordinate("top right"), terminalview, true);
        assertTrue(toprightresponse.equals(gamecontroller.getCurrentBoard()));

        gamecontroller.passNewBoardToView(gamecontroller.getCurrentBoard(), terminalview, true);
        assertEquals(toprightresponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldStartNewGame() throws Exception {
        System.setOut(new PrintStream(output));
        terminalview.io.setTestInput("middle center");

        gamecontroller.newGame(terminalview, true);
        assertEquals(gamecontroller.getCurrentBoard().toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldCatchInvalidMoves() throws Exception {

        terminalview.io.setTestInput("lower left");
        gamecontroller.newGame(terminalview, true);
        BoardCoordinate nextmove = terminalview.prompt();
        terminalview.passMoveToController(nextmove, gamecontroller, true);

        System.setOut(new PrintStream(output));

        gamecontroller.processMove(new BoardCoordinate("lower left"),
                                   terminalview,
                                   true);

        assertEquals("Square is already full.\n" + gamecontroller.getCurrentBoard(), output.toString());

        System.setOut(stdout);
    }
}
