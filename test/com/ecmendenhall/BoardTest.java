package com.ecmendenhall;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class BoardTest {

    private int X = 1;
    private int O = 2;
    private int _ = 0;

    private Board emptyboard;
    private Board board;
    private Board nowins;
    private Board playerxwins;
    private Board playerowins;
    private Board diagonal;

    private Player playerx;
    private BoardCoordinate upperright;

    @Before
    public void setUp() {
        emptyboard = new Board();
        board = new Board();
        nowins = new Board( new Row(O, O, X),
                            new Row(X, X, O),
                            new Row(O, X, X) );

        playerxwins =  new Board( new Row(X, _, _),
                            new Row(X, O, _),
                            new Row(X, _, O) );

        playerowins =  new Board( new Row(O, O, O),
                            new Row(X, X, _),
                            new Row(_, X, _) );

        diagonal = new Board( new Row(X, _, _),
                              new Row(O, X, _),
                              new Row(_, O, X) );

        playerx = new Player(X);
        upperright = new BoardCoordinate(0, 2);
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
        Assert.assertNull(emptyboard.hasWin());
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
        Row toprow    = new Row(O, O, X);
        Row middlerow = new Row(X, X, O);
        Row bottomrow = new Row(O, X, X);

        Row[] rows = nowins.getRows();
        Assert.assertArrayEquals(toprow.squares, rows[0].squares);
        Assert.assertArrayEquals(middlerow.squares, rows[1].squares);
        Assert.assertArrayEquals(bottomrow.squares, rows[2].squares);
    }

    @Test
    public void boardReturnsCorrectColumns() {
        Column firstcolumn  = new Column(O, X, O);
        Column middlecolumn = new Column(O, X, X);
        Column lastcolumn   = new Column(X, O, X);

        Column[] columns = nowins.getColumns();
        Assert.assertArrayEquals(firstcolumn.squares, columns[0].squares);
        Assert.assertArrayEquals(middlecolumn.squares, columns[1].squares);
        Assert.assertArrayEquals(lastcolumn.squares, columns[2].squares);
    }

    @Test
    public void boardReturnsCorrectDiags() {
        Diagonal leftright = new Diagonal(O, X, X);
        Diagonal rightleft = new Diagonal(X, X, O);

        Diagonal[] diagonals = nowins.getDiagonals();
        Assert.assertArrayEquals(leftright.squares, diagonals[0].squares);
        Assert.assertArrayEquals(rightleft.squares, diagonals[1].squares);
    }


    @Test
    public void boardHasHorizontalWin() {
        Assert.assertNotNull(playerxwins.hasWin());
    }

    @Test
    public void boardHasVerticalWin() {
        Assert.assertNotNull(playerowins.hasWin());
    }

    @Test
    public void drawBoardDoesNotHaveWin() {
        Assert.assertNull(nowins.hasWin());
    }

    @Test
    public void boardHasDiagonalWin() {
        Assert.assertNotNull(diagonal.hasWin());
    }

    @Test
    public void getTopLeftByCoordinate() {
        Assert.assertEquals(X, nowins.getSquareByCoordinate(new BoardCoordinate(1, 1)));
    }

    @Test
    public void getTopLeft() {
        Assert.assertEquals(O, nowins.getSquare("Top Left"));
    }

    @Test
    public void getBottomRight() {
        Assert.assertEquals(X, nowins.getSquare("bottom right"));
    }

    @Test
    public void getSquareWorksForAllSquares() {
        Assert.assertEquals(O, nowins.getSquare("Top center"));
        Assert.assertEquals(X, nowins.getSquare("top Right"));
        Assert.assertEquals(X, nowins.getSquare("MIDDLE LEFT"));
        Assert.assertEquals(X, nowins.getSquare("Middle Center"));
        Assert.assertEquals(O, nowins.getSquare("middle rIght"));
        Assert.assertEquals(O, nowins.getSquare("bottom left"));
        Assert.assertEquals(X, nowins.getSquare("BOTTOM CENTER"));
    }

    @Test
    public void addXToTopLeft() {
        board.fillSquare(new BoardCoordinate(0, 0), X);
        Assert.assertEquals(X, board.getSquare("top left"));
    }

    @Test
    public void emptyBoardIsNotFull() {
        Assert.assertFalse(emptyboard.isFull());
    }

    @Test
    public void noWinsIsFull() {
        Assert.assertTrue(nowins.isFull());
    }

    @Test
    public void noWinsHasNoWinner() {
        Assert.assertEquals(_, nowins.winnerIs());
    }

    @Test
    public void playerXWinsWinnerIsX() {
        Assert.assertEquals(X, playerxwins.winnerIs());
    }

    @Test
    public void upperRightMoveIsInvalid() {
        playerx.move(upperright, board);
        Assert.assertFalse(board.moveIsValid(upperright));
    }

    @Test
    public void lowerLeftMoveIsValid() {
        BoardCoordinate lowerleft = new BoardCoordinate("bottom left");
        Assert.assertTrue(board.moveIsValid(lowerleft));
    }
}
