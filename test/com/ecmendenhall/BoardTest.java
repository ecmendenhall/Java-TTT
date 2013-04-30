package com.ecmendenhall;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class BoardTest {

    private int x = 1;
    private int o = 2;
    private int _ = 0;
    private Board emptyboard;
    private Board nowins;

    @Before
    public void setUp() {
        emptyboard = new Board();
        nowins = new Board( new Row(o, o, x),
                            new Row(x, x, o),
                            new Row(o, x, x) );
    }

    @Test
    public void boardExists() {
        Board newboard = new Board();
    }

    @Test
    public void newBoardIsEmpty() {
        Row emptyrow = new Row(_, _, _);
        Assert.assertArrayEquals(emptyrow.squares, emptyboard.top.squares);
        Assert.assertArrayEquals(emptyrow.squares, emptyboard.middle.squares);
        Assert.assertArrayEquals(emptyrow.squares, emptyboard.bottom.squares);
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
        Row[] rows = emptyboard.getRows();
        Assert.assertEquals(3, rows.length);
    }

    @Test public void rowsHaveThreeSquares() {
        Row[] rows = emptyboard.getRows();
        for (Row row : rows) {
            Assert.assertEquals(3, row.squares.length);
        }
    }

    @Test
    public void boardHasColumns() {
        Assert.assertNotNull(emptyboard.getColumns());
    }

    @Test
    public void boardHasThreeColumns() {
        Column[] columns = emptyboard.getColumns();
        Assert.assertEquals(3, columns.length);
    }

    @Test
    public void columnsHaveThreeSquares() {
        Column[] columns = emptyboard.getColumns();
        for (Column column : columns) {
            Assert.assertEquals(3, column.squares.length);
        }
    }

    @Test
    public void boardReturnsCorrectRows() {
        Row toprow    = new Row(o, o, x);
        Row middlerow = new Row(x, x, o);
        Row bottomrow = new Row(o, x, x);

        Row[] rows = nowins.getRows();
        Assert.assertArrayEquals(toprow.squares, rows[0].squares);
        Assert.assertArrayEquals(middlerow.squares, rows[1].squares);
        Assert.assertArrayEquals(bottomrow.squares, rows[2].squares);
    }

    @Test
    public void boardReturnsCorrectColumns() {
        Column firstcolumn  = new Column(o, x, o);
        Column middlecolumn = new Column(o, x, x);
        Column lastcolumn   = new Column(x, o, x);

        Column[] columns = nowins.getColumns();
        Assert.assertArrayEquals(firstcolumn.squares, columns[0].squares);
        Assert.assertArrayEquals(middlecolumn.squares, columns[1].squares);
        Assert.assertArrayEquals(lastcolumn.squares, columns[2].squares);
    }

    @Test
    public void boardReturnsCorrectDiags() {
        Diagonal leftright = new Diagonal(o, x, x);
        Diagonal rightleft = new Diagonal(x, x, o);

        Diagonal[] diagonals = nowins.getDiagonals();
        Assert.assertArrayEquals(leftright.squares, diagonals[0].squares);
        Assert.assertArrayEquals(rightleft.squares, diagonals[1].squares);
    }

}
