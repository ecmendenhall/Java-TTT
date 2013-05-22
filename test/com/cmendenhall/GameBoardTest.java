package com.cmendenhall;

import org.junit.*;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;


@RunWith(JUnit4.class)
public class GameBoardTest extends TicTacToeTest {

    private GameBoard noWins = new GameBoard( new Row(O, O, X),
                                              new Row(X, X, O),
                                              new Row(O, X, X) );

    private GameBoard emptyBoard = new GameBoard( new Row(_, _, _),
                                                  new Row(_, _, _),
                                                  new Row(_, _, _) );


    private GameBoard playerXWins =  new GameBoard( new Row(X, _, _),
                                                    new Row(X, O, _),
                                                    new Row(X, _, O) );


    @Test
    public void boardShouldExist() {
        new GameBoard();
    }

    @Test
    public void boardReturnsCorrectString() {
        String divider = makeDivider(noWins);

        assertEquals(" O \u2502 O \u2502 X\n" + divider +
                     " X \u2502 X \u2502 O\n" + divider +
                     " O \u2502 X \u2502 X\n\n",
                     noWins.toString());
    }
    @Test
    public void boardExists() {
        Board newBoard = new GameBoard();
    }

    @Test
    public void newBoardIsEmpty() {
        Row emptyRow = new Row(_, _, _);
        assertArrayEquals(emptyRow.getSquares(), emptyBoard.getRows().get(0).getSquares());
        assertArrayEquals(emptyRow.getSquares(), emptyBoard.getRows().get(1).getSquares());
        assertArrayEquals(emptyRow.getSquares(), emptyBoard.getRows().get(2).getSquares());
    }

    @Test
    public void boardHasRows() {
        assertNotNull(emptyBoard.getRows());
    }

    @Test
    public void boardHasThreeRows() {
        assertEquals(3, emptyBoard.getSize());
    }

    @Test public void rowsHaveThreeSquares() {
        List<Row> rows = emptyBoard.getRows();
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
        assertEquals(3, emptyBoard.getColumns().size());
    }

    @Test
    public void columnsHaveThreeSquares() {
        List<Row> columns = emptyBoard.getColumns();
        for (Row column : columns) {
            assertEquals(3, column.numberOfSquares());
        }
    }

    @Test
    public void boardReturnsCorrectRows() {
        Row topRow    = new Row(O, O, X);
        Row middleRow = new Row(X, X, O);
        Row bottomRow = new Row(O, X, X);

        List<Row> rows = noWins.getRows();
        assertArrayEquals(topRow.getSquares(), rows.get(0).getSquares());
        assertArrayEquals(middleRow.getSquares(), rows.get(1).getSquares());
        assertArrayEquals(bottomRow.getSquares(), rows.get(2).getSquares());
    }

    @Test
    public void boardReturnsCorrectColumns() {
        Row firstColumn  = new Row(O, X, O);
        Row middleColumn = new Row(O, X, X);
        Row lastColumn   = new Row(X, O, X);

        List<Row> columns = noWins.getColumns();
        assertArrayEquals(firstColumn.getSquares(), columns.get(0).getSquares());
        assertArrayEquals(middleColumn.getSquares(), columns.get(1).getSquares());
        assertArrayEquals(lastColumn.getSquares(), columns.get(2).getSquares());
    }

    @Test
    public void boardReturnsCorrectDiags() {
        Row leftRight = new Row(O, X, X);
        Row rightLeft = new Row(X, X, O);

        List<Row> diagonals = noWins.getDiagonals();
        assertArrayEquals(leftRight.getSquares(), diagonals.get(0).getSquares());
        assertArrayEquals(rightLeft.getSquares(), diagonals.get(1).getSquares());
    }



    @Test
    public void getTopLeftByCoordinate() {
        assertEquals(X, noWins.getSquareByCoordinate(new UniversalBoardCoordinate(1, 1)));
    }

    @Test
    public void addXToTopLeft() throws InvalidMoveException, InvalidCoordinateException {
        board = board.fillSquare(new UniversalBoardCoordinate(0, 0), X);
        assertEquals(X, board.getRows().get(0).getSquare(0));
    }


    @Test
    public void boardsAreCorrectlyCompared() {
        assertTrue(noWins.equals(noWins));
    }

    @Test
    public void fillSquareHasNoSideEffects() throws InvalidMoveException, InvalidCoordinateException {

        Board newBoard = emptyBoard.fillSquare(new ThreeByThreeBoardCoordinate("top left"), X);
        Board expected = new GameBoard( new Row(X, _, _),
                                        new Row(_, _, _),
                                        new Row(_, _, _) );

        assertSameBoard(expected, newBoard);

        newBoard = emptyBoard.fillSquare(new ThreeByThreeBoardCoordinate("bottom middle"), O);

        expected = new GameBoard( new Row(_, _, _),
                                  new Row(_, _, _),
                                  new Row(_, O, _) );

        assertSameBoard(expected, newBoard);

        newBoard = emptyBoard.fillSquare(new UniversalBoardCoordinate(2, 0), O);
        expected = new GameBoard( new Row(_, _, _),
                                  new Row(_, _, _),
                                  new Row(O, _, _) );

        assertSameBoard(expected, newBoard);

        newBoard = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("top right"), O);
        expected = new GameBoard( new Row(O, _, O),
                                  new Row(_, X, _),
                                  new Row(X, _, _) );

        assertSameBoard(expected, newBoard);

        newBoard = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("middle right"), O);
        expected = new GameBoard( new Row(O, _, _),
                                  new Row(_, X, O),
                                  new Row(X, _, _) );

        assertSameBoard(expected, newBoard);

        newBoard = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("bottom right"), O);
        expected = new GameBoard( new Row(O, _, _),
                                  new Row(_, X, _),
                                  new Row(X, _, O) );

        assertSameBoard(expected, newBoard);

        newBoard = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("top middle"), O);
        expected = new GameBoard( new Row(O, O, _),
                                  new Row(_, X, _),
                                  new Row(X, _, _) );

        assertSameBoard(expected, newBoard);

        newBoard = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("middle left"), O);
        expected = new GameBoard( new Row(O, _, _),
                                  new Row(O, X, _),
                                  new Row(X, _, _) );

        assertSameBoard(expected, newBoard);

        newBoard = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("middle right"), O);
        expected = new GameBoard( new Row(O, _, _),
                                  new Row(_, X, O),
                                  new Row(X, _, _) );

        assertSameBoard(expected, newBoard);

        newBoard = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("bottom center"), O);
        expected = new GameBoard( new Row(O, _, _),
                                  new Row(_, X, _),
                                  new Row(X, O, _) );

        assertSameBoard(expected, newBoard);

    }


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void occupiedSquareMoveThrowsError() throws InvalidMoveException, InvalidCoordinateException {
        thrown.expect(InvalidMoveException.class);
        thrown.expectMessage("Square is already full.");
        Board invalid = playerONext.fillSquare(new ThreeByThreeBoardCoordinate("middle center"), O);
    }

    @Test
    public void invalidMoveThrowsError() throws InvalidMoveException {
        thrown.expect(InvalidMoveException.class);
        thrown.expectMessage("Invalid move coordinate.");
        Board invalid = playerONext.fillSquare(new UniversalBoardCoordinate(5, 7), O);
    }

    @Test
    public void boardCanBeConstructedWithArbitraryDimensions() throws InvalidBoardException {
        Board fourByFour = new GameBoard(4);
    }

    @Test
    public void fourByFourBoardHasFourRowsAndFourColumns() throws InvalidBoardException {
        GameBoard fourByFour = new GameBoard(4);
        List<Row> rows = fourByFour.getRows();
        List<Row> columns = fourByFour.getColumns();
        assertEquals(4, rows.size());
        assertEquals(4, columns.size());
    }

    @Test
    public void tooSmallBoardThrowsError() throws InvalidBoardException {
        thrown.expect(InvalidBoardException.class);
        thrown.expectMessage("Please choose a board size between 3 and 10.");
        Board invalid = new GameBoard(2);
    }

    @Test
    public void tooBigBoardThrowsError() throws InvalidBoardException {
        thrown.expect(InvalidBoardException.class);
        thrown.expectMessage("Please choose a board size between 3 and 10.");
        Board invalid = new GameBoard(11);
    }

    @After
    public void cleanUp() {
        System.setOut(stdout);
    }
}
