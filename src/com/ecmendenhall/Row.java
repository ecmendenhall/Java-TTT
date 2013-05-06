package com.ecmendenhall;

public class Row {

    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;
    public int[] squares;

    public Row() {
        squares = new int[] { _, _, _ };
    }

    public Row(int left, int middle, int right) {
        squares = new int[] { left, middle, right };
    }

    public boolean hasWin() {
        for (int square : squares) {
            if (square == _) return false;
        }
        return (squares[0] == squares[1]) &&  (squares[0] == squares[2]);
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
        Row newrow = new Row(squares[0], squares[1], squares[2]);
        newrow.squares[column] = player;
        return newrow;
    }

    public boolean isFull() {
        return (squares[0] != 0 && squares[1] != 0 && squares[2] != 0);
    }

    public int sum() {
        int rowsum = 0;
        for (int square : squares) {
            rowsum += square;
        }
        return rowsum;
    }

    public String intToSymbol(int square) {
        switch (square) {
            case 0:
                return " ";
            case 1:
                return "X";
            case 2:
                return "O";
        }
        return "";
    }

    public String toString() {
        String rowstring = "";
        int nsquares = squares.length;
        for (int i=0; i < nsquares - 1; i++) {
            String symbol = intToSymbol(squares[i]);
            rowstring += " " + symbol + " \u2502";
        }
        String lastsquare = intToSymbol(squares[nsquares - 1]);
        return rowstring + " " + lastsquare + "\n";
    }
}
