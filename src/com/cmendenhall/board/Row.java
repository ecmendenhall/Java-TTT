package com.cmendenhall.board;

import static com.cmendenhall.TicTacToeSymbols.*;

public class Row {

    final private int[] squares;

    public Row(int... rowSquares) {
        squares = rowSquares;
    }

    public int numberOfSquares() {
        return squares.length;
    }

    public int[] getSquares() {
        return squares;
    }

    public int getSquare(int square) {
        return squares[square];
    }

    public boolean hasWin() {
        int firstSquare = squares[0];

        for (int square : squares) {
            if (square == _) return false;
            if (square != firstSquare) return false;
        }

        return true;
    }

    public int winner() {
        if (hasWin() && squares[0] == X) {
            return X;
        } else if (hasWin() && squares[0] == O) {
            return O;
        } else
            return _;
    }

    public Row fillSquare(int column, int player) {
        int[] newSquares = squares.clone();
        newSquares[column] = player;
        return new Row(newSquares);
    }

    public boolean isFull() {
        for (int square : squares) {
            if (square == _) return false;
        }
        return true;
    }

    public boolean hasBlock() {
        Integer firstFullSquare = null;
        for (int square : squares) {
            if (square != _) {
                firstFullSquare = square;
            }
        }

        if (firstFullSquare == null) return false;

        for (int square : squares) {
            if (square != firstFullSquare && square != _) {
                return true;
            }
        }
        return false;
    }

    public boolean squareIsFull(int square) {
        return squares[square] != _;
    }

    public int sum() {
        int rowSum = 0;
        for (int square : squares) {
            rowSum += square;
        }
        return rowSum;
    }

    public String intToSymbol(int square) {
        switch (square) {
            case _:
                return " ";
            case X:
                return "X";
            case O:
                return "O";
        }
        return "";
    }

    public String toString() {
        String rowString = "";
        int nSquares = squares.length;

        for (int i = 0; i < nSquares - 1; i++) {
            String symbol = intToSymbol(squares[i]);
            rowString += " " + symbol + " |";
        }

        String lastSquare = intToSymbol(squares[nSquares - 1]);
        return rowString + " " + lastSquare + "\n";
    }

    public Boolean equals(Row otherRow) {
        for (int i = 0; i < squares.length; i++) {
            if (squares[i] != otherRow.getSquare(i)) return false;
        }
        return true;
    }
}
