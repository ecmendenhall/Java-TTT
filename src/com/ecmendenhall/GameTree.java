package com.ecmendenhall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTree {
    public Board root;
    public List<Node> children;

    public GameTree(Board rootboard) throws InvalidMoveException {
        root = rootboard;
        children = new ArrayList<Node>();
        if (rootboard.getNextStates().isEmpty()) {
            return;
        } else {
            for (Board nextnode : rootboard.getNextStates()) {
                children.add(new Node(nextnode));
            }
        }
    }

    public boolean isTerminal() {
        return (root.isFull() || root.hasWin());
    }

    public static class Node {
        public Board gamestate;
        public List<Node> children;

        public Node(Board state) throws InvalidMoveException {
            gamestate = state;
            children = new ArrayList<Node>();
            if (state.getNextStates().isEmpty()) {
                return;
            } else {
                for (Board nextnode : state.getNextStates()) {
                    children.add(new Node(nextnode));
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
                scores.add(player.scoreBoard(leaf.gamestate));
            }
            return Collections.max(scores);
        }

        public int minLeafScore(MinimaxPlayer player) {
            List<Node> leaves = getLeaves();
            List<Integer> scores = new ArrayList<Integer>();
            for (Node leaf : leaves) {
                scores.add(player.scoreBoard(leaf.gamestate));
            }
            return Collections.min(scores);
        }

        public boolean isTerminal() {
            return (gamestate.isFull() || gamestate.hasWin());
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
