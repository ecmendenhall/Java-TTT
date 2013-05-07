package com.cmendenhall;

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

    public void newGame(TerminalView view) throws Exception {
        newGame(view, false);
    }

    public void newGame(TerminalView view, Boolean pause) throws Exception {
        currentBoard = new Board();
        view.newGame(currentBoard, this, pause);
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    private void checkForWins(TerminalView view) throws Exception {
        if (currentBoard.hasWin()) {
            view.winEndGame(currentBoard, this);
        }
    }

    private void checkForEndState(TerminalView view) throws Exception {
        if (currentBoard.isFull() && !currentBoard.hasWin()) {
            view.drawEndGame(currentBoard, this);
        }
    }

    public void processMove(BoardCoordinate move, TerminalView view) throws Exception {
        processMove(move, view, false);
    }

    public void processMove(BoardCoordinate move, TerminalView view, Boolean pause) throws Exception {
        try {
            currentBoard = human.move(move, currentBoard);
            checkForWins(view);
            checkForEndState(view);
        } catch (InvalidMoveException e) {
            checkForWins(view);
            checkForEndState(view);
            view.promptWithMessage(e.getMessage(), currentBoard, this, pause);
            return;
        }
        currentBoard = computer.bestMove(currentBoard);
        checkForWins(view);
        checkForEndState(view);
        if (pause) {
            return;
        }
        passNewBoardToView(currentBoard, view, pause);
    }

    public void passNewBoardToView(Board board, TerminalView view, Boolean pause) throws Exception {
        if (pause) {
            view.processBoard(board, this, pause);
        } else {
            view.processBoard(board, this);
        }
    }

}
