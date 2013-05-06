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

    public void newGame(TerminalView view) throws InvalidMoveException, IOException {
        newGame(view, false);
    }

    public void newGame(TerminalView view, Boolean pause) throws InvalidMoveException, IOException {
        currentBoard = new Board();
        passNewBoardToView(currentBoard, view, pause);
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void processMove(BoardCoordinate move, TerminalView view) throws InvalidMoveException, IOException {
        processMove(move, view, false);
    }

    public void processMove(BoardCoordinate move, TerminalView view, Boolean pause) throws InvalidMoveException, IOException {
        try {
            currentBoard = human.move(move, currentBoard);
        } catch (InvalidMoveException e) {
            view.promptWithMessage(e.getMessage(), currentBoard, this, pause);
            return;
        }
        currentBoard = computer.bestMove(currentBoard);
        if (pause) {
            return;
        }
        passNewBoardToView(currentBoard, view, pause);
    }

    public void passNewBoardToView(Board board, TerminalView view, Boolean pause) throws InvalidMoveException, IOException {
        if (pause) {
            view.processBoard(board, this, pause);
        } else {
            view.processBoard(board, this);
        }
    }
}
