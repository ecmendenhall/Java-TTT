package com.cmendenhall;

import java.io.IOException;
import java.util.Properties;
import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;

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
        playerOne = loadPlayer(X);
        playerTwo = loadPlayer(O);
    }

    private Player loadPlayer(int number) {
        String playerMessage = "Choose type for Player " + number + " [h]uman/[c]omputer: ";
        view.displayMessage(playerMessage);
        String playerType = view.getInput();
        if (playerType.equals("h")) {
            return new HumanPlayer(number);
        } else if (playerType.equals("c")) {
            return new MinimaxPlayer(number);
        } else {
            return loadPlayer(number);
        }
    }

    public void newGame() {
       view.displayMessage(welcome);
       view.displayMessage(divider);
       loadGame(new GameBoard());
       view.displayBoard(board);
    }

    public void startGame() throws GameOverException {
        playRound();
    }

    public void checkForGameOver() throws GameOverException {
        checkForWins();
        checkForDraw();
    }

    private String getWinnerMessage(int winner) {
        return (winner == X) ? xWins : oWins;
    }

    private void checkForWins() throws GameOverException {
        if (board.hasWin()) {
            view.displayBoard(board);
            String winnerMessage = getWinnerMessage(board.winnerIs());
            view.displayMessage(gameOverWin + winnerMessage);
            view.endGame();
        }
    }

    private void checkForDraw() throws GameOverException {
        if (board.isFull() && !board.hasWin()) {
            view.displayBoard(board);
            view.displayMessage(gameOverDraw);
            view.endGame();
        }
    }

    public void restartGame() throws GameOverException {
       view.displayMessage(playAgain);
       String restart = view.getInput();
       if (restart.equals("n")) {
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
