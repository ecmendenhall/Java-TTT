package com.ecmendenhall;

import java.io.*;

public class TerminalView {
    protected static IOHandler io;

    public static IOHandler streamDevice(InputStream in, OutputStream out, Boolean test) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        PrintWriter writer = new PrintWriter(out, true);
        if (test) {
            return new TestConsole(reader, writer);
        } else {
            return new CharStreamConsole(reader, writer);
        }
    }

    public TerminalView() throws InvalidPlayerException {
            Console systemconsole = System.console();
        if (systemconsole == null) {
            System.err.println("No console found.");
            io = streamDevice(System.in, System.out, false);
        } else {
            io = new SystemConsole(systemconsole);
        }
    }

    public TerminalView(Boolean test) throws InvalidPlayerException {
        Console systemconsole = System.console();
        if (test) {
            io = streamDevice(System.in, System.out, true);
        } else if (systemconsole == null) {
            System.err.println("No console found.");
            io = streamDevice(System.in, System.out, false);
        } else {
            io = new SystemConsole(systemconsole);
        }
    }

    class SystemConsole extends IOHandler {
        private final Console console;

        public SystemConsole(Console console) {
            this.console = console;
        }

        @Override
        public IOHandler printf(String fmt, Object... params)
                throws IOException {
            console.format(fmt, params);
            return this;
        }

        @Override
        public Reader reader() throws IOException {
            return console.reader();
        }

        @Override
        public String readLine() throws IOException {
            return console.readLine();
        }

        @Override
        public String readLine(String message) throws IOException {
            return console.readLine(message);
        }

        @Override
        public char[] readPassword() throws IOException {
            return console.readPassword();
        }

        @Override
        public PrintWriter writer() throws IOException {
            return console.writer();
        }

        @Override
        public void setTestInput(String input) throws Exception {
            throw new Exception("SystemConsole cannot access test input.");
        }
    }

    private static class CharStreamConsole extends IOHandler {
        private final BufferedReader reader;
        private final PrintWriter writer;

        public CharStreamConsole(BufferedReader reader, PrintWriter writer) {
            this.reader = reader;
            this.writer = writer;
        }

        @Override
        public CharStreamConsole printf(String fmt, Object... params)
                throws IOException {
            writer.printf(fmt, params);
            return this;
        }

        @Override
        public String readLine() throws IOException {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new IllegalStateException();
            }
        }

        @Override
        public String readLine(String message) throws IOException {
            try {
                System.out.println(message);
                return reader.readLine();
            } catch (IOException e) {
                throw new IllegalStateException();
            }
        }

        @Override
        public char[] readPassword() throws IOException {
            return readLine().toCharArray();
        }

        @Override
        public Reader reader() throws IOException {
            return reader;
        }

        @Override
        public PrintWriter writer() throws IOException {
            return writer;
        }

        @Override
        public void setTestInput(String input) throws Exception {
            throw new Exception("CharStreamConsole cannot access test input.");
        }
    }

    private static class TestConsole extends CharStreamConsole {
        public String testinput = "";

        public TestConsole(BufferedReader reader, PrintWriter writer) {
            super(reader, writer);
        }

        @Override
        public void setTestInput(String input) {
            testinput = input;
        }

        @Override
        public String readLine(String unused) {
            return testinput;
        }

    }

    public void print(Board board) {
        board.print();
    }

    public BoardCoordinate prompt() throws IOException {
        String locationphrase = io.readLine("Your move: ");
        return new BoardCoordinate(locationphrase);
    }

    public void passMoveToController(BoardCoordinate move, GameController gamecontroller) throws InvalidMoveException, IOException {
        gamecontroller.processMove(move, this, false);
    }

    public void passMoveToController(BoardCoordinate move, GameController gamecontroller, boolean pause) throws InvalidMoveException, IOException {
        gamecontroller.processMove(move, this, pause);
    }

    public void processBoard(Board board, GameController gamecontroller) throws InvalidMoveException, IOException {
        processBoard(board, gamecontroller, false);
    }

    public void processBoard(Board board, GameController gamecontroller, Boolean pause) throws InvalidMoveException, IOException {
        print(board);
        if (pause) {
            return;
        }
        BoardCoordinate nextmove = prompt();
        passMoveToController(nextmove, gamecontroller);
    }

    public void promptWithMessage(String message, Board board, GameController gamecontroller) throws InvalidMoveException, IOException {
        promptWithMessage(message, board, gamecontroller, false);
    }

    public void promptWithMessage(String message, Board board, GameController gamecontroller, Boolean pause) throws InvalidMoveException, IOException {
        System.out.println(message);
        processBoard(board, gamecontroller, pause);
    }

}
