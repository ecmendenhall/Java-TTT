package com.cmendenhall;
import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class GameControllerTest extends TicTacToeTest {
    private MockGameController gameController = new MockGameController();
    private MockTerminalView terminalView = new MockTerminalView();
    private TestConsole testConsole =  terminalView.getTestConsole();
    private Board topLeftResponse;
    private Board topRightResponse;
    private String welcome;
    private String playagain;
    private String gameoverwin;
    private String gameoverdraw;
    private String xwins;

    @Before
    public void setUp() {

        Properties viewstrings = new Properties();

        try {
            viewstrings.load(getClass().getResourceAsStream("viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        welcome = viewstrings.getProperty("welcome");
        playagain = viewstrings.getProperty("playagain");
        gameoverwin = viewstrings.getProperty("gameoverwin");
        gameoverdraw = viewstrings.getProperty("gameoverdraw");
        xwins = viewstrings.getProperty("xwins");

        topLeftResponse = new Board( new Row(X, _, _),
                                     new Row(_, O, _),
                                     new Row(_, _, _) );

        topRightResponse = new Board( new Row(_, _, X),
                                      new Row(_, O, _),
                                      new Row(_, _, _) );

    }

    @Test
    public void gameControllerShouldStoreCurrentBoard() {
        Board startingBoard = gameController.getCurrentBoard();
        assertTrue(startingBoard.equals(emptyBoard));
    }

    @Test
    public void gameControllerShouldGetPlayerMoveFromView() throws Exception {
        assertTrue(gameController.getCurrentBoard().equals(emptyBoard));

        testConsole.setTestInput("top left");
        terminalView.passMoveToController(new BoardCoordinate("top left"), gameController);
        assertTrue(gameController.getCurrentBoard().equals(topLeftResponse));
    }

    @Test
    public void gameControllerShouldPassNewBoardToView() throws Exception {
        System.setOut(new PrintStream(output));

        gameController = new MockGameController();
        terminalView = new MockTerminalView();
        gameController.passNewBoardToView(topLeftResponse, terminalView);

        assertEquals("\n" + topLeftResponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldPassCorrectResponseToView() throws Exception {
        System.setOut(new PrintStream(output));

        gameController = new MockGameController();
        gameController.processMove(new BoardCoordinate("top right"), terminalView);
        assertTrue(topRightResponse.equals(gameController.getCurrentBoard()));

        gameController.passNewBoardToView(gameController.getCurrentBoard(), terminalView);
        assertEquals("\n" + topRightResponse.toString(), output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldStartNewGame() throws Exception {
        System.setOut(new PrintStream(output));
        testConsole.setTestInput("middle center");

        gameController = new MockGameController();
        gameController.newGame(terminalView);

        String expected = welcome + "\n\n" + new Board().toString();

        assertEquals(expected, output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldCatchInvalidMoves() throws Exception {

        testConsole.setTestInput("lower left");
        gameController.newGame(terminalView);
        BoardCoordinate nextMove = terminalView.prompt();
        terminalView.passMoveToController(nextMove, gameController);

        System.setOut(new PrintStream(output));

        gameController.processMove(new BoardCoordinate("lower left"), terminalView);

        assertEquals("Square is already full.\n\n" + gameController.getCurrentBoard(), output.toString());

        System.setOut(stdout);
    }

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void gameControllerShouldCheckForWins() throws Exception {
        gameController = new MockGameController();
        gameController.setBoard(playerXWins);

        System.setOut(new PrintStream(output));
        gameController.checkForEndState(terminalView);

        assertEquals("\n" + gameController.getCurrentBoard() + gameoverwin + " " + xwins + "\n" + playagain + " \n", output.toString());

        System.setOut(stdout);
    }

    @Rule
    public final ExpectedSystemExit drawExit = ExpectedSystemExit.none();

    @Test
    public void gameControllerShouldCheckForEndStates() throws Exception {

        gameController = new MockGameController();
        gameController.setBoard(noWins);

        System.setOut(new PrintStream(output));

        gameController.checkForEndState(terminalView);

        assertEquals("\n" + gameController.getCurrentBoard() + gameoverdraw + "\n" + playagain + " \n", output.toString());

        System.setOut(stdout);
    }

    @Test
    public void gameControllerShouldCatchInvalidBoardCoordinates() throws Exception {
        testConsole.setTestInput("THIS IS NOT A VALID MOVE");

        gameController.newGame(terminalView);
        System.setOut(new PrintStream(output));
        BoardCoordinate nextMove = terminalView.prompt();
        terminalView.passMoveToController(nextMove, gameController);

        assertEquals("Your move: \n" +
                     "That's not a valid board location.\n" +
                     "Invalid move coordinate.\n\n" +
                     gameController.getCurrentBoard(),
                     output.toString());

        System.setOut(stdout);
    }
}
