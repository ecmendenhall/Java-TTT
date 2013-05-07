package com.cmendenhall;

public class Player extends GameElement {
    private final char symbol;
    private final int gamePiece;

    Player(int playerNumber) throws InvalidPlayerException {
        if (playerNumber != 2 && playerNumber != 1) {
            throw new InvalidPlayerException("Invalid player number.");
        }
        gamePiece = playerNumber;
        symbol = (gamePiece == X)? 'X' : 'O';
    }

    public int otherPlayer() {
        return (gamePiece == X)? O : X;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getGamePiece() {
        return gamePiece;
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
