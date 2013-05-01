package com.ecmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

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

    @Before
    public void setUp() {
        board = new Board();
        playerx = new Player(X);
        playero = new MinimaxPlayer(O);
    }

    @Test
    public void minimaxPlayerExists() {
        new MinimaxPlayer(O);
    }

    @Test
    public void minimaxPlayerPredictsNextStates () {

        playerx.move (new BoardCoordinate ("middle center"), board);

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
                                                new Row (_, X, O),
                                                new Row (_, _, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (_, X, _),
                                                new Row (_, _, O) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (_, X, _),
                                                new Row (_, O, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (_, X, _),
                                                new Row (O, _, _) ),

                                    new Board ( new Row (_, _, _),
                                                new Row (O, X, _),
                                                new Row (_, _, _) ) };

        assertArrayEquals(expectedstates, playero.getNextStates());
    }
}