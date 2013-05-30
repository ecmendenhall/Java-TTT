package com.cmendenhall.tests;

import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.views.TerminalView;
import com.cmendenhall.mocks.TestConsole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TerminalViewTest {

    private TerminalView view = new TerminalView();
    private TestConsole console = new TestConsole();

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream outputStream;


    @Before
    public void setUp() throws Exception {
        outputStream = new PrintStream(output, true, "UTF-8");
    }

    @Test
    public void viewShouldDisplayBoard() {
        System.setOut(outputStream);

        view.displayBoard(TicTacToeTestHelper.noWins);
        String expected = TicTacToeTestHelper.noWins.toString() + "\n";
        assertEquals(expected, output.toString());

        System.setOut(stdout);
    }

    @Test
    public void viewShouldDisplayMessage() {
        System.setOut(outputStream);

        view.displayMessage("Welcome to Tic-Tac-Toe");
        assertEquals("Welcome to Tic-Tac-Toe\n", output.toString());

        System.setOut(stdout);
    }

    @Test(expected = GameOverException.class)
    public void viewShouldEndGame() throws GameOverException {
        view.endGame();
    }

    @Test
    public void viewShouldGetInput() {
        console.setTestInput("upper left");
        view = new TerminalView(console);

        String input = view.getInput();
        assertEquals("upper left", input);
    }
}