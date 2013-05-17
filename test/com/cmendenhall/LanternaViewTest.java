package com.cmendenhall;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static junit.framework.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class LanternaViewTest extends TicTacToeTest {

    private LanternaView view;

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream outputStream;


    @Before
    public void setUp() throws Exception {
        view = new LanternaView();
        outputStream = new PrintStream(output, true, "UTF-8");
    }

    @Test
    public void lanternaViewShouldExist() {
        LanternaView view = new LanternaView();
    }

    @Test
    public void lanternaViewShouldHaveTerminal() {
        assertNotNull(view.terminal);
    }

    @Test
    public void lanternaViewShouldHaveScreen() {
        assertNotNull(view.screen);
    }

    @Test
    public void lanternatViewShouldHaveIO() {
        assertNotNull(view.io);
    }

 /*
    @Test
    public void lanternaViewShouldDisplayBoard() {
        System.setOut(outputStream);

        view.displayBoard(noWins);
        String expected = noWins.toString() + "\n";
        assertEquals(expected, output.toString());

        System.setOut(stdout);
    }

    @Test
    public void lanternaViewShouldDisplayMessage() {
        System.setOut(outputStream);

        view.displayMessage("Welcome to Tic-Tac-Toe");
        assertEquals("Welcome to Tic-Tac-Toe\n", output.toString());

        System.setOut(stdout);
    }

    @Rule
    public final ExpectedSystemExit gameOver = ExpectedSystemExit.none();

    @Test
    public void lanternaViewShouldEndGame() {
        gameOver.expectSystemExit();
        view.endGame();
    }


    /*@Test
    public void viewShouldGetInput() {
        console.setTestInput("upper left");
        view = new TerminalView(console);

        String input = view.getInput();
        assertEquals("upper left", input);
    }*/

}