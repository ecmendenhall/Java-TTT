package com.cmendenhall.tests;

import com.cmendenhall.board.BoardAnalyzer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static com.cmendenhall.TicTacToeSymbols.*;


@RunWith(JUnit4.class)
public class BoardAnalyzerTest {

    @Test
    public void boardHasHorizontalWin() {
        assertTrue(BoardAnalyzer.hasWin(TicTacToeTest.playerOWins));
    }

    @Test
    public void boardHasVerticalWin() {
        assertTrue(BoardAnalyzer.hasWin(TicTacToeTest.playerXWins));
    }

    @Test
    public void drawBoardDoesNotHaveWin() {
        assertFalse(BoardAnalyzer.hasWin(TicTacToeTest.noWins));
    }

    @Test
    public void boardHasDiagonalWin() {
        assertTrue(BoardAnalyzer.hasWin(TicTacToeTest.diagonal));
    }

    @Test
    public void noWinsOnNewBoard() {
        assertFalse(BoardAnalyzer.hasWin(TicTacToeTest.emptyBoard));
    }


    @Test
    public void emptyBoardIsNotFull() {
        assertFalse(BoardAnalyzer.isFull(TicTacToeTest.emptyBoard));
    }

    @Test
    public void partiallyFullBoardIsNotFull() {
        assertFalse(BoardAnalyzer.isFull(TicTacToeTest.playerONext));
        assertFalse(BoardAnalyzer.isFull(TicTacToeTest.playerXShouldBlock));
    }

    @Test
    public void noWinsIsFull() {
        assertTrue(BoardAnalyzer.isFull(TicTacToeTest.noWins));
    }

    @Test
    public void noWinsHasNoWinner() {
        assertEquals(_, BoardAnalyzer.winnerIs(TicTacToeTest.noWins));
    }

    @Test
    public void playerXWinsWinnerIsX() {
        assertEquals(X, BoardAnalyzer.winnerIs(TicTacToeTest.playerXWins));
    }

    @Test
    public void playerOWinsWinnerIsO() {
        assertEquals(O, BoardAnalyzer.winnerIs(TicTacToeTest.playerOWins));
    }

    @Test
    public void playerXMovesFirst() {
        assertEquals(X, BoardAnalyzer.nextTurn(TicTacToeTest.emptyBoard));
    }

    @Test
    public void playerOMovesNext() {
        assertEquals(O, BoardAnalyzer.nextTurn(TicTacToeTest.playerONext));
    }

    @Test
    public void fullBoardHasNoNextTurn() {
        assertEquals(_, BoardAnalyzer.nextTurn(TicTacToeTest.noWins));
    }

    @Test
    public void emptyBoardSumIsZero() {
        assertEquals(0, BoardAnalyzer.sum(TicTacToeTest.emptyBoard));
    }

    @Test
    public void noWinsBoardSumIsThirteen() {
        assertEquals(13, BoardAnalyzer.sum(TicTacToeTest.noWins));
    }

    @Test
    public void playerXWinsBoardSumIsSeven() {
        assertEquals(7, BoardAnalyzer.sum(TicTacToeTest.playerXWins));
    }

    @Test
    public void emptyBoardIsEmpty() {
        assertTrue(BoardAnalyzer.isEmpty(TicTacToeTest.emptyBoard));
    }

    @Test
    public void turnsPlayedOnEmptyBoardEqualsZero() {
        int played = BoardAnalyzer.turnsPlayed(TicTacToeTest.emptyBoard);
        assertEquals(0, played);
    }

    @Test
    public void turnsPlayedOnBoardWithOneMoveEqualsOne() {
        int played = BoardAnalyzer.turnsPlayed(TicTacToeTest.xInCenter);
        assertEquals(1, played);
    }

    @Test
    public void turnsPlayedOnBoardWithThreeMovesEqualsThree() {
       int played = BoardAnalyzer.turnsPlayed(TicTacToeTest.playerONext);
       assertEquals(3, played);
    }

    @Test
    public void turnsPlayedOnFullBoardEqualsNine() {
       int played = BoardAnalyzer.turnsPlayed(TicTacToeTest.noWins);
       assertEquals(9, played);
    }
}
