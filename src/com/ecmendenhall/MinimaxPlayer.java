package com.ecmendenhall;

import java.util.List;
import java.util.ArrayList;

public class MinimaxPlayer extends Player {

    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;

    MinimaxPlayer(int playernumber) {
        super(playernumber);
    }

    public List< Pair<Integer, BoardCoordinate> > scoreNextMoves(Board board) {
        List<Pair<Board,
                  BoardCoordinate>> possiblemoves = board.getNextStates();

        List<Pair< Integer,
                   BoardCoordinate>> movescores = new ArrayList<Pair<Integer,
                                                                     BoardCoordinate>>();

        for (Pair<Board,
                  BoardCoordinate> move : possiblemoves) {
            movescores.add(new Pair<Integer,
                                    BoardCoordinate>(scoreMove(move.first),
                                                     move.rest));
        }
        return movescores;
    }

    public BoardCoordinate bestMove(Board board) {
        return new BoardCoordinate(1, 2);
    }

    public Integer scoreMove(Board board) {
        if ( (board.hasWin() != null) || board.isFull()) {
            return scoreBoard(board);
        } else {
            for (Pair<Board, BoardCoordinate> state : board.getNextStates()) {
                return scoreMove(state.first);
            }
        }
        return null;
    }

    public int scoreBoard(Board board) {
        if (board.winnerIs() == number) {
            return 1;
        } else if (board.winnerIs() == _) {
            return 0;
        } else {
            return -1;
        }
    }
}
