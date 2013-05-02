package com.ecmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class MinimaxPlayerTest {
    private final int X = 1;
    private final int O = 2;
    private final int _ = 0;

    private Player playerx;
    private MinimaxPlayer playero;
    private Board board;

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
        playerx = new Player(X);
        playero = new MinimaxPlayer(O);
        board = playerx.move(new BoardCoordinate("middle center"), new Board());
    }

    @Test
    public void minimaxPlayerExists() {
        new MinimaxPlayer(O);
    }

    @Test
    public void minimaxPlayerPredictsNextStates () {

        Board [] expectedstates = { new Board ( new Row (O, _, _),
                                                new Row (_, X, _),
                                                new Row (_, _, _) ),

                                    new Board ( new Row (_, O, _),
                                                new Row (_, X, _),
                                                new Row (_, _, _) ),

                                    new Board ( new Row (_, _, O),
                                                new Row (_, X, _),
                                                new Row (_, _, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (O, X, _),
                                                new Row (_, _, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (_, X, O),
                                                new Row (_, _, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (_, X, _),
                                                new Row (O, _, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (_, X, _),
                                                new Row (_, O, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (_, X, _),
                                                new Row (_, _, O) )};

        List<Board> nextstates = playero.getNextStates(board);

        for (int i=0; i < expectedstates.length; i++) {
            Board next = nextstates.get(i);
            Board expected = expectedstates[i];
            assertTrue(sameBoards(expected, next));
        }
    }
}