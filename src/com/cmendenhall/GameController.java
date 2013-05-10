package com.cmendenhall;

import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;

public class GameController {
    protected Board currentBoard;
    protected Player human;
    protected MinimaxPlayer computer;

    public GameController() {
        currentBoard = new Board();
        human = new Player(X);
        computer = new MinimaxPlayer(O);
    }

    public void newGame(TerminalView view ) {
        currentBoard = new Board();
        view.newGame(currentBoard, this);
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void checkForWins(TerminalView view) {
        if (currentBoard.hasWin()) {
            view.winEndGame(currentBoard, this);
        }
    }

    public void checkForDraw(TerminalView view) {
        if (currentBoard.isFull() && !currentBoard.hasWin()) {
            view.drawEndGame(currentBoard, this);
        }
    }

    public void checkForEndState(TerminalView view) {
        checkForWins(view);
        checkForDraw(view);
    }

    public void processMove(BoardCoordinate move, TerminalView view) {
        try {
            currentBoard = human.move(move, currentBoard);
            checkForEndState(view);
        } catch (InvalidMoveException e) {
            checkForEndState(view);
            view.promptWithMessage(e.getMessage(), currentBoard, this);
            return;
        }
        currentBoard = computer.bestMove(currentBoard);
        checkForEndState(view);
        passNewBoardToView(currentBoard, view);
    }

    public void passNewBoardToView(Board board, TerminalView view) {
        view.getNextMove(board, this);
    }

}
