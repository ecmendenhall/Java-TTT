package com.ecmendenhall;

import java.io.IOException;

public class GameController {
    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;

    private Board currentboard;
    private Player human;
    private MinimaxPlayer computer;

    public GameController() throws InvalidPlayerException {
        currentboard = new Board();
        human = new Player(X);
        computer = new MinimaxPlayer(O);
    }

    public void newGame(TerminalView view) throws InvalidMoveException, IOException {
        newGame(view, false);
    }

    public void newGame(TerminalView view, Boolean pause) throws InvalidMoveException, IOException {
        currentboard = new Board();
        passNewBoardToView(currentboard, view, pause);
    }

    public Board getCurrentBoard() {
        return currentboard;
    }

    public void processMove(BoardCoordinate move, TerminalView view) throws InvalidMoveException, IOException {
        processMove(move, view, false);
    }

    public void processMove(BoardCoordinate move, TerminalView view, Boolean pause) throws InvalidMoveException, IOException {
        try {
            currentboard = human.move(move, currentboard);
        } catch (InvalidMoveException e) {
            view.promptWithMessage(e.getMessage(), currentboard, this, pause);
            return;
        }
        currentboard = computer.bestMove(currentboard);
        if (pause) {
            return;
        }
        passNewBoardToView(currentboard, view, pause);
    }

    public void passNewBoardToView(Board board, TerminalView view, Boolean pause) throws InvalidMoveException, IOException {
        if (pause) {
            view.processBoard(board, this, pause);
        } else {
            view.processBoard(board, this);
        }
    }
}
