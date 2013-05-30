package com.cmendenhall.tests;
import com.cmendenhall.board.Board;
import com.cmendenhall.board.GameBoard;
import com.cmendenhall.board.Row;
import com.cmendenhall.board.ThreeByThreeBoardCoordinate;
import com.cmendenhall.controllers.Controller;
import com.cmendenhall.controllers.GameController;
import com.cmendenhall.exceptions.InvalidBoardException;
import com.cmendenhall.exceptions.InvalidCoordinateException;
import com.cmendenhall.exceptions.InvalidMoveException;
import com.cmendenhall.players.MinimaxPlayer;
import com.cmendenhall.mocks.MockTerminalView;
import com.cmendenhall.utils.OutputRecorder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static com.cmendenhall.TicTacToeSymbols.*;

@RunWith(JUnit4.class)
public class MinimaxPlayerTest extends TicTacToeTest {

    private MinimaxPlayer playerX;

    @Before
    public void setUp() throws InvalidCoordinateException, InvalidMoveException {
        playerX = new MinimaxPlayer(X);
        TicTacToeTestHelper.board = playerX.move(new ThreeByThreeBoardCoordinate("middle center"), new GameBoard());
    }

    @Test
    public void minimaxPlayerExists() {
        new MinimaxPlayer(O);
    }

    @Test
    public void winningBoardScoreIsOne() {
        Assert.assertEquals(1, TicTacToeTestHelper.playerO.scoreBoard(TicTacToeTestHelper.playerOWins));
    }

    @Test
    public void drawBoardScoreIsZero() {
        Assert.assertEquals(0, TicTacToeTestHelper.playerO.scoreBoard(TicTacToeTestHelper.noWins));
    }

    @Test
    public void losingBoardScoreIsNegativeOne() {
        Assert.assertEquals(-1, TicTacToeTestHelper.playerO.scoreBoard(TicTacToeTestHelper.playerXWins));
    }

    @Test
    public void winningMoveXIsBestMove() {
        Board expected = new GameBoard( new Row(X, _, _),
                                        new Row(X, O, _),
                                        new Row(X, _, O) );

        Board actual = playerX.bestMove(TicTacToeTestHelper.playerXCanWin);
        TicTacToeTestHelper.assertSameBoard(expected, actual);
    }

    @Test
    public void winningMoveOIsBestMove() {
        Board expected = new GameBoard( new Row(X, _, _),
                                        new Row(O, O, O),
                                        new Row(X, X, _));

        Board actual = TicTacToeTestHelper.playerO.bestMove(TicTacToeTestHelper.playerOCanWin);
        TicTacToeTestHelper.assertSameBoard(expected, actual);
    }

    @Test
    public void blockIsBestMove() {
        Board expected = new GameBoard( new Row(_, X, _),
                                        new Row(_, X, _),
                                        new Row(X, O, O) );

        Board actual = playerX.bestMove(TicTacToeTestHelper.playerXShouldBlock);
        TicTacToeTestHelper.assertSameBoard(expected, actual);

    }

    @Test
    public void threeByThreeLookAheadDepthIsEight() {
        assertEquals(8, playerX.calculateDepth(TicTacToeTestHelper.xInCenter));
    }

    @Test
    public void fourByFourLookAheadDepthIsFour() throws InvalidBoardException {
        assertEquals(4, playerX.calculateDepth(new GameBoard(4)));
    }

    @Test
    public void lookAheadDepthIsAlwaysAtLeastTwo() throws InvalidBoardException {
        assertEquals(2, playerX.calculateDepth(new GameBoard(9)));
        assertEquals(2, playerX.calculateDepth(new GameBoard(10)));
    }

    @Test
    public void fullBoardIsTerminalNode() {
        assertTrue(playerX.isTerminal(TicTacToeTestHelper.noWins));

    }

    @Test
    public void winningBoardIsTerminalNode() {
        assertTrue(playerX.isTerminal(TicTacToeTestHelper.playerXWins));
    }

    @Test
    public void boardWithEmptySpacesAndNoWinIsNotTerminal() {
        assertFalse(playerX.isTerminal(TicTacToeTestHelper.playerONext));
    }

    @Test
    public void playerXCanWaitFiveTurnsOnFourByFourBoard() throws InvalidBoardException {
        int randomMoves = playerX.randomMoveLimit(new GameBoard(4));
        assertEquals(5, randomMoves);
    }

    @Test
    public void playerXCanWaitFourTurnsOnThreeByThreeBoard() {
        int randomMoves = playerX.randomMoveLimit(new GameBoard());
        assertEquals(4, randomMoves);
    }

    @Test
    public void playerOCanWaitThreeTurnsOnThreeByThreeBoard() {
        int randomMoves = TicTacToeTestHelper.playerO.randomMoveLimit(new GameBoard());
        assertEquals(3, randomMoves);
    }

    @Test
    public void playerOCanWaitFourTurnsOnFourByFourBoard() throws InvalidBoardException {
        int randomMoves = TicTacToeTestHelper.playerO.randomMoveLimit(new GameBoard(4));
        assertEquals(4, randomMoves);
    }


    private void playGame(Integer boardSize) throws UnsupportedEncodingException {
        MockTerminalView view = new MockTerminalView();
        Controller controller = new GameController(view);

        view.enqueueInput(boardSize.toString(), "c", "c");

        startRecorder();

        try {
            controller.setUp();
            controller.newGame();
            controller.startGame();
        } catch (Exception e) {
            recorder.popLastOutput();
            String itsADraw = recorder.popLastOutput();
            assertEquals("Game over: It's a draw.", itsADraw);
        }
    }

    @Test
    public void minimaxPlayersShouldAlwaysTie() throws UnsupportedEncodingException {
            playGame(3);
            playGame(4);
            playGame(5);
    }

}