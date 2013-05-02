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

    public List<Board> getNextStates(Board board) {

        board.print();

        List<Board> newstates = new ArrayList<Board>();

        for (int i=0; i < board.top.squares.length; i++) {
            if (board.top.squares[i] == _) {
                Board newboard = board.fillSquare(new BoardCoordinate(0, i), number);
                newstates.add(newboard);
                newboard.print();
            }
        }

        for (int i=0; i < board.middle.squares.length; i++) {
            if (board.middle.squares[i] == _) {
                Board newboard = board.fillSquare(new BoardCoordinate(1, i), number);
                newstates.add(newboard);
                newboard.print();
            }
        }

        for (int i=0; i < board.bottom.squares.length; i++) {
            if (board.bottom.squares[i] == _) {
                Board newboard = board.fillSquare(new BoardCoordinate(2, i), number);
                newstates.add(newboard);
                newboard.print();
            }
        }
        return newstates;
    }
}
