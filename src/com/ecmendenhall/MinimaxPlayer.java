package com.ecmendenhall;

import java.util.List;
import java.util.ArrayList;

public class MinimaxPlayer extends Player {

    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;
    private int alpha;

    MinimaxPlayer(int playernumber) throws InvalidPlayerException {
        super(playernumber);
    }

    public int scoreMove(Board move) throws InvalidMoveException {
        if (move.hasWin() || move.isFull()) {
            return scoreBoard(move);
        } else {
            GameTree.Node node = new GameTree.Node(move);
            return miniMaxScoreMove(node);
            //return alphaBetaScoreMove(node, Integer.MIN_VALUE + 1, Integer.MAX_VALUE -1, node.gamestate.nextTurn());
        }
    }

    public int miniMaxScoreMove(GameTree.Node node) {
        if (node.isTerminal()) {
            return scoreBoard(node.gamestate);
        } else {
            List<Integer> movescores = new ArrayList<Integer>();
            if (node.gamestate.nextTurn() == gamepiece) {
                for (GameTree.Node child : node.children) {
                    movescores.add(miniMaxScoreMove(child));
                }
                return Collections.max(movescores);
            } else {
                for (GameTree.Node child : node.children) {
                    movescores.add(miniMaxScoreMove(child));
                }
                return Collections.min(movescores);
            }
        }
    }

    public int alphaBetaScoreMove(GameTree.Node node,
                                  int alpha,
                                  int beta,
                                  int player) {
        if (node.isTerminal()) {
            return scoreBoard(node.gamestate);
        } else {
            if (player == gamepiece) {
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

        List<Board> nextmoves = board.getNextStates();

        int bestscore = Integer.MIN_VALUE + 1;
        Board bestmove = null;

        for (Board move : nextmoves) {

            if (move.winnerIs() == gamepiece) {
                return move;
            } else {
                int movescore = scoreMove(move);

                if (movescore >= bestscore) {
                    bestmove = move;
                    bestscore = movescore;
                }
            }
        }
        return bestmove;
    }
}
