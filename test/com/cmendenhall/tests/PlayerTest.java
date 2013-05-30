package com.cmendenhall.tests;
import com.cmendenhall.board.Board;
import com.cmendenhall.board.GameBoard;
import com.cmendenhall.board.UniversalBoardCoordinate;
import com.cmendenhall.exceptions.InvalidCoordinateException;
import com.cmendenhall.exceptions.InvalidMoveException;
import com.cmendenhall.players.HumanPlayer;
import com.cmendenhall.players.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static com.cmendenhall.TicTacToeSymbols.*;

@RunWith(JUnit4.class)
public class PlayerTest {

    private Player playerX;
    private Board board;

    @Before
    public void setUp() {
        board = new GameBoard();
        playerX = new HumanPlayer(X);
    }

    @Test
    public void playerExists() {
        Player newplayer = new HumanPlayer(O);
    }

    @Test
    public void playerXHasSymbol() {
        assertEquals('X', playerX.getSymbol());
    }

    @Test
    public void playerOHasSymbol() {
        Assert.assertEquals('O', TicTacToeTestHelper.playerO.getSymbol());
    }

    @Test
    public void playerCanMove() throws InvalidMoveException, InvalidCoordinateException {
        board = playerX.move(new UniversalBoardCoordinate(0, 0), board);
        int topleft = board.getRows().get(0).getSquare(0);
        assertEquals(X, topleft);
    }

    @Test
    public void playerCorrectlyScoresWinningBoard() {
        Assert.assertEquals(-1, TicTacToeTestHelper.playerO.scoreBoard(TicTacToeTestHelper.playerXWins));
    }

    @Test
    public void playerCorrectlyScoresBoardWithoutWin() {
        Assert.assertEquals(0, TicTacToeTestHelper.playerO.scoreBoard(TicTacToeTestHelper.noWins));
    }

    @Test
    public void playerCorrectlyScoresLosingBoard() {
        Assert.assertEquals(1, TicTacToeTestHelper.playerO.scoreBoard(TicTacToeTestHelper.playerOWins));
    }
}
