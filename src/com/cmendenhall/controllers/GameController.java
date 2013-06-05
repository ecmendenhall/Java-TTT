package com.cmendenhall.controllers;

import com.cmendenhall.board.*;
import com.cmendenhall.exceptions.InvalidBoardException;
import com.cmendenhall.exceptions.InvalidCoordinateException;
import com.cmendenhall.exceptions.InvalidMoveException;
import com.cmendenhall.players.HumanPlayer;
import com.cmendenhall.players.MinimaxPlayer;
import com.cmendenhall.players.Player;
import com.cmendenhall.utils.ExitManager;
import com.cmendenhall.views.View;

import java.text.MessageFormat;
import java.util.HashMap;

import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols.X;

public class GameController implements Controller {
    private ExitManager exitManager;
    private HashMap<String, String> viewStrings;
    private Board board;
    private View view;
    private Player playerOne;
    private Player playerTwo;

    public GameController(View gameView) {
        exitManager = new ExitManager();
        view = gameView;
        viewStrings = view.getStrings();
        board = new GameBoard();
    }

    public GameController(View gameView, ExitManager customExitManager) {
        exitManager = customExitManager;
        view = gameView;
        viewStrings = view.getStrings();
        board = new GameBoard();
    }

    public void setPlayerOne(Player player) {
        playerOne = player;
    }

    public void setPlayerTwo(Player player) {
        playerTwo = player;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void loadGame(Board boardState) {
        board = boardState;
    }

    public void setUp() {
        board = getBoardSize();
        playerOne = loadPlayer(X);
        playerTwo = loadPlayer(O);
    }

    private String pollForInput() {
        String input = view.getInput();
        while (input == "" || input == null) {
            input = view.getInput();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return input;
    }

    private Board getBoardSize() {
        view.displayMessage(viewStrings.get("boardsize"));
        String boardDimensions = pollForInput();
        try {
            Integer size = Integer.parseInt(boardDimensions);
            if (size > 0) {
                return new GameBoard(size);
            } else {
                return getBoardSize();
            }
        } catch (Exception e) {
            if (e.getClass() == InvalidBoardException.class) {
                view.displayMessage(e.getMessage());
            }
            return getBoardSize();
        }
    }

    private Player loadPlayer(int number) {
        String playerMessage = (number == X) ? viewStrings.get("chooseplayerone") : viewStrings.get("chooseplayertwo");
        view.displayMessage(playerMessage);
        String playerType = pollForInput();

        if (playerType.equals("h")) {
            return new HumanPlayer(number);
        } else if (playerType.equals("c")) {
            return new MinimaxPlayer(number);
        } else {
            return loadPlayer(number);
        }
    }

    public void newGame() {
        view.displayMessage(viewStrings.get("welcome"));
        view.displayMessage(viewStrings.get("divider"));
        view.reload();
        loadGame(new GameBoard());
    }

    public void startGame()  {
        view.displayBoard(board);
        playRound();
    }

    public void checkForGameOver()  {
        checkForWins();
        checkForDraw();
    }

    private String getWinnerMessage(int winner) {
        return (winner == X) ? viewStrings.get("xwins") : viewStrings.get("owins");
    }

    private void checkForWins()  {
        if (BoardAnalyzer.hasWin(board)) {
            view.displayBoard(board);
            String winnerMessage = getWinnerMessage(BoardAnalyzer.winnerIs(board));
            view.displayMessage(viewStrings.get("gameoverwin") + winnerMessage);
            restartGame();
        }
    }

    private void checkForDraw()  {
        if (BoardAnalyzer.hasDraw(board) && !BoardAnalyzer.hasWin(board)) {
            view.displayBoard(board);
            view.displayMessage(viewStrings.get("gameoverdraw"));
            restartGame();
        }
    }

    public void endGame() {
        exitManager.exit();
    }

    public void restartGame()  {
        view.reload();
        view.displayMessage(viewStrings.get("playagain"));
        String restart = view.getInput();
        if (restart.equals("n")) {
            endGame();
        } else {
            loadGame(new GameBoard());
            setUp();
            startGame();
        }
    }

    private Player getCurrentPlayer() {
        int turn = BoardAnalyzer.nextTurn(board);
        if (turn == playerOne.getGamePiece()) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public void playRound()  {
        checkForGameOver();
        board = getNextMove();
        view.displayBoard(board);
        playRound();
    }

    private String nextTurnMessage() {
        Player currentPlayer = getCurrentPlayer();
        char currentSymbol = currentPlayer.getSymbol();
        String moveMessage = (board.getRows().size() == 3) ? viewStrings.get("yourmovethreesquares") : viewStrings.get("yourmove");
        return MessageFormat.format(moveMessage, board.getSize() - 1) + " " + currentSymbol + ".";
    }

    private Board getNextMove() {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.isHuman()) {
            view.displayMessage(nextTurnMessage());
            String input = pollForInput();
            try {
                BoardCoordinate move;
                if (board.getRows().size() == 3) {
                    move = new ThreeByThreeBoardCoordinate(input);
                } else {
                    move = new UniversalBoardCoordinate(input);
                }

                return currentPlayer.move(move, board);
            } catch (InvalidCoordinateException e) {
                view.displayMessage(e.getMessage());
                return getNextMove();
            } catch (InvalidMoveException e) {
                view.displayMessage(e.getMessage());
                return getNextMove();
            }
        } else {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MinimaxPlayer ai = (MinimaxPlayer) currentPlayer;
            return ai.bestMove(board);
        }
    }

}
