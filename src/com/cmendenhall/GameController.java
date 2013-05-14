package com.cmendenhall;

import java.io.IOException;
import java.util.Properties;

public class GameController implements Controller {
    private Board board;
    private View view;
    private Player playerOne;
    private Player playerTwo;

    private String welcome;
    private String yourMove;
    private String playAgain;
    private String gameOverDraw;
    private String gameOverWin;
    private String xWins;
    private String oWins;

    public GameController(View gameView) {
        loadViewStrings();
        view = gameView;
        board = new GameBoard();
    }

    private void loadViewStrings() {
        Properties viewstrings = new Properties();
        try {
            viewstrings.load(getClass().getResourceAsStream("viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        welcome = viewstrings.getProperty("welcome");
        yourMove = viewstrings.getProperty("yourmove");
        playAgain = viewstrings.getProperty("playagain");
        gameOverDraw  = viewstrings.getProperty("gameoverdraw");
        gameOverWin = viewstrings.getProperty("gameoverwin");
        xWins = viewstrings.getProperty("xwins");
        oWins = viewstrings.getProperty("owins");
    }

    public void setPlayerOne(Player player) {
        playerOne = player;
    }

    public void setPlayerTwo(Player player) {
        playerTwo = player;
    }

    public void loadGame(Board boardState) {
        board = boardState;
    }

    public void newGame() {
       board = new GameBoard();
       view.displayBoard(board);
    }

    public void checkForGameOver() {
        checkForWins();
        checkForDraw();
    }

    private void checkForWins() {
        if (board.hasWin()) {
            view.displayBoard(board);
            view.displayMessage(gameOverWin);
            view.endGame();
        }
    }

    private void checkForDraw() {
        if (board.isFull() && !board.hasWin()) {
            view.displayBoard(board);
            view.displayMessage(gameOverDraw);
            view.endGame();
        }
    }

    public void restartGame() {
       view.displayMessage(playAgain);
       String restart = view.getInput();
       if (restart == "n") {
           view.endGame();
       } else {
           newGame();
       }
    }

    private Player getCurrentPlayer() {
        int turn = board.nextTurn();
        if (turn == playerOne.getGamePiece()) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public void playRound() {
        checkForGameOver();
        board = getNextMove();
        view.displayBoard(board);
        playRound();
    }

    private Board getNextMove() {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.isHuman()) {
            view.displayMessage(yourMove);
            String input = view.getInput();
            try {
                BoardCoordinate move = new BoardCoordinate(input);
                return currentPlayer.move(move, board);
            } catch (InvalidCoordinateException e) {
                view.displayMessage(e.getMessage());
                return getNextMove();
            } catch (InvalidMoveException e) {
                view.displayMessage(e.getMessage());
                return getNextMove();
            }
        } else {
            MinimaxPlayer ai = (MinimaxPlayer) currentPlayer;
            return ai.bestMove(board);
        }
    }

}
