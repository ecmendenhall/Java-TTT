package com.ecmendenhall;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TicTacToeTest {

    public int X = 1;
    public int O = 2;
    public int _ = 0;

    public PrintStream stdout = System.out;

    public final String HORIZONTAL_LINE = "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n";

    public final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public Board emptyBoard = new Board();
    public Board board = new Board();

    public Board noWins = new Board( new Row(O, O, X),
                                     new Row(X, X, O),
                                     new Row(O, X, X) );

    public Board playerXWins =  new Board( new Row(X, _, _),
                                           new Row(X, O, _),
                                           new Row(X, _, O) );

    public Board playerOWins = new Board( new Row(O, O, O),
                                          new Row(X, X, _),
                                          new Row(_, X, _) );

    public Board diagonal = new Board( new Row(X, _, _),
                                       new Row(O, X, _),
                                       new Row(_, O, X) );

    public Board playerONext = new Board( new Row(O, _, _),
                                          new Row(_, X, _),
                                          new Row(X, _, _) );

    public Board xInCenter = new Board( new Row(_, _, _),
                                        new Row(_, X, _),
                                        new Row(_, _, _) );

    public Board playerOCanWin = new Board( new Row(X, _, _),
                                            new Row(O, O, _),
                                            new Row(X, X, _) );

    public Board playerXCanWin = new Board( new Row(X, _, _),
                                            new Row(X, O, _),
                                            new Row(_, _, O) );

    public Board playerXShouldBlock = new Board( new Row(_, X, _),
                                                 new Row(_, X, _),
                                                 new Row(_, O, O) );
    public Board willDraw = new Board( new Row(X, O, X),
                                       new Row(_, X, O),
                                       new Row(_, X, O) );

    public Player playerX;
    public MinimaxPlayer playerO;
    public BoardCoordinate upperRight;

    @Before
    public void sharedSetUp() throws InvalidPlayerException {
        playerX = new Player(X);
        upperRight = new BoardCoordinate(0, 2);
        playerO = new MinimaxPlayer(O);
    }
}