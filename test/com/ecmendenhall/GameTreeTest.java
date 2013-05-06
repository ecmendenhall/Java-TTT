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
    private Board twoMoreMoves;
    private Board threeMoreMoves;
    private GameTree.Node twoMoreMovesNode;
    private MinimaxPlayer playerX;

    @Before
    public void setUp() throws InvalidPlayerException, InvalidMoveException {
        twoMoreMoves = new Board( new Row(O, X, _),
                                  new Row(O, X, X),
                                  new Row(X, O, _) );

        twoMoreMovesNode = new GameTree.Node(twoMoreMoves);

        threeMoreMoves = new Board( new Row(X, O, _),
                                    new Row(_, X, _),
                                    new Row(O, X, O) );

        tree = new GameTree(twoMoreMoves);
        playerX = new MinimaxPlayer(X);
    }

    @Test
    public void gameTreeRootContainsGameState() throws InvalidMoveException {
        GameTree tree = new GameTree(playerXWins);
        assertEquals(playerXWins, tree.root);
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

        assertTrue(leafone.equals(leaves.get(0).gameState));
        assertTrue(leaftwo.equals(leaves.get(1).gameState));
    }

    @Test
    public void gameTreeReturnsCorrectNumberOfLeaves() throws InvalidMoveException {
        GameTree gameTree = new GameTree(threeMoreMoves);
        List<GameTree.Node> leaves = gameTree.getLeaves();
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
        assertEquals(1, twoMoreMovesNode.maxLeafScore(playerX));
    }

    @Test
    public void minLeafScoreIsOne() {
        assertEquals(0, twoMoreMovesNode.minLeafScore(playerX));
    }

    @Test
    public void fullBoardIsTerminalNode() throws InvalidMoveException {
        assertTrue(new GameTree.Node(noWins).isTerminal());

    }

    @Test
    public void winningBoardIsTerminalNode() throws InvalidMoveException {
        assertTrue(new GameTree.Node(playerXWins).isTerminal());
    }

    @Test
    public void boardWithEmptySpacesAndNoWinIsNotTerminal() throws InvalidMoveException {
        assertFalse(new GameTree.Node(playerONext).isTerminal());
    }
}
