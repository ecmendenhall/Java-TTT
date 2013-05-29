package com.cmendenhall.board;

import com.cmendenhall.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import static com.cmendenhall.TicTacToeSymbols.*;

public class BoardAnalyzer {

    public static boolean hasWin(Board board) {
        return (getWinningRow(board).getSquare(0) != -1) ? true : false;
    }

    public static int turnsPlayed(Board board) {
        return (int) Math.ceil(sum(board) / 1.5);
    }

    private static Row getWinningRow(Board board) {
        List<Row> rows = board.getRows();
        List<Row> cols = board.getColumns();
        List<Row> diags = board.getDiagonals();

        for (Row row : rows) {
            if (row.hasWin()) return row;
        }

        for (Row col : cols) {
            if (col.hasWin()) return col;
        }

        for (Row diag : diags) {
            if (diag.hasWin()) return diag;
        }

        int[] error = new int[]{-1};
        return new Row(error);
    }

    public static boolean isFull(Board board) {
        for (Row row : board.getRows()) {
            if (!row.isFull()) {
                return false;
            }
        }
        return true;
    }

    public static int winnerIs(Board board) {
        if (hasWin(board)) {
            return getWinningRow(board).winner();
        }
        return _;
    }

    public static int nextTurn(Board board) {
        if (isFull(board)) return _;
        if (sum(board) % 3 == 0) {
            return X;
        } else {
            return O;
        }
    }

    public static int sum(Board board) {
        int sum = 0;
        for (Row row : board.getRows()) {
            sum += row.sum();
        }
        return sum;
    }

    public static boolean isEmpty(Board board) {
        return sum(board) == 0;
    }

    private static Board nextState(Board board, int row, int column) {
        BoardCoordinate moveCoordinate = new UniversalBoardCoordinate(row, column);
        try {
            Board newBoard = board.fillSquare(moveCoordinate, BoardAnalyzer.nextTurn(board));
            return newBoard;
        } catch (InvalidMoveException e) {
            return board;
        }
    }

    public static List<Board> getNextStates(Board board) {

        List<Board> newStates = new ArrayList<Board>();

        if (BoardAnalyzer.isFull(board) || BoardAnalyzer.hasWin(board)) {
            return newStates;
        }

        int rowLength = board.getRows().size();
        int columnLength = board.getRows().get(0).numberOfSquares();

        for (int row = 0; row < rowLength; row++) {
            Row thisRow = board.getRows().get(row);

            for (int column = 0; column < columnLength; column++) {
                if (thisRow.getSquare(column) == _) newStates.add(nextState(board, row, column));
            }

        }

        return newStates;
    }
}
