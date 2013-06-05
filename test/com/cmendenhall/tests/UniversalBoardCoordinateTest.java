package com.cmendenhall.tests;

import com.cmendenhall.board.BoardCoordinate;
import com.cmendenhall.board.UniversalBoardCoordinate;
import com.cmendenhall.exceptions.InvalidCoordinateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UniversalBoardCoordinateTest {
    private BoardCoordinate lowerLeft;
    private BoardCoordinate alsoLowerLeft;

    @Before
    public void setUp() throws InvalidCoordinateException {
        lowerLeft = new UniversalBoardCoordinate("2, 0");
        alsoLowerLeft = new UniversalBoardCoordinate("(2, 0)");
    }

    @Test
    public void coordinateCanBeConstructedFromString() {
        assertEquals((Integer) 2, lowerLeft.getRow());
        assertEquals((Integer) 0, lowerLeft.getColumn());
    }


    @Test(expected = InvalidCoordinateException.class)
    public void phraseWithMoreThanTwoCoordinatesThrowsException() throws InvalidCoordinateException {
        BoardCoordinate invalid = new UniversalBoardCoordinate("(0, 1, 2)");
    }
}