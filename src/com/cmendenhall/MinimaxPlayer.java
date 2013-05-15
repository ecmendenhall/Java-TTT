package com.cmendenhall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;

public class MinimaxPlayer extends GamePlayer {

    MinimaxPlayer(int playerNumber) {
        super(playerNumber);
    }

    private int otherPlayer() {
        return (getGamePiece() == X)? O : X;
    }

    public int scoreBoard(Board board) {
        if (board.winnerIs() == getGamePiece()) {
            return 1;
        } else if (board.winnerIs() == otherPlayer()) {
            return -1;
        } else {
            return 0;
        }
    }

    private int scoreMove(Board board) {
        if (board.hasWin() || board.isFull()) {
            return scoreBoard(board);
        } else {
            GameTree node = new GameTree(board);
            return scoreMoveWithPruning(node);
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

    private int scoreMoveWithPruning(GameTree node) {
        return alphaBetaSearch(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private int alphaBetaSearch(GameTree node, Integer alpha, Integer beta) {
        if (node.isTerminal()) {
            return scoreBoard(node.gameState);
        } else {

            int gamePiece = getGamePiece();
            if (node.gameState.nextTurn() == gamePiece) {
                for (GameTree child : node.children) {
                    alpha = Math.max(alpha, alphaBetaSearch(child, alpha, beta));
                    if (beta <= alpha) break;
                }
                return alpha;
            } else {
                for (GameTree child : node.children) {
                    beta = Math.min(beta, alphaBetaSearch(child, alpha, beta));
                    if (beta <= alpha) break;
                }
                return beta;
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
