package com.ecmendenhall;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class BoardCoordinateTest {

    private BoardCoordinate upperright;

    @Before
    public void setUp() {
        upperright = new BoardCoordinate(0, 2);
    }

    @Test
    public void rowIsZero() {
        Assert.assertEquals(0, upperright.row);
    }

    @Test
    public void columnIsTwo() {
        Assert.assertEquals(2, upperright.column);
    }
}