package com.ecmendenhall;

import com.ecmendenhall.Board;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;
import org.junit.Test;

@RunWith(JUnit4.class)
public class BoardTest {

    @Test
    public void boardExists() {
        Board board = new Board();
    }

    @Test
    public void newBoardIsEmpty() {
        Board newboard;
        newboard = new Board();
        int[] nineEmptySquares = new int[9];
        Assert.assertArrayEquals("Board is not empty.", newboard.squares, nineEmptySquares);
    }
}
