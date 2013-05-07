package com.cmendenhall;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TerminalViewTest extends TicTacToeTest {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private TerminalView terminalView;
    private GameController gameController;

    @Before
    public void setUp() throws UnsupportedEncodingException, InvalidPlayerException {
        terminalView = new TerminalView(true);
    }

    @Test
    public void terminalViewShouldPrintBoards() throws UnsupportedEncodingException {
        System.setOut(new PrintStream(output, true, "UTF-8"));
        terminalView.print(noWins);
        assertEquals("\n" + noWins.toString(), output.toString());
        System.setOut(stdout);
    }

    @Test
    public void terminalViewShouldPromptUserForInput() throws Exception {
        terminalView.io.setTestInput("top left");
        BoardCoordinate coordinate = terminalView.prompt();

        BoardCoordinate expected = new BoardCoordinate("top left");
        assertEquals(expected.getRow(), coordinate.getRow());
        assertEquals(expected.getColumn(), coordinate.getColumn());
    }

    @After
    public void cleanUp() {
        System.setOut(stdout);
    }
}