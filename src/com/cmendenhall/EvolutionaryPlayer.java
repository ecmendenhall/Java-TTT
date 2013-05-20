package com.cmendenhall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;

public class EvolutionaryPlayer extends GamePlayer {
    private HashMap<Board, Board> genome;
    private List<Board> allStates;

    EvolutionaryPlayer(int playerNumber) {
        super(playerNumber);
        genome = new HashMap<Board, Board>();
        allStates = new ArrayList<Board>();
    }

    public void aggregateAllBoardStates(GameTree startState) {
        allStates.add(startState.gameState);
        for (GameTree child : startState.children) {
            if (!child.isTerminal()) {
                allStates.add(child.gameState);
                aggregateAllBoardStates(child);
            }
        }
    }

    public List<Board> getAllBoardStates() {
        GameBoard newBoard = new GameBoard();
        GameTree newTree = new GameTree(newBoard);
        aggregateAllBoardStates(newTree);
        return allStates;

    }

    public HashMap<Board, Board> getGenome() {
        return genome;
    }




}
