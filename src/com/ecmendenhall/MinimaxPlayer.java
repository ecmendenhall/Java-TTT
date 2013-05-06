package com.ecmendenhall;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MinimaxPlayer extends Player {

    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;
    private int alpha;

    MinimaxPlayer(int playerNumber) throws InvalidPlayerException {
        super(playerNumber);
    }

    public int scoreMove(Board move) throws InvalidMoveException {
        if (move.hasWin() || move.isFull()) {
            return scoreBoard(move);
        } else {
            GameTree.Node node = new GameTree.Node(move);
            return miniMaxScoreMove(node);
        }
    }

    public int miniMaxScoreMove(GameTree.Node node) {
        if (node.isTerminal()) {
            return scoreBoard(node.gameState);
        } else {
            List<Integer> moveScores = new ArrayList<Integer>();
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
            if (player == gamePiece) {
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

        int bestscore = Integer.MIN_VALUE + 1;
        Board bestmove = null;

        for (Board move : nextMoves) {

            if (move.winnerIs() == gamePiece) {
                return move;
            } else {
                int moveScore = scoreMove(move);

                if (moveScore >= bestscore) {
                    bestmove = move;
                    bestscore = moveScore;
                }
            }
        }
        return bestmove;
    }
}
