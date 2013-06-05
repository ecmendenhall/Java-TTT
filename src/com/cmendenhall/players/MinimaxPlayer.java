package com.cmendenhall.players;

import com.cmendenhall.board.Board;
import com.cmendenhall.board.BoardAnalyzer;
import com.cmendenhall.board.BoardCoordinate;
import com.cmendenhall.board.UniversalBoardCoordinate;
import com.cmendenhall.exceptions.InvalidMoveException;

import java.util.*;

import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols.X;

public class MinimaxPlayer extends GamePlayer {
    private HashMap<Board, Integer> scoreCache = new HashMap<Board, Integer>();

    public MinimaxPlayer(int playerNumber) {
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
        } else if (scoreCache.containsKey(board)) {
            return scoreCache.get(board);
        } else {
            int score = scoreMoveWithPruning(board);
            scoreCache.put(board, score);
            return score;
        }
    }

    public int calculateDepth(Board board) {
        int size = board.getSize();
        int lookAheadDepth = 12 - 4 * (size - 2);
        return Math.max(2, lookAheadDepth);
    }

    public boolean isTerminal(Board board) {
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

    public int randomSquare(Board board) {
        int size = board.getSize();
        Random generator = new Random();
        return generator.nextInt(size);
    }

    public Board randomMove(Board board) {

        int row = randomSquare(board);
        int column = randomSquare(board);

        BoardCoordinate move = new UniversalBoardCoordinate(row, column);

        try {
            return board.fillSquare(move, getGamePiece());
        } catch (InvalidMoveException e) {
            return randomMove(board);
        }
    }

    public int randomMoveLimit(Board board) {
        return board.getSize() + (2 - getGamePiece());
    }

    private boolean randomMoveOkay(Board board) {
        return (BoardAnalyzer.turnsPlayed(board) < randomMoveLimit(board) &&
                board.getSize() > 3);
    }

    public Board bestMove(Board board) {

        if ( randomMoveOkay(board) ) {
            return randomMove(board);
        }

        List<Board> nextMoves = BoardAnalyzer.getNextStates(board);
        HashMap<Board, Integer> unsortedMoveScores = new HashMap<Board, Integer>();
        ScoreComparator scoreComparator = new ScoreComparator(unsortedMoveScores);
        TreeMap<Board, Integer> moveScores = new TreeMap<Board, Integer>(scoreComparator);

        for (Board move : nextMoves) {

            int gamePiece = getGamePiece();
            if (BoardAnalyzer.winnerIs(move) == gamePiece) {
                return move;
            } else {
                int moveScore = scoreMove(move);
                unsortedMoveScores.put(move, moveScore);
            }
        }
        moveScores.putAll(unsortedMoveScores);
        Board bestMove = moveScores.firstKey();
        return bestMove;
    }

    class ScoreComparator implements Comparator<Board> {
        private Map<Board, Integer> map;

        public ScoreComparator(Map<Board, Integer> unsortedMap) {
            map = unsortedMap;
        }

        public int compare(Board a, Board b) {
            if (map.get(a) >= map.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
