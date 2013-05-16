package com.cmendenhall;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UniversalBoardCoordinateTest {
    private BoardCoordinate upperRight;
    private BoardCoordinate lowerLeft;
    private BoardCoordinate alsoLowerLeft;

    @Before
    public void setUp() throws InvalidCoordinateException {
        upperRight = new UniversalBoardCoordinate(0, 2);
        lowerLeft = new UniversalBoardCoordinate("2, 0");
        alsoLowerLeft = new UniversalBoardCoordinate("(2, 0)");
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

    @Test(expected = InvalidCoordinateException.class)
    public void invalidPhraseThrowsException() throws InvalidCoordinateException {
        BoardCoordinate invalid = new BoardCoordinate("5, 10");
    }

    @Test(expected = InvalidCoordinateException.class)
    public void phraseWithMoreThanTwoCoordinatesThrowsException() throws InvalidCoordinateException {
        BoardCoordinate invalid = new BoardCoordinate("0, 1, 2");
    }

}