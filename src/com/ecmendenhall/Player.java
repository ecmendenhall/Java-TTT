package com.ecmendenhall;

public class Player {
    public char symbol;
    public final int number;

    Player(int playernumber) {
        number = playernumber;
        if (number == 1) {
            symbol = 'X';
        } else {
            symbol = 'O';
        }

    }

    public void move(BoardCoordinate square, Board board) {
        board.fillSquare(square, number);
    }
}
