package com.cmendenhall;

public class TerminalView implements View {
    private IOHandler io;

    public TerminalView() {
        setUpConsole();
    }

    public TerminalView(IOHandler ioHandler) {
        io = ioHandler;
    }

    private void setUpConsole() {
        if (System.console() == null) {
            System.err.println("No system console found.");
            io = new CharStreamConsole();
        } else {
            io = new SystemConsole();
        }
    }

    public void displayBoard(Board board) {
        String boardString = board.toString();
        io.print(boardString);
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public void endGame() throws GameOverException {
        throw new GameOverException("Game over.");
    }

    public String getInput() {
        String input = io.readLine();
        return input;
    }

    public void reload() {

    }
}
