package com.cmendenhall;

import java.util.ArrayList;
import java.util.List;

public class GameTree {
    public Board gameState;
    public List<GameTree> children;

    public GameTree(Board state) {
        gameState = state;
        children = new ArrayList<GameTree>();
        if (state.getNextStates().isEmpty()) {
            return;
        } else {
            for (Board nextGameTree : state.getNextStates()) {
                children.add(new GameTree(nextGameTree));
            }
        }
    }

    public List<GameTree> getLeaves() {
        List<GameTree> leaves = new ArrayList<GameTree>();

        for (GameTree child : children) {
            if (child.children.isEmpty()) {
                leaves.add(child);
            } else {
                leaves.addAll(child.getLeaves());
            }
        }
        return leaves;
    }

    public boolean isTerminal() {
        return (gameState.isFull() || gameState.hasWin());
    }
}

