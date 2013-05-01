package com.ecmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class PlayerTest {
    private final int X = 1;
    private final int O = 2;
    private final int _ = 0;

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
        Assert.assertEquals('X', playerx.symbol);
    }

    @Test
    public void playerCanMove() {
        playerx.move(new BoardCoordinate(0, 0), board);
        Assert.assertEquals(X, board.getSquare("top left"));
    }
}
