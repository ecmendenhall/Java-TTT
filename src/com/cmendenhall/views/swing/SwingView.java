package com.cmendenhall.views.swing;

import com.cmendenhall.board.Board;
import com.cmendenhall.utils.StringLoader;
import com.cmendenhall.views.View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SwingView extends JFrame implements View {
    static final int WIDTH = 350;
    static final int HEIGHT = 400;

    private InputAdapter inputAdapter = new InputAdapter();

    public BoardPanel boardPanel = new BoardPanel(this, inputAdapter);
    private MessagePanel messagePanel = new MessagePanel();
    private ConfigPanel configPanel = new ConfigPanel(this, inputAdapter);

    public SwingView() {
        setTitle("Tic Tac Toe");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        add(Box.createRigidArea(new Dimension(100, 25)),
            boardPanel,
            messagePanel,
            configPanel);
    }

    public Component add(Component... components) {
        for (Component component : components) {
            super.add(component);
        }
        return this;
    }

    public void displayBoard(Board board) {
        boardPanel.loadBoard(board);
    }

    public void displayMessage(String message) {
        if (message.length() == 0) {
            return;
        } else {
            JLabel messageLabel = messagePanel.getLabel();
            messageLabel.setText(message);
        }
    }

    public void reload() {
        boardPanel.disableBoard();
        inputAdapter.clearInput();
        configPanel.enableConfigButtons();
    }

    public void resizeWindow(int width, int height) {
        setSize(width, height);
    }

    public String getInput() {
        return inputAdapter.getInput();
    }

    public HashMap<String, String> getStrings() {
        HashMap<String, String> viewStrings = new StringLoader().getViewStrings("/swingviewstrings.properties");
        return viewStrings;
    }

    public void enableBoard() {
        boardPanel.enableBoard();
    }
}
