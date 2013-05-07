package com.cmendenhall;

public class Row extends GameElement {

    protected int[] squares;

    public Row() {
        squares = new int[] { _, _, _ };
    }

    public Row(int left, int middle, int right) {
        squares = new int[] { left, middle, right };
    }

    public int[] getSquares() {
        return squares;
    }

    public int getSquare(Integer square) {
        return squares[square];
    }

    public boolean hasWin() {
        for (int square : squares) {
            if (square == _) return false;
        }
        return (squares[0] == squares[1]) && (squares[0] == squares[2]);
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
        switch (column) {
            case 0:
                return new Row(player, squares[1], squares[2]);
            case 1:
                return new Row(squares[0], player, squares[2]);
            case 2:
                return new Row(squares[0], squares[1], player);
        }
        return this;
    }

    public boolean isFull() {
        return (squares[0] != 0 && squares[1] != 0 && squares[2] != 0);
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

    protected String intToSymbol(int square) {
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

        // Insert a Unicode vertical box-drawing character
        // after every square but the last.

        for (int i=0; i < nSquares - 1; i++) {
            String symbol = intToSymbol(squares[i]);
            rowString += " " + symbol + " \u2502";
        }

        String lastSquare = intToSymbol(squares[nSquares - 1]);
        return rowString + " " + lastSquare + "\n";
    }

    public Boolean equals(Row otherRow) {
        for (int i=0; i<squares.length; i++) {
            if (squares[i] != otherRow.getSquare(i)) return false;
        }
        return true;
    }
}
