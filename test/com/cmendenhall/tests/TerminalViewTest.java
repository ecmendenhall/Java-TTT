package com.cmendenhall.tests;

import com.cmendenhall.mocks.TestConsole;
import com.cmendenhall.views.TerminalView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TerminalViewTest extends TicTacToeTest {

    private TerminalView view;
    private TestConsole console;

    @Before
    public void setUp() throws Exception {
        setUpRecorder();
        startRecorder();
        view = new TerminalView();
        console = new TestConsole();
    }

    @Test
    public void viewShouldDisplayBoard() {
        startRecorder();

        view.displayBoard(TicTacToeTestHelper.noWins);
        String expected = TicTacToeTestHelper.noWins.toString();
        String output = recorder.popLastOutput();
        assertEquals(expected, output);
    }

    @Test
    public void viewShouldDisplayMessage() {
        startRecorder();

        view.displayMessage("Welcome to Tic-Tac-Toe");

        String expected = "Welcome to Tic-Tac-Toe";
        String output = recorder.popLastOutput();
        assertEquals(expected, output);

    }

    @Test
    public void viewShouldGetInput() {
        console.setTestInput("upper left");
        view = new TerminalView(console);

        String input = view.getInput();
        assertEquals("upper left", input);
    }
}