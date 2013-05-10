package com.cmendenhall;

import org.junit.*;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;


@RunWith(JUnit4.class)
public class BoardTest extends TicTacToeTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void boardExists() {
        Board newBoard = new Board();
    }

    @Test
    public void newBoardIsEmpty() {
        Row emptyRow = new Row(_, _, _);
        assertArrayEquals(emptyRow.getSquares(), emptyBoard.getTop().getSquares());
        assertArrayEquals(emptyRow.getSquares(), emptyBoard.getMiddle().getSquares());
        assertArrayEquals(emptyRow.getSquares(), emptyBoard.getBottom().getSquares());
    }

    @Test
    public void noWinsOnNewBoard() {
        assertFalse(emptyBoard.hasWin());
    }

    @Test
    public void boardHasRows() {
        assertNotNull(emptyBoard.getRows());
    }

    @Test
    public void boardHasThreeRows() {
        Row[] rows = emptyBoard.getRows();
        assertEquals(3, rows.length);
    }

    @Test public void rowsHaveThreeSquares() {
        Row[] rows = emptyBoard.getRows();
        for (Row row : rows) {
            assertEquals(3, row.getSquares().length);
        }
    }

    @Test
    public void boardHasColumns() {
        assertNotNull(emptyBoard.getColumns());
    }

    @Test
    public void boardHasThreeColumns() {
        Row[] columns = emptyBoard.getColumns();
        assertEquals(3, columns.length);
    }

    @Test
    public void columnsHaveThreeSquares() {
        Row[] columns = emptyBoard.getColumns();
        for (Row column : columns) {
            assertEquals(3, column.getSquares().length);
        }
    }

    @Test
    public void boardReturnsCorrectRows() {
        Row topRow    = new Row(O, O, X);
        Row middleRow = new Row(X, X, O);
        Row bottomRow = new Row(O, X, X);

        Row[] rows = noWins.getRows();
        assertArrayEquals(topRow.getSquares(), rows[0].getSquares());
        assertArrayEquals(middleRow.getSquares(), rows[1].getSquares());
        assertArrayEquals(bottomRow.getSquares(), rows[2].getSquares());
    }

    @Test
    public void boardReturnsCorrectColumns() {
        Row firstColumn  = new Row(O, X, O);
        Row middleColumn = new Row(O, X, X);
        Row lastColumn   = new Row(X, O, X);

        Row[] columns = noWins.getColumns();
        assertArrayEquals(firstColumn.getSquares(), columns[0].getSquares());
        assertArrayEquals(middleColumn.getSquares(), columns[1].getSquares());
        assertArrayEquals(lastColumn.getSquares(), columns[2].getSquares());
    }

    @Test
    public void boardReturnsCorrectDiags() {
        Row leftRight = new Row(O, X, X);
        Row rightLeft = new Row(X, X, O);

        Row[] diagonals = noWins.getDiagonals();
        assertArrayEquals(leftRight.getSquares(), diagonals[0].getSquares());
        assertArrayEquals(rightLeft.getSquares(), diagonals[1].getSquares());
    }


    @Test
    public void boardHasHorizontalWin() {
        assertTrue(playerOWins.hasWin());
    }

    @Test
    public void boardHasVerticalWin() {
        assertTrue(playerXWins.hasWin());
    }

    @Test
    public void drawBoardDoesNotHaveWin() {
        assertFalse(noWins.hasWin());
    }

    @Test
    public void boardHasDiagonalWin() {
        assertTrue(diagonal.hasWin());
    }

    @Test
    public void getTopLeftByCoordinate() {
        assertEquals(X, noWins.getSquareByCoordinate(new BoardCoordinate(1, 1)));
    }

    @Test
    public void getTopLeft() throws InvalidCoordinateException {
        assertEquals(O, noWins.getSquare("Top Left"));
    }

    @Test
    public void getBottomRight() throws InvalidCoordinateException {
        assertEquals(X, noWins.getSquare("bottom right"));
    }

    @Test
    public void getSquareWorksForAllSquares() throws InvalidCoordinateException {
        assertEquals(O, noWins.getSquare("Top center"));
        assertEquals(X, noWins.getSquare("top Right"));
        assertEquals(X, noWins.getSquare("MIDDLE LEFT"));
        assertEquals(X, noWins.getSquare("Middle Center"));
        assertEquals(O, noWins.getSquare("middle rIght"));
        assertEquals(O, noWins.getSquare("bottom left"));
        assertEquals(X, noWins.getSquare("BOTTOM CENTER"));
    }

    @Test
    public void addXToTopLeft() throws InvalidMoveException, InvalidCoordinateException {
        board = board.fillSquare(new BoardCoordinate(0, 0), X);
        assertEquals(X, board.getSquare("top left"));
    }

    @Test
    public void emptyBoardIsNotFull() {
        assertFalse(emptyBoard.isFull());
    }

    @Test
    public void partiallyFullBoardIsNotFull() {
        assertFalse(playerONext.isFull());
        assertFalse(playerXShouldBlock.isFull());
    }

    @Test
    public void noWinsIsFull() {
        assertTrue(noWins.isFull());
    }

    @Test
    public void noWinsHasNoWinner() {
        assertEquals(_, noWins.winnerIs());
    }

    @Test
    public void playerXWinsWinnerIsX() {
        assertEquals(X, playerXWins.winnerIs());
    }

    @Test
    public void playerOWinsWinnerIsO() {
        assertEquals(O, playerOWins.winnerIs());
    }

    @Test
    public void upperRightMoveIsInvalid() throws InvalidMoveException {
        board = playerX.move(upperRight, board);
        assertFalse(board.moveIsValid(upperRight));
    }

    @Test
    public void lowerLeftMoveIsValid() throws Exception {
        BoardCoordinate lowerLeft = new BoardCoordinate("bottom left");
        assertTrue(board.moveIsValid(lowerLeft));
    }

    @Test
    public void playerXMovesFirst() {
        assertEquals(X, emptyBoard.nextTurn());
    }

    @Test
    public void playerOMovesNext() {
        assertEquals(O, playerONext.nextTurn());
    }

    @Test
    public void fullBoardHasNoNextTurn() {
        assertEquals(_, noWins.nextTurn());
    }

    @Test
    public void emptyBoardSumIsZero() {
        assertEquals(0, emptyBoard.sum());
    }

    @Test
    public void noWinsBoardSumIsThirteen() {
        assertEquals(13, noWins.sum());
    }

    @Test
    public void playerXWinsBoardSumIsSeven() {
        assertEquals(7, playerXWins.sum());
    }

    @Test
    public void emptyBoardHasNineEmptySquares() {
        assertEquals(9, emptyBoard.countEmptySquares());
    }

    @Test
    public void boardReturnsCorrectString() {
        assertEquals(" O \u2502 O \u2502 X\n" + HORIZONTAL_LINE +
                     " X \u2502 X \u2502 O\n" + HORIZONTAL_LINE +
                     " O \u2502 X \u2502 X\n\n",
                     noWins.toString());
    }

    @Test
    public void fillSquareHasNoSideEffects() throws InvalidMoveException, InvalidCoordinateException {

        Board newBoard = emptyBoard.fillSquare(new BoardCoordinate("top left"), X);
        Board expected = new Board( new Row(X, _, _),
                                    new Row(_, _, _),
                                    new Row(_, _, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = emptyBoard.fillSquare(new BoardCoordinate("bottom middle"), O);
        expected = new Board( new Row(_, _, _),
                               new Row(_, _, _),
                               new Row(_, O, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = emptyBoard.fillSquare(new BoardCoordinate(2, 0), O);
        expected = new Board( new Row(_, _, _),
                               new Row(_, _, _),
                               new Row(O, _, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = playerONext.fillSquare(new BoardCoordinate("top right"), O);
        expected = new Board( new Row(O, _, O),
                               new Row(_, X, _),
                               new Row(X, _, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = playerONext.fillSquare(new BoardCoordinate("middle right"), O);
        expected = new Board( new Row(O, _, _),
                               new Row(_, X, O),
                               new Row(X, _, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = playerONext.fillSquare(new BoardCoordinate("bottom right"), O);
        expected = new Board( new Row(O, _, _),
                               new Row(_, X, _),
                               new Row(X, _, O) );

        assertTrue(expected.equals(newBoard));

        newBoard = playerONext.fillSquare(new BoardCoordinate("top middle"), O);
        expected = new Board( new Row(O, O, _),
                               new Row(_, X, _),
                               new Row(X, _, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = playerONext.fillSquare(new BoardCoordinate("middle left"), O);
        expected = new Board( new Row(O, _, _),
                               new Row(O, X, _),
                               new Row(X, _, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = playerONext.fillSquare(new BoardCoordinate("middle right"), O);
        expected = new Board( new Row(O, _, _),
                               new Row(_, X, O),
                               new Row(X, _, _) );

        assertTrue(expected.equals(newBoard));

        newBoard = playerONext.fillSquare(new BoardCoordinate("bottom center"), O);
        expected = new Board( new Row(O, _, _),
                               new Row(_, X, _),
                               new Row(X, O, _) );

        assertTrue(expected.equals(newBoard));

    }

    @Test
    public void boardReturnsNextStates () throws InvalidMoveException {

        List<Board> nextBoards = new ArrayList<Board>();

        nextBoards.add(new Board(new Row(O, _, _),
                                 new Row(_, X, _),
                                 new Row(_, _, _)));

        nextBoards.add(new Board(new Row(_, _, _),
                                 new Row(O, X, _),
                                 new Row(_, _, _)));

        nextBoards.add(new Board(new Row(_, _, _),
                                 new Row(_, X, _),
                                 new Row(O, _, _)));

        nextBoards.add(new Board(new Row(_, O, _),
                                 new Row(_, X, _),
                                 new Row(_, _, _)));

        nextBoards.add(new Board(new Row(_, _, _),
                                 new Row(_, X, _),
                                 new Row(_, O, _)));

        nextBoards.add(new Board(new Row(_, _, O),
                                 new Row(_, X, _),
                                 new Row(_, _, _)));

        nextBoards.add(new Board(new Row(_, _, _),
                                 new Row(_, X, O),
                                 new Row(_, _, _)));

        nextBoards.add(new Board(new Row(_, _, _),
                                 new Row(_, X, _),
                                 new Row(_, _, O)));

        List<Board> nextStates = xInCenter.getNextStates();

        for (int i=0; i < nextStates.size(); i++) {
            Board expected = nextBoards.get(i);
            Board actual = nextStates.get(i);
            assertTrue(expected.equals(actual));
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void occupiedSquareMoveThrowsError() throws InvalidMoveException, InvalidCoordinateException {
        thrown.expect(InvalidMoveException.class);
        thrown.expectMessage("Square is already full.");
        Board invalid = playerONext.fillSquare(new BoardCoordinate("middle center"), O);
    }

    @Test
    public void invalidMoveThrowsError() throws InvalidMoveException {
        thrown.expect(InvalidMoveException.class);
        thrown.expectMessage("Invalid move coordinate.");
        Board invalid = playerONext.fillSquare(new BoardCoordinate(5, 7), O);
    }

    @After
    public void cleanUp() {
        System.setOut(stdout);
    }

}
