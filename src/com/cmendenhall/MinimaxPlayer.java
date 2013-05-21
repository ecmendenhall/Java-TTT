package com.cmendenhall;

import java.util.*;

import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols._;

public class MinimaxPlayer extends GamePlayer {
    private GameTree tree;
    private HashMap<Board, Integer> scoreCache = new HashMap<Board, Integer>();

    MinimaxPlayer(int playerNumber) {
        super(playerNumber);
    }

    private int otherPlayer() {
        return (getGamePiece() == X) ? O : X;
    }

    public int scoreBoard(Board board) {
        if (BoardAnalyzer.winnerIs(board) == getGamePiece()) {
            return 1;
        } else if (BoardAnalyzer.winnerIs(board) == otherPlayer()) {
            return -1;
        } else {
            return 0;
        }
    }

    private int scoreMove(Board board) {
        if (BoardAnalyzer.hasWin(board) || BoardAnalyzer.isFull(board)) {
            return scoreBoard(board);
        } else if (scoreCache.containsKey(board)){
            System.out.println("Cache hit!");
            return scoreCache.get(board);
        } else {
            int score = scoreMoveWithPruning(board);
            scoreCache.put(board, score);
            return score;
        }
    }

    private int miniMaxScoreMove(GameTree node) {
        if (node.isTerminal()) {
            return scoreBoard(node.gameState);
        } else {

            List<Integer> moveScores = new ArrayList<Integer>();

            int gamePiece = getGamePiece();
            if (BoardAnalyzer.nextTurn(node.gameState) == gamePiece) {
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

    private int calculateDepth(Board board) {
        int size = board.getSize();
        return Math.max(2, 16 - 4 * (size - 2));
    }

    private boolean isTerminal(Board board) {
        return BoardAnalyzer.isFull(board) || BoardAnalyzer.hasWin(board);
    }

    private int scoreMoveWithPruning(Board board) {
        int depth = calculateDepth(board);
        return alphaBetaSearch(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private int alphaBetaSearch(Board board, Integer depth, Integer alpha, Integer beta) {
        if (isTerminal(board) || depth == 0) {
            return scoreBoard(board);
        } else {

            int gamePiece = getGamePiece();
            List<Board> children = BoardAnalyzer.getNextStates(board);

            if (BoardAnalyzer.nextTurn(board) == gamePiece) {
                for (Board child : children) {
                    alpha = Math.max(alpha, alphaBetaSearch(child, depth - 1, alpha, beta));
                    if (beta <= alpha) break;
                }
                return alpha;
            } else {
                for (Board child : children) {
                    beta = Math.min(beta, alphaBetaSearch(child, depth - 1, alpha, beta));
                    if (beta <= alpha) break;
                }
                return beta;
            }
        }
    }

    private Board firstMove(Board board) {
        int size = board.getSize();
        Random generator = new Random();

        int row = generator.nextInt(size);
        int column = generator.nextInt(size);

        BoardCoordinate move = new UniversalBoardCoordinate(row, column);

        try {
            return board.fillSquare(move, getGamePiece());
        } catch (InvalidMoveException e) {
            return firstMove(board);
        }
    }

    private int randomMoveLimit(Board board) {
        return 2 * board.getSize() - (getGamePiece() + 2);
    }

    public Board bestMove(Board board) {

        if (BoardAnalyzer.turnsPlayed(board) <= randomMoveLimit(board) && board.getSize() > 3) {
            return firstMove(board);
        }

        List<Board> nextMoves = BoardAnalyzer.getNextStates(board);

        int bestScore = Integer.MIN_VALUE + 1;
        Board bestMove = null;

        for (Board move : nextMoves) {

            int gamePiece = getGamePiece();
            if (BoardAnalyzer.winnerIs(move) == gamePiece) {
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
