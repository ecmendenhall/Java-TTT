package com.ecmendenhall;
import org.junit.Before;
import org.junit.Test;
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
    public void setUp() {
        board = new Board();
        playerx = new Player(1);
    }

    @Test
    public void playerExists() {
        Player newplayer = new Player(2);
    }

    @Test
    public void playerHasSymbol() {
        assertEquals('X', playerx.symbol);
    }

    @Test
    public void playerCanMove() {
        board = playerx.move(new BoardCoordinate(0, 0), board);
        assertEquals(X, board.getSquare("top left"));
    }
}
