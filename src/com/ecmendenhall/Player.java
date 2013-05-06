package com.ecmendenhall;

public class Player {
    public char symbol;
    public final int gamePiece;
    public final int X = 1;
    public final int O = 2;
    public final int _ = 0;

    Player(int playerNumber) throws InvalidPlayerException {
        if (playerNumber != 2 && playerNumber != 1) {
            throw new InvalidPlayerException("Invalid player number.");
        }
        gamePiece = playerNumber;
        if (gamePiece == 1) {
            symbol = 'X';
        } else {
            symbol = 'O';
        }

    }

    public int otherPlayer() {
        if (gamePiece == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    public Board move(BoardCoordinate square, Board board) throws InvalidMoveException {
        return board.fillSquare(square, gamePiece);
    }

    public int scoreBoard(Board board) {
        if (board.winnerIs() == gamePiece) {
            return 1;
        } else if (board.winnerIs() == otherPlayer()) {
            return -1;
        } else {
            return 0;
        }
    }
}
