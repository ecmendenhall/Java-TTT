package com.cmendenhall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTree {
    public Board root;
    public List<Node> children;

    public GameTree(Board rootBoard) throws InvalidMoveException {
        root = rootBoard;
        children = new ArrayList<Node>();
        if (rootBoard.getNextStates().isEmpty()) {
            return;
        } else {
            for (Board nextNode : rootBoard.getNextStates()) {
                children.add(new Node(nextNode));
            }
        }
    }

    public boolean isTerminal() {
        return (root.isFull() || root.hasWin());
    }

    public static class Node {
        public Board gameState;
        public List<Node> children;

        public Node(Board state) throws InvalidMoveException {
            gameState = state;
            children = new ArrayList<Node>();
            if (state.getNextStates().isEmpty()) {
                return;
            } else {
                for (Board nextNode : state.getNextStates()) {
                    children.add(new Node(nextNode));
                }
            }
        }

        public List<Node> getLeaves() {
            List<Node> leaves = new ArrayList<Node>();

            for (Node child : children) {
                if (child.children.isEmpty()) {
                    leaves.add(child);
                } else {
                    leaves.addAll(child.getLeaves());
                }
            }
            return leaves;
        }

        public int maxLeafScore(MinimaxPlayer player) {
            List<Node> leaves = getLeaves();
            List<Integer> scores = new ArrayList<Integer>();
            for (Node leaf : leaves) {
                scores.add(player.scoreBoard(leaf.gameState));
            }
            return Collections.max(scores);
        }

        public int minLeafScore(MinimaxPlayer player) {
            List<Node> leaves = getLeaves();
            List<Integer> scores = new ArrayList<Integer>();
            for (Node leaf : leaves) {
                scores.add(player.scoreBoard(leaf.gameState));
            }
            return Collections.min(scores);
        }

        public boolean isTerminal() {
            return (gameState.isFull() || gameState.hasWin());
        }
    }

    public List<Node> getLeaves() {
        List<Node> leaves = new ArrayList<Node>();

        for (Node child : children) {
                leaves.addAll(child.getLeaves());
        }
        return leaves;
    }
}
