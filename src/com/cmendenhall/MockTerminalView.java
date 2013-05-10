package com.cmendenhall;

import static com.cmendenhall.TicTacToeSymbols.X;

public class MockTerminalView extends TerminalView {

    public MockTerminalView() {
            io = new TestConsole();
    }

    public TestConsole getTestConsole() {
        return (TestConsole) io;
    }

    public BoardCoordinate prompt() {
        String locationPhrase = io.readLine("Your move: ");
        try {
            return new BoardCoordinate(locationPhrase);
        } catch (InvalidCoordinateException e) {
            System.out.println(e.getMessage());
        }
        return new BoardCoordinate(-1, -1);
    }

    public void getNextMove(Board board, GameController gameController) {
        print(board);
        return;
    }

    private void playAgainPrompt(GameController gameController) {
        String playAgain = io.readLine("Play again? (y/n): ");
    }

    public void drawEndGame(Board board, GameController gameController) {
        System.out.println();
        board.print();
        System.out.println("Game over: It's a draw.");
        playAgainPrompt(gameController);
    }

    public void winEndGame(Board board, GameController gameController) {
        int winner = board.getWinningRow().winner();
        String messageTemplate = "Game over: Player ";
        String winnerDescription = (winner == X) ? "X wins." : "O wins.";
        System.out.println();
        board.print();
        System.out.println(messageTemplate + winnerDescription);
        playAgainPrompt(gameController);
    }
}
