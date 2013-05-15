package com.cmendenhall;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Theme;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalPosition;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

public class LanternaView implements View {
    public Terminal terminal;
    public Screen screen;
    public LanternaConsole io;

    public LanternaView() {
        terminal = TerminalFacade.createTerminal();
        screen = new Screen(terminal);
        io = new LanternaConsole(screen);
    }

    public void displayBoard(Board board) {
        String boardString = board.toString();
        io.print(boardString);
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public String getInput() {
        return io.readLine();
    }

    public void endGame() {
        screen.stopScreen();
        System.exit(0);
    }

}
