package com.cmendenhall;

import org.junit.*;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;


@RunWith(JUnit4.class)
public class BoardAnalyzerTest extends TicTacToeTest {

    @Test
    public void boardHasHorizontalWin() {
        assertTrue(BoardAnalyzer.hasWin(playerOWins));
    }

    @Test
    public void boardHasVerticalWin() {
        assertTrue(BoardAnalyzer.hasWin(playerXWins));
    }

    @Test
    public void drawBoardDoesNotHaveWin() {
        assertFalse(BoardAnalyzer.hasWin(noWins));
    }

    @Test
    public void boardHasDiagonalWin() {
        assertTrue(BoardAnalyzer.hasWin(diagonal));
    }

    @Test
    public void noWinsOnNewBoard() {
        assertFalse(BoardAnalyzer.hasWin(emptyBoard));
    }


    @Test
    public void emptyBoardIsNotFull() {
        assertFalse(BoardAnalyzer.isFull(emptyBoard));
    }

    @Test
    public void partiallyFullBoardIsNotFull() {
        assertFalse(BoardAnalyzer.isFull(playerONext));
        assertFalse(BoardAnalyzer.isFull(playerXShouldBlock));
    }

    @Test
    public void noWinsIsFull() {
        assertTrue(BoardAnalyzer.isFull(noWins));
    }

    @Test
    public void noWinsHasNoWinner() {
        assertEquals(_, BoardAnalyzer.winnerIs(noWins));
    }

    @Test
    public void playerXWinsWinnerIsX() {
        assertEquals(X, BoardAnalyzer.winnerIs(playerXWins));
    }

    @Test
    public void playerOWinsWinnerIsO() {
        assertEquals(O, BoardAnalyzer.winnerIs(playerOWins));
    }

    @Test
    public void playerXMovesFirst() {
        assertEquals(X, BoardAnalyzer.nextTurn(emptyBoard));
    }

    @Test
    public void playerOMovesNext() {
        assertEquals(O, BoardAnalyzer.nextTurn(playerONext));
    }

    @Test
    public void fullBoardHasNoNextTurn() {
        assertEquals(_, BoardAnalyzer.nextTurn(noWins));
    }

    @Test
    public void emptyBoardSumIsZero() {
        assertEquals(0, BoardAnalyzer.sum(emptyBoard));
    }

    @Test
    public void noWinsBoardSumIsThirteen() {
        assertEquals(13, BoardAnalyzer.sum(noWins));
    }

    @Test
    public void playerXWinsBoardSumIsSeven() {
        assertEquals(7, BoardAnalyzer.sum(playerXWins));
    }
}
