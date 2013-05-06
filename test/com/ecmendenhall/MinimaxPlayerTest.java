package com.ecmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class MinimaxPlayerTest extends TicTacToeTest {

    private MinimaxPlayer playerx;

    public boolean sameBoards(Board expected, Board actual) {
        System.out.println("EXPECTED");
        expected.print();

        System.out.println("ACTUAL");
        actual.print();

        for (int i=0; i< actual.getTop().getSquares().length; i++) {
            if (expected.getTop().getSquare(i) != actual.getTop().getSquare(i)) return false;
        }

        for (int i=0; i< actual.getMiddle().getSquares().length; i++) {
            if (expected.getMiddle().getSquare(i) != actual.getMiddle().getSquare(i)) return false;
        }

        for (int i=0; i< actual.getBottom().getSquares().length; i++) {
            if (expected.getBottom().getSquare(i) != actual.getBottom().getSquare(i)) return false;
        }

        return true;
    }

    @Before
    public void setUp() throws InvalidPlayerException, InvalidMoveException {
        playerx = new MinimaxPlayer(X);
        board = playerx.move(new BoardCoordinate("middle center"), new Board());
    }

    @Test
    public void minimaxPlayerExists() throws InvalidPlayerException {
        new MinimaxPlayer(O);
    }

    @Test
    public void winningBoardScoreIsOne() {
        assertEquals(1, playero.scoreBoard(playerowins));
    }

    @Test
    public void drawBoardScoreIsZero() {
        assertEquals(0, playero.scoreBoard(nowins));
    }

    @Test
    public void losingBoardScoreIsNegativeOne() {
        assertEquals(-1, playero.scoreBoard(playerxwins));
    }
 /*
    @Test
    public void winningMoveScoreIsOne() {
        BoardCoordinate winningmove = new BoardCoordinate("middle right");
        Pair<Board, BoardCoordinate> winningpair = new Pair<Board, BoardCoordinate>(playerocanwin, winningmove);
        assertEquals(1, playero.scoreMove(new GameTree.Node(winningpair)));

        winningmove = new BoardCoordinate("bottom left");
        winningpair = new Pair<Board, BoardCoordinate>(playerxcanwin, winningmove);
        assertEquals(1, playerx.scoreMove(new GameTree.Node(winningpair)));
    }

    @Test
    public void drawnGameMoveScoreIsZero() {
        BoardCoordinate move = new BoardCoordinate("middle left");
        Pair<Board, BoardCoordinate> movepair = new Pair<Board, BoardCoordinate>(willdraw, move);
        assertEquals(0, playero.scoreMove(new GameTree.Node(movepair)));
    }

    @Test
    public void losingPathMoveScoreIsNegativeOne() {
        BoardCoordinate move = new BoardCoordinate("bottom right");
        Pair<Board, BoardCoordinate> movepair = new Pair<Board, BoardCoordinate>(playeronext, move);
        assertEquals(-1, playero.scoreMove(new GameTree.Node(movepair)));
    } */

    @Test
    public void winningMoveXIsBestMove() throws InvalidMoveException {
        Board bestmove = new Board( new Row(X, _, _),
                                    new Row(X, O, _),
                                    new Row(X, _, O) );

        assertTrue(playerx.bestMove(playerxcanwin).equals(bestmove));
    }

    @Test
    public void winningMoveOIsBestMove() throws InvalidMoveException {
        Board bestmove = new Board( new Row(X, _, _),
                                    new Row(O, O, O),
                                    new Row(X, X, _));

        assertTrue(playero.bestMove(playerocanwin).equals(bestmove));
    }

    @Test
    public void blockIsBestMove() throws InvalidMoveException {
        Board bestmove = new Board( new Row(_, X, _),
                                    new Row(_, X, _),
                                    new Row(X, O, O) );

        assertTrue(playerx.bestMove(playerxshouldblock).equals(bestmove));


    }

}