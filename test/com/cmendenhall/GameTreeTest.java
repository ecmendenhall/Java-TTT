package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GameTreeTest extends TicTacToeTest {

    private GameTree tree;
    private Board twoMoreMoves;
    private Board threeMoreMoves;
    private GameTree twoMoreMovesNode;
    private MinimaxPlayer playerX;

    @Before
    public void setUp() {
        twoMoreMoves = new GameBoard( new Row(O, X, _),
                                      new Row(O, X, X),
                                      new Row(X, O, _) );

        twoMoreMovesNode = new GameTree(twoMoreMoves);

        threeMoreMoves = new GameBoard( new Row(X, O, _),
                                        new Row(_, X, _),
                                        new Row(O, X, O) );

        tree = new GameTree(twoMoreMoves);
        playerX = new MinimaxPlayer(X);
    }

    @Test
    public void gameTreeRootContainsGameState() {
        GameTree tree = new GameTree(playerXWins);
        assertEquals(playerXWins, tree.gameState);
    }

    @Test
    public void  gameTreeRootHasTwoChildren() {
        assertEquals(2, tree.children.size());
    }

    @Test
    public void gameTreeReturnsCorrectNumberOfLeaves() {
        GameTree gameTree = new GameTree(threeMoreMoves);
        List<GameTree> leaves = gameTree.getLeaves();
        assertEquals(6, leaves.size());
    }

    /* @Test
    public void fullGameTreeContainsAllPossibleGames() {
        GameTree gametree = new GameTree(new Board());
        List<GameTree> leaves = gametree.getLeaves();
        assertEquals(362880, leaves.size());
    } */


    @Test
    public void fullBoardIsTerminalNode() {
        assertTrue(new GameTree(noWins).isTerminal());

    }

    @Test
    public void winningBoardIsTerminalNode() {
        assertTrue(new GameTree(playerXWins).isTerminal());
    }

    @Test
    public void boardWithEmptySpacesAndNoWinIsNotTerminal() {
        assertFalse(new GameTree(playerONext).isTerminal());
    }
}
