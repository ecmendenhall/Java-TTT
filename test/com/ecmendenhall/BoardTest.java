package com.ecmendenhall;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

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
    private Board playeronext;


    private Player playerx;
    private BoardCoordinate upperright;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final ByteArrayOutputStream error = new ByteArrayOutputStream();

    public boolean sameBoards(Board expected, Board actual) {

        for (int i=0; i< actual.top.squares.length; i++) {
            if (expected.top.squares[i] != actual.top.squares[i]) return false;
        }

        for (int i=0; i< actual.middle.squares.length; i++) {
            if (expected.middle.squares[i] != actual.middle.squares[i]) return false;
        }

        for (int i=0; i< actual.bottom.squares.length; i++) {
            if (expected.bottom.squares[i] != actual.bottom.squares[i]) return false;
        }

        return true;
    }

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

        playeronext = new Board( new Row(O, _, _),
                                 new Row(_, X, _),
                                 new Row(X, _, _) );

        playerx = new Player(X);
        upperright = new BoardCoordinate(0, 2);

        System.setOut(new PrintStream(output));
        System.setErr(new PrintStream(error));
    }

    @Test
    public void boardExists() {
        Board newboard = new Board();
    }

    @Test
    public void newBoardIsEmpty() {
        Row emptyrow = new Row(_, _, _);
        assertArrayEquals(emptyrow.squares, emptyboard.top.squares);
        assertArrayEquals(emptyrow.squares, emptyboard.middle.squares);
        assertArrayEquals(emptyrow.squares, emptyboard.bottom.squares);
    }

    @Test
    public void noWinsOnNewBoard() {
        assertNull(emptyboard.hasWin());
    }

    @Test
    public void boardHasRows() {
        assertNotNull(emptyboard.getRows());
    }

    @Test
    public void boardHasThreeRows() {
        Row[] rows = emptyboard.getRows();
        assertEquals(3, rows.length);
    }

    @Test public void rowsHaveThreeSquares() {
        Row[] rows = emptyboard.getRows();
        for (Row row : rows) {
            assertEquals(3, row.squares.length);
        }
    }

    @Test
    public void boardHasColumns() {
        assertNotNull(emptyboard.getColumns());
    }

    @Test
    public void boardHasThreeColumns() {
        Column[] columns = emptyboard.getColumns();
        assertEquals(3, columns.length);
    }

    @Test
    public void columnsHaveThreeSquares() {
        Column[] columns = emptyboard.getColumns();
        for (Column column : columns) {
            assertEquals(3, column.squares.length);
        }
    }

    @Test
    public void boardReturnsCorrectRows() {
        Row toprow    = new Row(O, O, X);
        Row middlerow = new Row(X, X, O);
        Row bottomrow = new Row(O, X, X);

        Row[] rows = nowins.getRows();
        assertArrayEquals(toprow.squares, rows[0].squares);
        assertArrayEquals(middlerow.squares, rows[1].squares);
        assertArrayEquals(bottomrow.squares, rows[2].squares);
    }

    @Test
    public void boardReturnsCorrectColumns() {
        Column firstcolumn  = new Column(O, X, O);
        Column middlecolumn = new Column(O, X, X);
        Column lastcolumn   = new Column(X, O, X);

        Column[] columns = nowins.getColumns();
        assertArrayEquals(firstcolumn.squares, columns[0].squares);
        assertArrayEquals(middlecolumn.squares, columns[1].squares);
        assertArrayEquals(lastcolumn.squares, columns[2].squares);
    }

    @Test
    public void boardReturnsCorrectDiags() {
        Diagonal leftright = new Diagonal(O, X, X);
        Diagonal rightleft = new Diagonal(X, X, O);

        Diagonal[] diagonals = nowins.getDiagonals();
        assertArrayEquals(leftright.squares, diagonals[0].squares);
        assertArrayEquals(rightleft.squares, diagonals[1].squares);
    }


    @Test
    public void boardHasHorizontalWin() {
        assertNotNull(playerxwins.hasWin());
    }

    @Test
    public void boardHasVerticalWin() {
        assertNotNull(playerowins.hasWin());
    }

    @Test
    public void drawBoardDoesNotHaveWin() {
        assertNull(nowins.hasWin());
    }

    @Test
    public void boardHasDiagonalWin() {
        assertNotNull(diagonal.hasWin());
    }

    @Test
    public void getTopLeftByCoordinate() {
        assertEquals(X, nowins.getSquareByCoordinate(new BoardCoordinate(1, 1)));
    }

    @Test
    public void getTopLeft() {
        assertEquals(O, nowins.getSquare("Top Left"));
    }

    @Test
    public void getBottomRight() {
        assertEquals(X, nowins.getSquare("bottom right"));
    }

    @Test
    public void getSquareWorksForAllSquares() {
        assertEquals(O, nowins.getSquare("Top center"));
        assertEquals(X, nowins.getSquare("top Right"));
        assertEquals(X, nowins.getSquare("MIDDLE LEFT"));
        assertEquals(X, nowins.getSquare("Middle Center"));
        assertEquals(O, nowins.getSquare("middle rIght"));
        assertEquals(O, nowins.getSquare("bottom left"));
        assertEquals(X, nowins.getSquare("BOTTOM CENTER"));
    }

    @Test
    public void addXToTopLeft() {
        board = board.fillSquare(new BoardCoordinate(0, 0), X);
        assertEquals(X, board.getSquare("top left"));
    }

    @Test
    public void emptyBoardIsNotFull() {
        assertFalse(emptyboard.isFull());
    }

    @Test
    public void noWinsIsFull() {
        assertTrue(nowins.isFull());
    }

    @Test
    public void noWinsHasNoWinner() {
        assertEquals(_, nowins.winnerIs());
    }

    @Test
    public void playerXWinsWinnerIsX() {
        assertEquals(X, playerxwins.winnerIs());
    }

    @Test
    public void upperRightMoveIsInvalid() {
        board = playerx.move(upperright, board);
        assertFalse(board.moveIsValid(upperright));
    }

    @Test
    public void lowerLeftMoveIsValid() {
        BoardCoordinate lowerleft = new BoardCoordinate("bottom left");
        assertTrue(board.moveIsValid(lowerleft));
    }

    @Test
    public void playerXMovesFirst() {
        assertEquals(X, emptyboard.nextTurn());
    }

    @Test
    public void playerOMovesNext() {
        assertEquals(O, playeronext.nextTurn());
    }

    @Test
    public void fullBoardHasNoNextTurn() {
        assertEquals(_, nowins.nextTurn());
    }

    @Test
    public void emptyBoardSumIsZero() {
        assertEquals(0, emptyboard.sum());
    }

    @Test
    public void noWinsBoardSumIsThirteen() {
        assertEquals(13, nowins.sum());
    }

    @Test
    public void playerXWinsBoardSumIsSeven() {
        assertEquals(7, playerxwins.sum());
    }

    @Test
    public void emptyBoardHasNineEmptySquares() {
        assertEquals(9, emptyboard.countEmptySquares());
    }

    @Test
    public void boardReturnsCorrectString() {
        assertEquals("OOX\nXXO\nOXX\n", nowins.toString());
    }

    @Test
    public void boardPrintsCorrectly() {
        nowins.print();
        assertEquals(nowins.toString(), output.toString());
    }

    @Test
    public void fillSquareHasNoSideEffects() {

        Board newboard = emptyboard.fillSquare(new BoardCoordinate("top left"), X);
        Board expected = new Board( new Row(X, _, _),
                                    new Row(_, _, _),
                                    new Row(_, _, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = emptyboard.fillSquare(new BoardCoordinate("bottom middle"), O);
        expected = new Board ( new Row(_, _, _),
                               new Row(_, _, _),
                               new Row(_, O, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = emptyboard.fillSquare(new BoardCoordinate(2, 0), O);
        expected = new Board ( new Row(_, _, _),
                               new Row(_, _, _),
                               new Row(O, _, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = playeronext.fillSquare(new BoardCoordinate("top right"), O);
        expected = new Board ( new Row(O, _, O),
                               new Row(_, X, _),
                               new Row(X, _, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = playeronext.fillSquare(new BoardCoordinate("middle right"), O);
        expected = new Board ( new Row(O, _, _),
                               new Row(_, X, O),
                               new Row(X, _, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = playeronext.fillSquare(new BoardCoordinate("bottom right"), O);
        expected = new Board ( new Row(O, _, _),
                               new Row(_, X, _),
                               new Row(X, _, O) );

        assertTrue(sameBoards(expected, newboard));

        newboard = playeronext.fillSquare(new BoardCoordinate("top middle"), O);
        expected = new Board ( new Row(O, O, _),
                               new Row(_, X, _),
                               new Row(X, _, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = playeronext.fillSquare(new BoardCoordinate("middle left"), O);
        expected = new Board ( new Row(O, _, _),
                               new Row(O, X, _),
                               new Row(X, _, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = playeronext.fillSquare(new BoardCoordinate("middle right"), O);
        expected = new Board ( new Row(O, _, _),
                               new Row(_, X, O),
                               new Row(X, _, _) );

        assertTrue(sameBoards(expected, newboard));

        newboard = playeronext.fillSquare(new BoardCoordinate("bottom center"), O);
        expected = new Board ( new Row(O, _, _),
                               new Row(_, X, _),
                               new Row(X, O, _) );

        assertTrue(sameBoards(expected, newboard));

    }

    @After
    public void cleanUp() {
        System.setOut(null);
        System.setErr(null);
    }

}
