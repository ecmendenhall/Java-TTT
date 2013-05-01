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

    public void fillSquare(int column, int player) {
        squares[column] = player;
    }

    public boolean isFull() {
        return (squares[0] != 0 && squares[1] != 0 && squares[2] != 0);
    }
}
