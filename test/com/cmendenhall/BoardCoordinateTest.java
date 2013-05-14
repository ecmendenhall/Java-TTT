package com.cmendenhall;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BoardCoordinateTest {
    private final int X = 1;
    private final int O = 2;
    private final int _ = 0;

    private BoardCoordinate upperRight;
    private BoardCoordinate lowerLeft;
    private BoardCoordinate alsoLowerLeft;

    @Before
    public void setUp() throws InvalidCoordinateException {
        upperRight = new BoardCoordinate(0, 2);
        lowerLeft = new BoardCoordinate("bottom left");
        alsoLowerLeft = new BoardCoordinate("left bottom");
    }

    @Test
    public void rowIsZero() {
        assertEquals((Integer) 0, upperRight.getRow());
    }

    @Test
    public void columnIsTwo() {
        assertEquals((Integer) 2, upperRight.getColumn());
    }

    @Test
    public void coordinateCanBeConstructedFromString() {
        assertEquals((Integer) 2, lowerLeft.getRow());
        assertEquals((Integer) 0, lowerLeft.getColumn());
    }

    @Test
    public void wordOrderDoesNotMatter() {
        assertEquals(lowerLeft.getColumn(), alsoLowerLeft.getColumn());
        assertEquals(lowerLeft.getRow(), alsoLowerLeft.getRow());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidPhraseThrowsException() throws InvalidCoordinateException {
        thrown.expect(InvalidCoordinateException.class);
        BoardCoordinate invalid = new BoardCoordinate("left left");
    }

    @Test
    public void phraseLongerThanTwoWordsThrowsException() throws InvalidCoordinateException {
        thrown.expect(InvalidCoordinateException.class);
        BoardCoordinate invalid = new BoardCoordinate("Please kindly place a piece on the center square");
    }
}