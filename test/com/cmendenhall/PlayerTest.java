package com.cmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PlayerTest extends TicTacToeTest {

    private Player playerX;
    private Board board;

    @Before
    public void setUp() {
        board = new GameBoard();
        playerX = new HumanPlayer(X);
    }

    @Test
    public void playerExists() {
        Player newplayer = new HumanPlayer(O);
    }

    @Test
    public void playerXHasSymbol() {
        assertEquals('X', playerX.getSymbol());
    }

    @Test
    public void playerOHasSymbol() {
        assertEquals('O', playerO.getSymbol());
    }

    @Test
    public void playerCanMove() throws InvalidMoveException, InvalidCoordinateException {
        board = playerX.move(new UniversalBoardCoordinate(0, 0), board);
        int topleft = board.getRows().get(0).getSquare(0);
        assertEquals(X, topleft);
    }

    @Test
    public void playerCorrectlyScoresWinningBoard() {
        assertEquals(-1, playerO.scoreBoard(playerXWins));
    }

    @Test
    public void playerCorrectlyScoresBoardWithoutWin() {
        assertEquals(0, playerO.scoreBoard(noWins));
    }

    @Test
    public void playerCorrectlyScoresLosingBoard() {
        assertEquals(1, playerO.scoreBoard(playerOWins));
    }
}
