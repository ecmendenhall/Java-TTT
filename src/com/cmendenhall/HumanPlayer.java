package com.cmendenhall;

public class HumanPlayer extends Player {

    public HumanPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
