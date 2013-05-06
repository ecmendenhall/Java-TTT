package com.ecmendenhall;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class PlayerTest extends TicTacToeTest {

    private Player playerx;
    private Board board;

    @Before
    public void setUp() throws InvalidPlayerException {
        board = new Board();
        playerx = new Player(1);
    }

    @Test
    public void playerExists() throws InvalidPlayerException {
        Player newplayer = new Player(2);
    }

    @Test
    public void playerXHasSymbol() {
        assertEquals('X', playerx.symbol);
    }

    @Test
    public void playerOHasSymbol() {
        assertEquals('O', playero.symbol);
    }

    @Test
    public void playerCanMove() throws InvalidMoveException {
        board = playerx.move(new BoardCoordinate(0, 0), board);
        assertEquals(X, board.getSquare("top left"));
    }

    @Test
    public void playerCorrectlyScoresWinningBoard() {
        assertEquals(1, playerx.scoreBoard(playerxwins));
    }

    @Test
    public void playerCorrectlyScoresBoardWithoutWin() {
        assertEquals(0, playerx.scoreBoard(nowins));
    }

    @Test
    public void playerCorrectlyScoresLosingBoard() {
        assertEquals(-1, playerx.scoreBoard(playerowins));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void playerWithInvalidGamepieceThrowsException() throws InvalidPlayerException {
        thrown.expect(InvalidPlayerException.class);
        thrown.expectMessage("Invalid player number.");
        Player invalid = new Player(3);
    }
}
