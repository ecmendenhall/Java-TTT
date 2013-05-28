package com.cmendenhall;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols.X;

public class GameController implements Controller {
    private Board board;
    private View view;
    private Player playerOne;
    private Player playerTwo;

    private String welcome;
    private String divider;
    private String yourMove;
    private String yourMoveThreeSquares;
    private String playAgain;
    private String gameOverDraw;
    private String gameOverWin;
    private String xWins;
    private String oWins;
    private String choosePlayerOne;
    private String choosePlayerTwo;
    private String boardSize;

    public GameController(View gameView) {
        loadViewStrings();
        view = gameView;
        board = new GameBoard();
    }

    private void loadViewStrings() {
        Properties viewStrings = new Properties();
        try {
            viewStrings.load(getClass().getResourceAsStream("/viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        welcome = viewStrings.getProperty("welcome");
        divider = viewStrings.getProperty("divider");
        yourMove = viewStrings.getProperty("yourmove");
        yourMoveThreeSquares = viewStrings.getProperty("yourmovethreesquares");
        playAgain = viewStrings.getProperty("playagain");
        gameOverDraw = viewStrings.getProperty("gameoverdraw");
        gameOverWin = viewStrings.getProperty("gameoverwin");
        xWins = viewStrings.getProperty("xwins");
        oWins = viewStrings.getProperty("owins");
        choosePlayerOne = viewStrings.getProperty("chooseplayerone");
        choosePlayerTwo = viewStrings.getProperty("chooseplayertwo");
        boardSize = viewStrings.getProperty("boardsize");

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

    private Board getBoardSize() {
        view.displayMessage(boardSize);
        String boardDimensions = view.getInput();
        while (boardDimensions == "") {
            boardDimensions = view.getInput();
        }
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
        String playerMessage = (number == X) ? choosePlayerOne : choosePlayerTwo;
        view.displayMessage(playerMessage);
        String playerType = view.getInput();
        while (playerType == "") {
            playerType = view.getInput();
        }
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
        view.reload();
        loadGame(new GameBoard());
    }

    public void startGame() throws GameOverException {
        view.displayBoard(board);
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
        if (BoardAnalyzer.hasWin(board)) {
            view.displayBoard(board);
            String winnerMessage = getWinnerMessage(BoardAnalyzer.winnerIs(board));
            view.displayMessage(gameOverWin + winnerMessage);
            restartGame();
        }
    }

    private void checkForDraw() throws GameOverException {
        if (BoardAnalyzer.isFull(board) && !BoardAnalyzer.hasWin(board)) {
            view.displayBoard(board);
            view.displayMessage(gameOverDraw);
            restartGame();
        }
    }

    public void restartGame() throws GameOverException {
        view.reload();
        view.displayMessage(playAgain);
        String restart = view.getInput();
        if (restart.equals("n")) {
            view.endGame();
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

    public void playRound() throws GameOverException {
        checkForGameOver();
        board = getNextMove();
        view.displayBoard(board);
        playRound();
    }

    private String nextTurnMessage() {
        Player currentPlayer = getCurrentPlayer();
        char currentSymbol = currentPlayer.getSymbol();
        String moveMessage = (board.getRows().size() == 3) ? yourMoveThreeSquares : yourMove;
        return MessageFormat.format(moveMessage, board.getSize() - 1) + " " + currentSymbol + ".";
    }

    private Board getNextMove() {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.isHuman()) {
            view.displayMessage(nextTurnMessage());
            String input = view.getInput();
            while (input == "") {
                input = view.getInput();
            }
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
            MinimaxPlayer ai = (MinimaxPlayer) currentPlayer;
            return ai.bestMove(board);
        }
    }

}
