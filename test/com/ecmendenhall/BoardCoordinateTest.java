package com.ecmendenhall;

import org.junit.Test;
import org.junit.Before;

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
public class BoardCoordinateTest {
    private final int X = 1;
    private final int O = 2;
    private final int _ = 0;

    private Board board;
    private Player playerx;
    private BoardCoordinate upperright;
    private BoardCoordinate lowerleft;

    @Before
    public void setUp() throws InvalidPlayerException {
        board = new Board();
        playerx = new Player(X);
        upperright = new BoardCoordinate(0, 2);
        lowerleft = new BoardCoordinate("bottom left");
    }

    @Test
    public void rowIsZero() {
        assertEquals(0, upperright.getRow());
    }

    @Test
    public void columnIsTwo() {
        assertEquals(2, upperright.getColumn());
    }

    @Test
    public void coordinateCanBeConstructedFromString() {
        assertEquals(2, lowerleft.getRow());
        assertEquals(0, lowerleft.getColumn());
    }
}