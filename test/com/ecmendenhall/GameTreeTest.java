package com.ecmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
    private Board threemoremoves;
    private GameTree.Node twomoremovesnode;
    private MinimaxPlayer playerx;

    @Before
    public void setUp() throws InvalidPlayerException, InvalidMoveException {
        twomoremoves = new Board( new Row(O, X, _),
                                  new Row(O, X, X),
                                  new Row(X, O, _) );

        twomoremovesnode = new GameTree.Node(twomoremoves);

        threemoremoves = new Board( new Row(X, O, _),
                                    new Row(_, X, _),
                                    new Row(O, X, O) );

        tree = new GameTree(twomoremoves);
        playerx = new MinimaxPlayer(X);
    }

    @Test
    public void gameTreeRootContainsGameState() throws InvalidMoveException {
        GameTree tree = new GameTree(playerxwins);
        assertEquals(playerxwins, tree.root);
    }

    @Test
    public void  gameTreeRootHasTwoChildren() {
        assertEquals(2, tree.children.size());
    }

    @Test
    public void gameTreeReturnsCorrectLeaves() {
        List<GameTree.Node> leaves = tree.getLeaves();

        Board leaftwo = new Board( new Row(O, X, X),
                                   new Row(O, X, X),
                                   new Row(X, O, O) );

        Board leafone = new Board( new Row(O, X, O),
                                   new Row(O, X, X),
                                   new Row(X, O, X) );

        assertTrue(leafone.equals(leaves.get(0).gamestate));
        assertTrue(leaftwo.equals(leaves.get(1).gamestate));
    }

    @Test
    public void gameTreeReturnsCorrectNumberOfLeaves() throws InvalidMoveException {
        GameTree gametree = new GameTree(threemoremoves);
        List<GameTree.Node> leaves = gametree.getLeaves();
        assertEquals(6, leaves.size());
    }

    /* @Test
    public void fullGameTreeContainsAllPossibleGames() {
        GameTree gametree = new GameTree(new Board());
        List<GameTree.Node> leaves = gametree.getLeaves();
        assertEquals(362880, leaves.size());
    } */

    @Test
    public void maxLeafScoreIsOne() {
        assertEquals(1, twomoremovesnode.maxLeafScore(playerx));
    }

    @Test
    public void minLeafScoreIsOne() {
        assertEquals(0, twomoremovesnode.minLeafScore(playerx));
    }

    @Test
    public void fullBoardIsTerminalNode() throws InvalidMoveException {
        assertTrue(new GameTree.Node(nowins).isTerminal());

    }

    @Test
    public void winningBoardIsTerminalNode() throws InvalidMoveException {
        assertTrue(new GameTree.Node(playerxwins).isTerminal());
    }

    @Test
    public void boardWithEmptySpacesAndNoWinIsNotTerminal() throws InvalidMoveException {
        assertFalse(new GameTree.Node(playeronext).isTerminal());
    }
}
