package com.cmendenhall.views.swing;

import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.board.Board;
import com.cmendenhall.views.View;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SwingView extends JFrame implements View {
    static final int WIDTH = 350;
    static final int HEIGHT = 400;

    private InputAdapter inputAdapter = new InputAdapter();

    public BoardPanel boardPanel = new BoardPanel(this, inputAdapter);
    private MessagePanel messagePanel = new MessagePanel();
    private ConfigPanel configPanel = new ConfigPanel(this, inputAdapter);

    private String divider;
    private String playAgain;
    private String choosePlayerOne;
    private String choosePlayerTwo;
    private String boardSize;

    private Set<String> ignoreThese = new HashSet<String>();

    public SwingView() {
        loadViewStrings();
        setTitle("Tic Tac Toe");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        add(Box.createRigidArea(new Dimension(100, 25)));
        add(boardPanel);
        add(messagePanel);
        add(configPanel);
    }

    private void loadViewStrings() {
        Properties viewStrings = new Properties();
        try {
            viewStrings.load(getClass().getResourceAsStream("/viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        divider = viewStrings.getProperty("divider");
        playAgain = viewStrings.getProperty("playagain");
        choosePlayerOne = viewStrings.getProperty("chooseplayerone");
        choosePlayerTwo = viewStrings.getProperty("chooseplayertwo");
        boardSize = viewStrings.getProperty("boardsize");

        ignoreThese.add(divider);
        ignoreThese.add(playAgain);
        ignoreThese.add(choosePlayerOne);
        ignoreThese.add(choosePlayerTwo);
        ignoreThese.add(boardSize);
    }

    public void displayBoard(Board board) {
        boardPanel.loadBoard(board);
    }

    public void displayMessage(String message) {
        if (message.contains("move")) {
            int endStart = message.indexOf("player") + 6;
            String ending = message.substring(endStart);
            message = "Your move, player" + ending;
        } else if (ignoreThese.contains(message)) {
            return;
        }
        JLabel messageLabel = messagePanel.getLabel();
        messageLabel.setText(message);
    }

    public void endGame() throws GameOverException {
        throw new GameOverException("Game over.");
    }

    public void reload() {
        inputAdapter.clearInput();
        configPanel.enableNewGameButton();
    }

    public void resizeWindow(int width, int height) {
        setSize(width, height);
    }

    public String getInput() {
        return inputAdapter.getInput();
    }

}