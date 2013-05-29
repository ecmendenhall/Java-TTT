package com.cmendenhall.players;

public class HumanPlayer extends GamePlayer {

    public HumanPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

}
