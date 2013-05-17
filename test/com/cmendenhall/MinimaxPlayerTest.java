package com.cmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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

}