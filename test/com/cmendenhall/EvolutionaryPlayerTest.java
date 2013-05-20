package com.cmendenhall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class EvolutionaryPlayerTest extends TicTacToeTest {
    private EvolutionaryPlayer evolutionaryPlayer = new EvolutionaryPlayer(X);

    @Before
    public void setUp() {

    }

    @Test
    public void evolutionaryPlayerExists() {
        Player evolutionaryPlayer = new EvolutionaryPlayer(X);
    }

    @Test
    public void evolutionaryPlayerShouldHaveGenome() {
        HashMap<Board, Board> genome = evolutionaryPlayer.getGenome();
    }

    @Test
    public void genomeShouldContainAllBoardStates() {
        HashMap<Board, Board> genome = evolutionaryPlayer.getGenome();
        assertEquals(827, genome.size());
    }

    @Test
    public void playerShouldGenerateAllBoardStates() {
        List<Board> allStates = evolutionaryPlayer.getAllBoardStates();
        assertEquals(2480, allStates.size());
    }


}
