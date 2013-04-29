package com.ecmendenhall;

import com.ecmendenhall.Board;

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

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void boardExists() {
        Board newboard = new Board();
    }

    @Test
    public void newBoardIsEmpty() {
        int[] nineEmptySquares = new int[9];
        Assert.assertArrayEquals("Board is not empty.", board.squares, nineEmptySquares);
    }

    @Test
    public void noWinsOnNewBoard() {
        Assert.assertFalse(board.hasWin());
    }

    @Test
    public void boardHasRows() {
        Assert.assertNotNull(board.getRows());
    }

    @Test
    public void boardHasThreeRows() {
        int[][] rows = board.getRows();
        Assert.assertEquals(rows.length, 3);
    }

    @Test public void rowsHaveThreeSquares() {
        int[][] rows = board.getRows();
        for (int[] row : rows) {
            Assert.assertEquals(row.length, 3);
        }
    }
}
