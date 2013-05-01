package com.ecmendenhall;

public class MinimaxPlayer extends Player {

    MinimaxPlayer(int playernumber) {
        super(playernumber);
    }

    public Board[] getNextStates() {
        return new Board[5];
    }
}
