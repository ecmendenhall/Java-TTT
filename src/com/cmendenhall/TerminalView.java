package com.cmendenhall;

import java.io.IOException;
import java.util.Properties;

import static com.cmendenhall.TicTacToeSymbols.X;

public class TerminalView {

    protected static IOHandler io;

    private String welcome;
    private String yourmove;
    private String playagain;
    private String gameoverdraw;
    private String gameoverwin;
    private String xwins;
    private String owins;

    public TerminalView() {
        loadViewStrings();
        setUpConsole();
    }

    private void loadViewStrings() {
        Properties viewstrings = new Properties();
        try {
            viewstrings.load(getClass().getResourceAsStream("viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        welcome = viewstrings.getProperty("welcome");
        yourmove = viewstrings.getProperty("yourmove");
        playagain = viewstrings.getProperty("playagain");
        gameoverdraw  = viewstrings.getProperty("gameoverdraw");
        gameoverwin = viewstrings.getProperty("gameoverwin");
        xwins = viewstrings.getProperty("xwins");
        owins = viewstrings.getProperty("owins");
    }

    private void setUpConsole() {
        if (System.console() == null) {
            System.err.println("No system console found.");
            io = new CharStreamConsole();
        } else {
            io = new SystemConsole();
        }
    }

    public void newGame(Board board, GameController gameController) {
        System.out.println(welcome);
        getNextMove(board, gameController);
    }

    public void print(Board board) {
        System.out.println();
        board.print();
    }

    public BoardCoordinate prompt() {
        String locationPhrase = io.readLine(yourmove);
        try {
            return new BoardCoordinate(locationPhrase);
        } catch (InvalidCoordinateException e) {
            System.out.println(e.getMessage());
        }
        return prompt();
    }

    public void passMoveToController(BoardCoordinate move, GameController gameController) {
        gameController.processMove(move, this);
    }

    public void getNextMove(Board board, GameController gameController) {
        print(board);
        BoardCoordinate nextMove = prompt();
        passMoveToController(nextMove, gameController);
    }

    public void promptWithMessage(String message, Board board, GameController gameController) {
        System.out.println(message);
        getNextMove(board, gameController);
    }

    private void playAgainPrompt(GameController gameController) {
        String playAgain = io.readLine(playagain);
        if (playAgain.equalsIgnoreCase("y")) {
            gameController.newGame(this);
        } else if (playAgain.equalsIgnoreCase("n")) {
            System.exit(0);
        } else {
            playAgainPrompt(gameController);
        }
    }

    public void drawEndGame(Board board, GameController gameController) {
        System.out.println();
        board.print();
        System.out.println(gameoverdraw);
        playAgainPrompt(gameController);
    }

    public void winEndGame(Board board, GameController gameController) {
        int winner = board.getWinningRow().winner();
        String messageTemplate = gameoverwin;
        String winnerDescription = (winner == X) ? xwins : owins;
        System.out.println();
        board.print();
        System.out.println(messageTemplate + winnerDescription);
        playAgainPrompt(gameController);
    }
}
