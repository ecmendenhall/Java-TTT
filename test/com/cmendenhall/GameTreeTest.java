package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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

    @Test
    public void gameTreeReturnsNextStates () throws InvalidMoveException {

        List<Board> nextBoards = new ArrayList<Board>();

        nextBoards.add(new GameBoard(new Row(O, _, _),
                new Row(_, X, _),
                new Row(_, _, _)));

        nextBoards.add(new GameBoard(new Row(_, O, _),
                new Row(_, X, _),
                new Row(_, _, _)));

        nextBoards.add(new GameBoard(new Row(_, _, O),
                new Row(_, X, _),
                new Row(_, _, _)));

        nextBoards.add(new GameBoard(new Row(_, _, _),
                new Row(O, X, _),
                new Row(_, _, _)));

        nextBoards.add(new GameBoard(new Row(_, _, _),
                new Row(_, X, O),
                new Row(_, _, _)));

        nextBoards.add(new GameBoard(new Row(_, _, _),
                new Row(_, X, _),
                new Row(O, _, _)));


        nextBoards.add(new GameBoard(new Row(_, _, _),
                new Row(_, X, _),
                new Row(_, O, _)));

        nextBoards.add(new GameBoard(new Row(_, _, _),
                new Row(_, X, _),
                new Row(_, _, O)));

        List<Board> nextStates = BoardAnalyzer.getNextStates(xInCenter);

        for (int i=0; i < nextStates.size(); i++) {
            Board expected = nextBoards.get(i);
            Board actual = nextStates.get(i);
            assertSameBoard(expected, actual);
        }
    }
}
