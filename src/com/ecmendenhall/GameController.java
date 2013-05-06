package com.ecmendenhall;

import java.io.IOException;

public class GameController {
    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;

    private Board currentBoard;
    private Player human;
    private MinimaxPlayer computer;

    public GameController() throws InvalidPlayerException {
        currentBoard = new Board();
        human = new Player(X);
        computer = new MinimaxPlayer(O);
    }

    public GameController(Board startingBoard) throws InvalidPlayerException {
        currentBoard = startingBoard;
        human = new Player(X);
        computer = new MinimaxPlayer(O);
    }

    public void newGame(TerminalView view) throws InvalidMoveException, IOException, InvalidCoordinateException {
        newGame(view, false);
    }

    public void newGame(TerminalView view, Boolean pause) throws InvalidMoveException, IOException, InvalidCoordinateException {
        currentBoard = new Board();
        passNewBoardToView(currentBoard, view, pause);
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    private void checkForWins() {
        if (currentBoard.hasWin()) {
            int winner = currentBoard.getWinningRow().winner();
            String messageTemplate = "Game over: Player ";
            String winnerDescription = (winner == X) ? "X wins." : "O wins.";
            currentBoard.print();
            System.out.print(messageTemplate + winnerDescription);
            System.exit(0);
        }
    }

    private void checkForEndState() {
        if (currentBoard.isFull() && !currentBoard.hasWin()) {
            currentBoard.print();
            System.out.print("Game over: It's a draw.");
            System.exit(0);
        }
    }

    public void processMove(BoardCoordinate move, TerminalView view) throws InvalidMoveException, IOException, InvalidCoordinateException {
        processMove(move, view, false);
    }

    public void processMove(BoardCoordinate move, TerminalView view, Boolean pause) throws InvalidMoveException, IOException, InvalidCoordinateException {
        try {
            currentBoard = human.move(move, currentBoard);
            checkForWins();
            checkForEndState();
        } catch (InvalidMoveException e) {
            checkForWins();
            checkForEndState();
            view.promptWithMessage(e.getMessage(), currentBoard, this, pause);
            return;
        }
        currentBoard = computer.bestMove(currentBoard);
        checkForWins();
        checkForEndState();
        if (pause) {
            return;
        }
        passNewBoardToView(currentBoard, view, pause);
    }

    public void passNewBoardToView(Board board, TerminalView view, Boolean pause) throws InvalidMoveException, IOException, InvalidCoordinateException {
        if (pause) {
            view.processBoard(board, this, pause);
        } else {
            view.processBoard(board, this);
        }
    }

}
