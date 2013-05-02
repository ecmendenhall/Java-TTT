package com.ecmendenhall;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

public class GameTreeTest extends TicTacToeTest {

    private GameTree tree;
    private Board twomoremoves;

    @Before
    public void setUp() {
        twomoremoves = new Board( new Row(O, X, _),
                                  new Row(O, X, X),
                                  new Row(X, O, _) );
        tree = new GameTree(twomoremoves);
    }

    @Test
    public void gameTreeRootContainsGameState() {
        GameTree tree = new GameTree(playerxwins);
        assertEquals(playerxwins, tree.root);
    }

    @Test
    public void  gameTreeRootHasTwoChildren() {
        assertEquals(2, tree.children.size());
    }

}
