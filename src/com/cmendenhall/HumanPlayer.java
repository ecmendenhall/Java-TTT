package com.cmendenhall;

public class HumanPlayer extends GamePlayer {

    HumanPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

}
