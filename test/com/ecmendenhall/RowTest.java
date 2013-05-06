package com.ecmendenhall;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class RowTest extends TicTacToeTest {

    public Row empty;
    public Row xxo;
    public Row ooo;

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private ByteArrayOutputStream error = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        empty = new Row();
        xxo = new Row(X, X, O);
        ooo = new Row(O, O, O);

        System.setOut(new PrintStream(output));
        System.setErr(new PrintStream(error));
    }

    @Test
    public void rowExists() {
        Row newrow = new Row();
    }

    @Test
    public void emptyRowContainsAllZeroes() {
        for (int square : empty.getSquares()) assertEquals(0, square);
    }

    @Test
    public void rowXXOContainsCorrectSquares() {
        int[] expected = {X, X, O};
        assertArrayEquals(expected, xxo.getSquares());
    }

    @Test
    public void emptyRowDoesNotHaveWin() {
        assertFalse(empty.hasWin());
    }

    @Test
    public void rowXXODoesNotHaveWin() {
        assertFalse(xxo.hasWin());
    }

    @Test
    public void rowOOOHasWin() {
        assertTrue(ooo.hasWin());
    }

    @Test
    public void rowOOOWinnerIsO() {
        assertEquals(2, ooo.winner());
    }

    @Test
    public void rowOOOWinnerIsNotX() {
        assertFalse(ooo.winner() == 1);
    }

    @Test
    public void rowXXOHasNoWinner() {
        assertEquals(0, xxo.winner());
    }

    @Test
    public void emptyRowSquaresAreFillable() {
        empty = empty.fillSquare(0, X);
        assertEquals(X, empty.getSquare(0));
    }

    @Test
    public void emptyRowIsNotFull() {
        assertFalse(empty.isFull());
    }

    @Test
    public void rowXXOIsFull() {
        assertTrue(xxo.isFull());
    }

    @Test
    public void emptyRowSumIsZero() {
        assertEquals(0, empty.sum());
    }

    @Test
    public void rowXXOSumIsFour() {
        assertEquals(4, xxo.sum());
    }

    @Test
    public void rowOOOSumIsSix() {
        assertEquals(6, ooo.sum());
    }

    @Test
    public void rowsReturnCorrectStrings() {
        assertEquals("XXO\n", xxo.toString());
        assertEquals("OOO\n", ooo.toString());
        assertEquals("O__\n", new Row(2, 0, 0).toString());
    }

    @Test
    public void squaresReturnCorrectSymbols() {
        assertEquals(xxo.intToSymbol(1), "X");
        assertEquals(xxo.intToSymbol(2), "O");
        assertEquals(xxo.intToSymbol(0), "_");
    }
}