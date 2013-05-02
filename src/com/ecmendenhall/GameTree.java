package com.ecmendenhall;

import java.util.ArrayList;
import java.util.List;

public class GameTree {
    public Board root;
    public List<Node> children;

    public GameTree(Board rootstate) {
        root = rootstate;
        children = new ArrayList<Node>();
        if (rootstate.getNextStates().isEmpty()) {
            return;
        } else {
            for (Pair<Board, BoardCoordinate> nextnode : rootstate.getNextStates()) {
                children.add(new Node(nextnode));
            }
        }
    }

    public static class Node {
        public Pair<Board, BoardCoordinate> gamestate;
        public List<Node> children;

        public Node(Pair<Board, BoardCoordinate> state) {
            gamestate = state;
            children = new ArrayList<Node>();
            if (state.first.getNextStates().isEmpty()) {
                return;
            } else {
                for (Pair<Board, BoardCoordinate> nextnode : state.first.getNextStates()) {
                    children.add(new Node(nextnode));
                }
            }
        }
    }
}
