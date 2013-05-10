package com.cmendenhall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimaxPlayer extends Player {

    MinimaxPlayer(int playerNumber) {
        super(playerNumber);
    }

    private int scoreMove(Board board) {
        if (board.hasWin() || board.isFull()) {
            return scoreBoard(board);
        } else {
            GameTree node = new GameTree(board);
            return miniMaxScoreMove(node);
        }
    }

    private int miniMaxScoreMove(GameTree node) {
        if (node.isTerminal()) {
            return scoreBoard(node.gameState);
        } else {

            List<Integer> moveScores = new ArrayList<Integer>();

            int gamePiece = getGamePiece();
            if (node.gameState.nextTurn() == gamePiece) {
                for (GameTree child : node.children) {
                    moveScores.add(miniMaxScoreMove(child));
                }
                return Collections.max(moveScores);
            } else {
                for (GameTree child : node.children) {
                    moveScores.add(miniMaxScoreMove(child));
                }
                return Collections.min(moveScores);
            }
        }
    }

    public Board bestMove(Board board) {

        List<Board> nextMoves = board.getNextStates();

        int bestScore = Integer.MIN_VALUE + 1;
        Board bestMove = null;

        for (Board move : nextMoves) {

            int gamePiece = getGamePiece();
            if (move.winnerIs() == gamePiece) {
                return move;
            } else {
                int moveScore = scoreMove(move);

                if (moveScore >= bestScore) {
                    bestMove = move;
                    bestScore = moveScore;
                }
            }
        }
        return bestMove;
    }
}
