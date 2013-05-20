package com.cmendenhall;

import java.util.ArrayList;
import java.util.List;

import static com.cmendenhall.TicTacToeSymbols._;

public class GameTree {
    public Board gameState;
    public List<GameTree> children;

    public GameTree(Board state) {
        gameState = state;
        children = new ArrayList<GameTree>();
        if (BoardAnalyzer.getNextStates(state).isEmpty()) {
            return;
        } else {
            for (Board nextGameTree : BoardAnalyzer.getNextStates(state)) {
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
        return (BoardAnalyzer.isFull(gameState) || BoardAnalyzer.hasWin(gameState));
    }
}

