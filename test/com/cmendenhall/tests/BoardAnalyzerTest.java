package com.cmendenhall.tests;

import com.cmendenhall.board.Board;
import com.cmendenhall.board.BoardAnalyzer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;
import static com.cmendenhall.TicTacToeSymbols.*;


@RunWith(JUnit4.class)
public class BoardAnalyzerTest extends TicTacToeTest {

    @Test
    public void boardHasHorizontalWin() {
        assertTrue(BoardAnalyzer.hasWin(TicTacToeTestHelper.playerOWins));
    }

    @Test
    public void boardHasVerticalWin() {
        assertTrue(BoardAnalyzer.hasWin(TicTacToeTestHelper.playerXWins));
    }

    @Test
    public void drawBoardDoesNotHaveWin() {
        assertFalse(BoardAnalyzer.hasWin(TicTacToeTestHelper.noWins));
    }

    @Test
    public void boardHasDiagonalWin() {
        assertTrue(BoardAnalyzer.hasWin(TicTacToeTestHelper.diagonal));
    }

    @Test
    public void noWinsOnNewBoard() {
        assertFalse(BoardAnalyzer.hasWin(TicTacToeTestHelper.emptyBoard));
    }


    @Test
    public void emptyBoardIsNotFull() {
        assertFalse(BoardAnalyzer.isFull(TicTacToeTestHelper.emptyBoard));
    }

    @Test
    public void partiallyFullBoardIsNotFull() {
        assertFalse(BoardAnalyzer.isFull(TicTacToeTestHelper.playerONext));
        assertFalse(BoardAnalyzer.isFull(TicTacToeTestHelper.playerXShouldBlock));
    }

    @Test
    public void noWinsIsFull() {
        assertTrue(BoardAnalyzer.isFull(TicTacToeTestHelper.noWins));
    }

    @Test
    public void noWinsHasNoWinner() {
        assertEquals(_, BoardAnalyzer.winnerIs(TicTacToeTestHelper.noWins));
    }

    @Test
    public void playerXWinsWinnerIsX() {
        assertEquals(X, BoardAnalyzer.winnerIs(TicTacToeTestHelper.playerXWins));
    }

    @Test
    public void playerOWinsWinnerIsO() {
        assertEquals(O, BoardAnalyzer.winnerIs(TicTacToeTestHelper.playerOWins));
    }

    @Test
    public void playerXMovesFirst() {
        assertEquals(X, BoardAnalyzer.nextTurn(TicTacToeTestHelper.emptyBoard));
    }

    @Test
    public void playerOMovesNext() {
        assertEquals(O, BoardAnalyzer.nextTurn(TicTacToeTestHelper.playerONext));
    }

    @Test
    public void fullBoardHasNoNextTurn() {
        assertEquals(_, BoardAnalyzer.nextTurn(TicTacToeTestHelper.noWins));
    }

    @Test
    public void emptyBoardSumIsZero() {
        assertEquals(0, BoardAnalyzer.sum(TicTacToeTestHelper.emptyBoard));
    }

    @Test
    public void noWinsBoardSumIsThirteen() {
        assertEquals(13, BoardAnalyzer.sum(TicTacToeTestHelper.noWins));
    }

    @Test
    public void playerXWinsBoardSumIsSeven() {
        assertEquals(7, BoardAnalyzer.sum(TicTacToeTestHelper.playerXWins));
    }

    @Test
    public void emptyBoardIsEmpty() {
        assertTrue(BoardAnalyzer.isEmpty(TicTacToeTestHelper.emptyBoard));
    }

    @Test
    public void turnsPlayedOnEmptyBoardEqualsZero() {
        int played = BoardAnalyzer.turnsPlayed(TicTacToeTestHelper.emptyBoard);
        assertEquals(0, played);
    }

    @Test
    public void turnsPlayedOnBoardWithOneMoveEqualsOne() {
        int played = BoardAnalyzer.turnsPlayed(TicTacToeTestHelper.xInCenter);
        assertEquals(1, played);
    }

    @Test
    public void turnsPlayedOnBoardWithThreeMovesEqualsThree() {
       int played = BoardAnalyzer.turnsPlayed(TicTacToeTestHelper.playerONext);
       assertEquals(3, played);
    }

    @Test
    public void turnsPlayedOnFullBoardEqualsNine() {
       int played = BoardAnalyzer.turnsPlayed(TicTacToeTestHelper.noWins);
       assertEquals(9, played);
    }

    @Test
    public void nextStatesIsEmptyWhenBoardIsFull() {
        List<Board> nextStates = BoardAnalyzer.getNextStates(TicTacToeTestHelper.noWins);
        assertEquals(0, nextStates.size());
    }
}
