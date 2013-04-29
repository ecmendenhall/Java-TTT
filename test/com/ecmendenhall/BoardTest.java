package com.ecmendenhall;

import com.ecmendenhall.Board;
import com.ecmendenhall.Row;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;
import org.junit.Test;

@RunWith(JUnit4.class)
public class BoardTest {

    private Board emptyboard;
    private Board nowins;

    @Before
    public void setUp() {
        emptyboard = new Board();
        int x = 1;
        int o = 2;
        //nowins = new Board(int[] { new Row(o, o, x),
        //                           new Row(x, x, o),
        //                           new Row(o, x, x) });
    }

    @Test
    public void boardExists() {
        Board newboard = new Board();
    }

    @Test
    public void newBoardIsEmpty() {
        int[] nineEmptySquares = new int[9];
        Assert.assertArrayEquals("Board is not empty.", emptyboard.squares, nineEmptySquares);
    }

    @Test
    public void noWinsOnNewBoard() {
        Assert.assertFalse(emptyboard.hasWin());
    }

    @Test
    public void boardHasRows() {
        Assert.assertNotNull(emptyboard.getRows());
    }

    @Test
    public void boardHasThreeRows() {
        int[][] rows = emptyboard.getRows();
        Assert.assertEquals(rows.length, 3);
    }

    @Test public void rowsHaveThreeSquares() {
        int[][] rows = emptyboard.getRows();
        for (int[] row : rows) {
            Assert.assertEquals(row.length, 3);
        }
    }

    @Test
    public void boardHasColumns() {
        Assert.assertNotNull(emptyboard.getColumns());
    }

    @Test
    public void boardHasThreeColumns() {
        int[][] columns = emptyboard.getColumns();
        Assert.assertEquals(columns.length, 3);
    }

    @Test public void columnsHaveThreeSquares() {
        int[][] columns = emptyboard.getColumns();
        for (int[] column : columns) {
            Assert.assertEquals(column.length, 3);
        }
    }

    @Test public void boardReturnsCorrectRows() {
        //Assert.assertArrayEquals(nowins.squares, nowins.getRows());
    }

    @Test public void boardReturnsCorrectColumns() {
        int x = 1;
        int o = 2;
        int _ = 0;
        //int[][] cols = [[o, x, o],
        //                [o, x, x],
        //                [x, o, x]];
    }
}
