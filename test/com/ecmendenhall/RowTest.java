package com.ecmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class RowTest {
    final int X = 1;
    final int O = 2;
    final int _ = 0;

    public Row empty;
    public Row xxo;
    public Row ooo;

    @Before
    public void setUp() {
        empty = new Row();
        xxo = new Row(X, X, O);
        ooo = new Row(O, O, O);
    }

    @Test
    public void rowExists() {
        Row newrow = new Row();
    }

    @Test
    public void emptyRowContainsAllZeroes() {
        for (int square : empty.squares) Assert.assertEquals(0, square);
    }

    @Test
    public void rowXXOContainsCorrectSquares() {
        int[] expected = {X, X, O};
        Assert.assertArrayEquals(expected, xxo.squares);
    }

    @Test
    public void emptyRowDoesNotHaveWin() {
        Assert.assertFalse(empty.hasWin());
    }

    @Test
    public void rowXXODoesNotHaveWin() {
        Assert.assertFalse(xxo.hasWin());
    }

    @Test
    public void rowOOOHasWin() {
        Assert.assertTrue(ooo.hasWin());
    }

    @Test
    public void rowOOOWinnerIsO() {
        Assert.assertEquals(2, ooo.winner());
    }

    @Test
    public void rowOOOWinnerIsNotX() {
        Assert.assertFalse(ooo.winner() == 1);
    }

    @Test
    public void rowXXOHasNoWinner() {
        Assert.assertEquals(0, xxo.winner());
    }

}