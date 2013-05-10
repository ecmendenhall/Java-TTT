package com.cmendenhall;

public class MockGameController extends GameController {

    public void setBoard(Board newBoard) {
        currentBoard = newBoard;
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
    }
}
