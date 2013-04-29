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

    @Before
    public void setUp() {
        int x = 1;
        int o = 2;
        int _ = 0;
        emptyrow = new Row();
        xxo = new Row(x, x, o);
    }

    @Test
    public void boardExists() {
        Row newrow = new Row();
    }

    @Test
    public void emptyRowContainsAllZeroes() {
        for (int square : emptyrow.squares) Assert.assertEquals(square, 0);
    }
}