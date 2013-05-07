package com.cmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MinimaxPlayerTest extends TicTacToeTest {

    private MinimaxPlayer playerX;

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
    public void setUp() throws InvalidPlayerException, InvalidMoveException, InvalidCoordinateException {
        playerX = new MinimaxPlayer(X);
        board = playerX.move(new BoardCoordinate("middle center"), new Board());
    }

    @Test
    public void minimaxPlayerExists() throws InvalidPlayerException {
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
 /*
    @Test
    public void winningMoveScoreIsOne() {
        BoardCoordinate winningmove = new BoardCoordinate("middle right");
        Pair<Board, BoardCoordinate> winningpair = new Pair<Board, BoardCoordinate>(playerOCanWin, winningmove);
        assertEquals(1, playerO.scoreMove(new GameTree.Node(winningpair)));

        winningmove = new BoardCoordinate("bottom left");
        winningpair = new Pair<Board, BoardCoordinate>(playerXCanWin, winningmove);
        assertEquals(1, playerX.scoreMove(new GameTree.Node(winningpair)));
    }

    @Test
    public void drawnGameMoveScoreIsZero() {
        BoardCoordinate move = new BoardCoordinate("middle left");
        Pair<Board, BoardCoordinate> movepair = new Pair<Board, BoardCoordinate>(willDraw, move);
        assertEquals(0, playerO.scoreMove(new GameTree.Node(movepair)));
    }

    @Test
    public void losingPathMoveScoreIsNegativeOne() {
        BoardCoordinate move = new BoardCoordinate("bottom right");
        Pair<Board, BoardCoordinate> movepair = new Pair<Board, BoardCoordinate>(playerONext, move);
        assertEquals(-1, playerO.scoreMove(new GameTree.Node(movepair)));
    } */

    @Test
    public void winningMoveXIsBestMove() throws InvalidMoveException {
        Board bestMove = new Board( new Row(X, _, _),
                                    new Row(X, O, _),
                                    new Row(X, _, O) );

        assertTrue(playerX.bestMove(playerXCanWin).equals(bestMove));
    }

    @Test
    public void winningMoveOIsBestMove() throws InvalidMoveException {
        Board bestMove = new Board( new Row(X, _, _),
                                    new Row(O, O, O),
                                    new Row(X, X, _));

        assertTrue(playerO.bestMove(playerOCanWin).equals(bestMove));
    }

    @Test
    public void blockIsBestMove() throws InvalidMoveException {
        Board bestMove = new Board( new Row(_, X, _),
                                    new Row(_, X, _),
                                    new Row(X, O, O) );

        assertTrue(playerX.bestMove(playerXShouldBlock).equals(bestMove));


    }

}