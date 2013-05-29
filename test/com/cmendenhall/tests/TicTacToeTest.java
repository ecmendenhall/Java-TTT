package com.cmendenhall.tests;

import com.cmendenhall.board.*;
import com.cmendenhall.players.HumanPlayer;
import com.cmendenhall.players.MinimaxPlayer;
import com.cmendenhall.players.Player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static com.cmendenhall.TicTacToeSymbols.*;

import static org.junit.Assert.assertArrayEquals;

public final class TicTacToeTest {

    public static void assertSameBoard(Board firstBoard, Board secondBoard) {
        List<Row> firstBoardRows = firstBoard.getRows();
        List<Row> secondBoardRows = secondBoard.getRows();

        for (int i=0; i< firstBoardRows.size(); i++) {
            int[] firstBoardRowSquares = firstBoardRows.get(i).getSquares();
            int[] secondBoardRowSquares = secondBoardRows.get(i).getSquares();
            assertArrayEquals(firstBoardRowSquares, secondBoardRowSquares);
        }

    }

    public static PrintStream stdout = System.out;

    public static String makeDivider(Board board) {
        String divider = "";
        int dividerLength = (int) Math.ceil(3.75 * board.getRows().get(0).numberOfSquares());

        for (int i=0; i < dividerLength; i++) {
            divider += "-";
        }

        divider += "\n";
        return divider;
    }

    public static final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public static Board emptyBoard = new GameBoard();
    public static Board board = new GameBoard();

    public static Board noWins = new GameBoard( new Row(O, O, X),
                                                new Row(X, X, O),
                                                new Row(O, X, X) );

    public static Board playerXWins =  new GameBoard( new Row(X, _, _),
                                                      new Row(X, O, _),
                                                      new Row(X, _, O) );

    public static Board playerOWins = new GameBoard( new Row(O, O, O),
                                                     new Row(X, X, _),
                                                     new Row(_, X, _) );

    public static Board diagonal = new GameBoard( new Row(X, _, _),
                                                  new Row(O, X, _),
                                                  new Row(_, O, X) );

    public static Board playerONext = new GameBoard( new Row(O, _, _),
                                                     new Row(_, X, _),
                                                     new Row(X, _, _) );

    public static Board xInCenter = new GameBoard( new Row(_, _, _),
                                                   new Row(_, X, _),
                                                   new Row(_, _, _) );

    public static Board playerOCanWin = new GameBoard( new Row(X, _, _),
                                                       new Row(O, O, _),
                                                       new Row(X, X, _) );

    public static Board playerXCanWin = new GameBoard( new Row(X, _, _),
                                                       new Row(X, O, _),
                                                       new Row(_, _, O) );

    public static Board playerXShouldBlock = new GameBoard( new Row(_, X, _),
                                                            new Row(_, X, _),
                                                            new Row(_, O, O) );

    public static Board willDraw = new GameBoard( new Row(X, O, X),
                                                  new Row(_, X, O),
                                                  new Row(_, X, O) );

    public static Board fourByFour = new GameBoard( new Row(_, _, _, _),
                                                    new Row(_, _, _, _),
                                                    new Row(_, _, _, _),
                                                    new Row(_, _, _, _));

    public static Player playerX = new HumanPlayer(X);
    public static MinimaxPlayer playerO = new MinimaxPlayer(O);
    public static BoardCoordinate upperRight = new UniversalBoardCoordinate(0, 2);
}