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

    private Player playerx;
    private MinimaxPlayer playero;
    private Board board;
    private Board playerocanwin;
    private Board playerxcanwin;

    public boolean sameBoards(Board expected, Board actual) {
        System.out.println("EXPECTED");
        expected.print();

        System.out.println("ACTUAL");
        actual.print();

        for (int i=0; i< actual.top.squares.length; i++) {
            if (expected.top.squares[i] != actual.top.squares[i]) return false;
        }

        for (int i=0; i< actual.middle.squares.length; i++) {
            if (expected.middle.squares[i] != actual.middle.squares[i]) return false;
        }

        for (int i=0; i< actual.bottom.squares.length; i++) {
            if (expected.bottom.squares[i] != actual.bottom.squares[i]) return false;
        }

        return true;
    }

    @Before
    public void setUp() {
        playerx = new Player(X);
        playero = new MinimaxPlayer(O);

        board = playerx.move(new BoardCoordinate("middle center"), new Board());

        playerocanwin = new Board( new Row(X, _, _),
                                   new Row(O, O, _),
                                   new Row(X, X, _) );

        playerxcanwin = new Board( new Row(X, _, _),
                                   new Row(X, O, _),
                                   new Row(_, _, O) );
    }

    @Test
    public void minimaxPlayerExists() {
        new MinimaxPlayer(O);
    }

    @Test
    public void winningMoveShouldHaveHighestScore() {
        List<Pair<Integer, BoardCoordinate>> sortedmoves = playero.scoreNextMoves(playerocanwin);
        Pair<Integer, BoardCoordinate> bestpair = sortedmoves.get(0);
        int highscore = bestpair.first;
        BoardCoordinate bestmove = bestpair.rest;
        assertEquals(1, highscore);
        assertEquals(1, bestmove.column);
        assertEquals(0, bestmove.row);
    }

    @Test
    public void winningMoveShouldBeBest() {
        BoardCoordinate bestmove = playero.bestMove(playerocanwin);
        assertEquals(2, bestmove.column);
        assertEquals(1, bestmove.row);
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

    @Test
    public void playerOCanWinBoardScoreIsOne() {
        assertEquals(new Integer(1), playero.scoreMove(playerocanwin));
    }
}