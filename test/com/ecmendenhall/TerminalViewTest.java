package com.ecmendenhall;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class TerminalViewTest extends TicTacToeTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private TerminalView terminalview;

    @Before
    public void setUp() throws UnsupportedEncodingException, InvalidPlayerException {
        terminalview = new TerminalView(true);
        System.setOut(new PrintStream(output, true, "UTF-8"));
    }

    @Test
    public void terminalViewShouldPrintBoards() {
        terminalview.print(nowins);
        assertEquals(nowins.toString(), output.toString());

    }

    @Test
    public void terminalViewShouldPromptUserForInput() throws Exception {
        terminalview.io.setTestInput("top left");
        BoardCoordinate coordinate = terminalview.prompt();

        BoardCoordinate expected = new BoardCoordinate("top left");
        assertEquals(expected.getRow(), coordinate.getRow());
        assertEquals(expected.getColumn(), coordinate.getColumn());
    }

    @After
    public void cleanUp() {
        System.setOut(stdout);
    }
}