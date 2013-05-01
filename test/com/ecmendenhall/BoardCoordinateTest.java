package com.ecmendenhall;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class BoardCoordinateTest {
    private final int X = 1;
    private final int O = 2;
    private final int _ = 0;

    private Board board;
    private Player playerx;
    private BoardCoordinate upperright;
    private BoardCoordinate lowerleft;

    @Before
    public void setUp() {
        board = new Board();
        playerx = new Player(X);
        upperright = new BoardCoordinate(0, 2);
        lowerleft = new BoardCoordinate("bottom left");
    }

    @Test
    public void rowIsZero() {
        Assert.assertEquals(0, upperright.row);
    }

    @Test
    public void columnIsTwo() {
        Assert.assertEquals(2, upperright.column);
    }

    @Test
    public void coordinateCanBeConstructedFromString() {
        Assert.assertEquals(2, lowerleft.row);
        Assert.assertEquals(0, lowerleft.column);
    }
}