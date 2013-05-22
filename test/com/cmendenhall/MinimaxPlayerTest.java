package com.cmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MinimaxPlayerTest extends TicTacToeTest {

    private MinimaxPlayer playerX;

    @Before
    public void setUp() throws InvalidCoordinateException, InvalidMoveException {
        playerX = new MinimaxPlayer(X);
        board = playerX.move(new ThreeByThreeBoardCoordinate("middle center"), new GameBoard());
    }

    @Test
    public void minimaxPlayerExists() {
        new MinimaxPlayer(O);
    }

    @Test
    public void winningBoardScoreIsOne() {
        assertEquals(1, playerO.scoreBoard(playerOWins));
    }

    @Test
    public void drawBoardScoreIsZero() {
        assertEquals(0, playerO.scoreBoard(noWins));
    }

    @Test
    public void losingBoardScoreIsNegativeOne() {
        assertEquals(-1, playerO.scoreBoard(playerXWins));
    }

    @Test
    public void winningMoveXIsBestMove() {
        Board expected = new GameBoard( new Row(X, _, _),
                                        new Row(X, O, _),
                                        new Row(X, _, O) );

        Board actual = playerX.bestMove(playerXCanWin);
        assertSameBoard(expected, actual);
    }

    @Test
    public void winningMoveOIsBestMove() {
        Board expected = new GameBoard( new Row(X, _, _),
                                        new Row(O, O, O),
                                        new Row(X, X, _));

        Board actual = playerO.bestMove(playerOCanWin);
        assertSameBoard(expected, actual);
    }

    @Test
    public void blockIsBestMove() {
        Board expected = new GameBoard( new Row(_, X, _),
                                        new Row(_, X, _),
                                        new Row(X, O, O) );

        Board actual = playerX.bestMove(playerXShouldBlock);
        assertSameBoard(expected, actual);

    }

    @Test
    public void threeByThreeLookAheadDepthIsEight() {
        assertEquals(8, playerX.calculateDepth(xInCenter));
    }

    @Test
    public void fourByFourLookAheadDepthIsFour() throws InvalidBoardException {
        assertEquals(4, playerX.calculateDepth(new GameBoard(4)));
    }

    @Test
    public void lookAheadDepthIsAlwaysAtLeastTwo() throws InvalidBoardException {
        assertEquals(2, playerX.calculateDepth(new GameBoard(9)));
        assertEquals(2, playerX.calculateDepth(new GameBoard(10)));
    }

    @Test
    public void fullBoardIsTerminalNode() {
        assertTrue(playerX.isTerminal(noWins));

    }

    @Test
    public void winningBoardIsTerminalNode() {
        assertTrue(playerX.isTerminal(playerXWins));
    }

    @Test
    public void boardWithEmptySpacesAndNoWinIsNotTerminal() {
        assertFalse(playerX.isTerminal(playerONext));
    }

    @Test
    public void playerXCanWaitFiveTurnsOnFourByFourBoard() throws InvalidBoardException {
        int randomMoves = playerX.randomMoveLimit(new GameBoard(4));
        assertEquals(5, randomMoves);
    }

    @Test
    public void playerXCanWaitFourTurnsOnThreeByThreeBoard() {
        int randomMoves = playerX.randomMoveLimit(new GameBoard());
        assertEquals(4, randomMoves);
    }

    @Test
    public void playerOCanWaitThreeTurnsOnThreeByThreeBoard() {
        int randomMoves = playerO.randomMoveLimit(new GameBoard());
        assertEquals(3, randomMoves);
    }

    @Test
    public void playerOCanWaitFourTurnsOnFourByFourBoard() throws InvalidBoardException {
        int randomMoves = playerO.randomMoveLimit(new GameBoard(4));
        assertEquals(4, randomMoves);
    }

    private void playGame(Integer boardSize) throws UnsupportedEncodingException {
        PrintStream stdout = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        OutputRecorder recorder = new OutputRecorder(output, true, "UTF-8");

        MockTerminalView view = new MockTerminalView();
        Controller controller = new GameController(view);

        view.enqueueInput(boardSize.toString());
        view.enqueueInput("c");
        view.enqueueInput("c");

        System.setOut(recorder);

        try {
            controller.setUp();
            controller.newGame();
            controller.startGame();
        } catch (Exception e) {
            recorder.popLastOutput();
            String itsADraw = recorder.popLastOutput();
            assertEquals("Game over: It's a draw.", itsADraw);
        }

        System.setOut(stdout);
    }

    @Test
    public void minimaxPlayersShouldAlwaysTie() throws UnsupportedEncodingException {
        for (int i=0; i < 10; i++) {
            playGame(3);
        }

        for (int i=0; i < 10; i++) {
            playGame(4);
            playGame(5);
        }
    }

}