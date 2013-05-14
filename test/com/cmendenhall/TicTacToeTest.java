package com.cmendenhall;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class TicTacToeTest {

    public void assertSameBoard(Board firstBoard, Board secondBoard) {
        List<Row> firstBoardRows = firstBoard.getRows();
        List<Row> secondBoardRows = secondBoard.getRows();

        for (int i=0; i< firstBoardRows.size(); i++) {
            int[] firstBoardRowSquares = firstBoardRows.get(i).getSquares();
            int[] secondBoardRowSquares = secondBoardRows.get(i).getSquares();
            assertArrayEquals(firstBoardRowSquares, secondBoardRowSquares);
        }

    }

    public int X = 1;
    public int O = 2;
    public int _ = 0;

    public PrintStream stdout = System.out;

    public String makeDivider(Board board) {
        String divider = "";
        int dividerLength = (int) Math.ceil(3.75 * board.getRows().get(0).numberOfSquares());

        for (int i=0; i < dividerLength; i++) {
            divider += "\u2500";
        }

        divider += "\n";
        return divider;
    }

    public final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public Board emptyBoard = new GameBoard();
    public Board board = new GameBoard();

    public Board noWins = new GameBoard( new Row(O, O, X),
                                         new Row(X, X, O),
                                         new Row(O, X, X) );

    public Board playerXWins =  new GameBoard( new Row(X, _, _),
                                               new Row(X, O, _),
                                               new Row(X, _, O) );

    public Board playerOWins = new GameBoard( new Row(O, O, O),
                                              new Row(X, X, _),
                                              new Row(_, X, _) );

    public Board diagonal = new GameBoard( new Row(X, _, _),
                                           new Row(O, X, _),
                                           new Row(_, O, X) );

    public Board playerONext = new GameBoard( new Row(O, _, _),
                                              new Row(_, X, _),
                                              new Row(X, _, _) );

    public Board xInCenter = new GameBoard( new Row(_, _, _),
                                            new Row(_, X, _),
                                            new Row(_, _, _) );

    public Board playerOCanWin = new GameBoard( new Row(X, _, _),
                                                new Row(O, O, _),
                                                new Row(X, X, _) );

    public Board playerXCanWin = new GameBoard( new Row(X, _, _),
                                                new Row(X, O, _),
                                                new Row(_, _, O) );

    public Board playerXShouldBlock = new GameBoard( new Row(_, X, _),
                                                     new Row(_, X, _),
                                                     new Row(_, O, O) );

    public Board willDraw = new GameBoard( new Row(X, O, X),
                                           new Row(_, X, O),
                                           new Row(_, X, O) );

    public Board fourByFour = new GameBoard( new Row(_, _, _, _),
                                             new Row(_, _, _, _),
                                             new Row(_, _, _, _),
                                             new Row(_, _, _, _));
    public Player playerX;
    public MinimaxPlayer playerO;
    public BoardCoordinate upperRight;

    @Before
    public void sharedSetUp() {
        playerX = new Player(X);
        upperRight = new BoardCoordinate(0, 2);
        playerO = new MinimaxPlayer(O);
    }

}