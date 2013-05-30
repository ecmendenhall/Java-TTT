package com.cmendenhall.tests;

import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.utils.OutputRecorder;
import com.cmendenhall.views.TerminalView;
import com.cmendenhall.mocks.TestConsole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TerminalViewTest {

    private TerminalView view = new TerminalView();
    private TestConsole console = new TestConsole();

    private OutputRecorder recorder;


    @Before
    public void setUp() throws Exception {
        setUpRecorder();
    }

    private void setUpRecorder() throws UnsupportedEncodingException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        recorder = new OutputRecorder(output, true, "UTF-8");
    }

    private void startRecorder() {
        System.setOut(recorder);
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