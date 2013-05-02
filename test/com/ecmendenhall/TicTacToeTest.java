package com.ecmendenhall;

import org.junit.Before;

import java.io.PrintStream;

public class TicTacToeTest {

    public int X = 1;
    public int O = 2;
    public int _ = 0;

    public Board emptyboard;
    public Board board;
    public Board nowins;
    public Board playerxwins;
    public Board playerowins;
    public Board diagonal;
    public Board playeronext;
    public Board xincenter;


    public Player playerx;
    public BoardCoordinate upperright;

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
    public void sharedSetUp() {
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
        xincenter = playerx.move(new BoardCoordinate("middle center"), new Board());
    }
}