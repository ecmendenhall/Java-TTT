package com.cmendenhall.tests;

import com.cmendenhall.board.ThreeByThreeBoardCoordinate;
import com.cmendenhall.exceptions.InvalidCoordinateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ThreeByThreeBoardCoordinateTest extends UniversalBoardCoordinateTest {

    private ThreeByThreeBoardCoordinate lowerLeft;
    private ThreeByThreeBoardCoordinate alsoLowerLeft;

    @Before
    public void setUp() throws InvalidCoordinateException {
        lowerLeft = new ThreeByThreeBoardCoordinate("bottom left");
        alsoLowerLeft = new ThreeByThreeBoardCoordinate("left bottom");
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

    @Test(expected = InvalidCoordinateException.class)
    public void invalidPhraseThrowsException() throws InvalidCoordinateException {
        ThreeByThreeBoardCoordinate invalid = new ThreeByThreeBoardCoordinate("left left");
    }

    @Test(expected = InvalidCoordinateException.class)
    public void tooManyCoordinatesThrowsException() throws InvalidCoordinateException {
        ThreeByThreeBoardCoordinate invalid = new ThreeByThreeBoardCoordinate("(1, 2, 3)");
    }

    @Test(expected = InvalidCoordinateException.class)
    public void phraseLongerThanTwoWordsThrowsException() throws InvalidCoordinateException {
        ThreeByThreeBoardCoordinate invalid = new ThreeByThreeBoardCoordinate("Please kindly place a piece on the center square");
    }

    @Test
    public void coordinateCanBeConstructedFromOrderedPair() throws InvalidCoordinateException {
        ThreeByThreeBoardCoordinate fromPair = new ThreeByThreeBoardCoordinate("(0, 3)");
        assertEquals((Integer) 0, fromPair.getRow());
        assertEquals((Integer) 3, fromPair.getColumn());
    }
}
