package com.cmendenhall;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MinimaxPlayer extends Player {
    private int alpha;

    MinimaxPlayer(int playerNumber) throws InvalidPlayerException {
        super(playerNumber);
    }

    public int scoreMove(Board board) throws InvalidMoveException {
        if (board.hasWin() || board.isFull()) {
            return scoreBoard(board);
        } else {
            GameTree.Node node = new GameTree.Node(board);
            return miniMaxScoreMove(node);
        }
    }

    public int miniMaxScoreMove(GameTree.Node node) {
        if (node.isTerminal()) {
            return scoreBoard(node.gameState);
        } else {

            List<Integer> moveScores = new ArrayList<Integer>();

            int gamePiece = getGamePiece();
            if (node.gameState.nextTurn() == gamePiece) {
                for (GameTree.Node child : node.children) {
                    moveScores.add(miniMaxScoreMove(child));
                }
                return Collections.max(moveScores);
            } else {
                for (GameTree.Node child : node.children) {
                    moveScores.add(miniMaxScoreMove(child));
                }
                return Collections.min(moveScores);
            }
        }
    }

    public int alphaBetaScoreMove(GameTree.Node node,
                                  int alpha,
                                  int beta,
                                  int player) {
        if (node.isTerminal()) {
            return scoreBoard(node.gameState);
        } else {
            if (player == getGamePiece()) {
                for (GameTree.Node child : node.children) {
                    alpha = Math.max(alpha,
                                     alphaBetaScoreMove(child,
                                                        alpha,
                                                        beta,
                                                        otherPlayer()));
                    if (alpha >= beta) {
                        break;
                    }
                }
                return alpha;
            } else {
                for (GameTree.Node child : node.children) {
                    beta = Math.min(beta,
                                    alphaBetaScoreMove(child,
                                                       alpha,
                                                       beta,
                                                       otherPlayer()));
                    if (alpha >= beta) {
                        break;
                    }
                }
                return beta;
            }
        }
    }

    public Board bestMove(Board board) throws InvalidMoveException {

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
