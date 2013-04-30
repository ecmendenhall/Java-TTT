package com.ecmendenhall;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;
import org.junit.Test;

@RunWith(JUnit4.class)
public class RowTest {

    private Row emptyrow;
    private Row xxo;
    private Row ooo;

    @Before
    public void setUp() {
        int x = 1;
        int o = 2;
        int _ = 0;
        emptyrow = new Row();
        xxo = new Row(x, x, o);
        ooo = new Row(o, o, o);
    }

    @Test
    public void boardExists() {
        Row newrow = new Row();
    }

    @Test
    public void emptyRowContainsAllZeroes() {
        for (int square : emptyrow.squares) Assert.assertEquals(0, square);
    }

    @Test
    public void rowXXOContainsCorrectSquares() {
        int[] expected = {1, 1, 2};
        Assert.assertArrayEquals(expected, xxo.squares);
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