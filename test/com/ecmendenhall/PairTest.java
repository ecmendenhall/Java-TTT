package com.ecmendenhall;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class PairTest {

    @Test
    public void pairStoresAndReturnsObjects() {
        Pair<BoardCoordinate, Board> movepair = new Pair(new BoardCoordinate(0, 0), new Board());
        assertNotNull(movepair.first);
        assertNotNull(movepair.rest);
    }
}
