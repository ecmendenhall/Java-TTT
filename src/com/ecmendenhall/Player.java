package com.ecmendenhall;

public class Player {
    public char symbol;
    public final int gamepiece;
    public final int X = 1;
    public final int O = 2;
    public final int _ = 0;

    Player(int playernumber) throws InvalidPlayerException {
        if (playernumber != 2 && playernumber != 1) {
            throw new InvalidPlayerException("Invalid player number.");
        }
        gamepiece = playernumber;
        if (gamepiece == 1) {
            symbol = 'X';
        } else {
            symbol = 'O';
        }

    }

    public int otherPlayer() {
        if (gamepiece == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    public Board move(BoardCoordinate square, Board board) throws InvalidMoveException {
        return board.fillSquare(square, gamepiece);
    }

    public int scoreBoard(Board board) {
        if (board.winnerIs() == gamepiece) {
            return 1;
        } else if (board.winnerIs() == otherPlayer()) {
            return -1;
        } else {
            return 0;
        }
    }
}
