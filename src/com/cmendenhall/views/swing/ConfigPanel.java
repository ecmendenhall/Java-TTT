package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigPanel extends JPanel {
    private GameActionPanel gameActionPanel;
    private BoardConfigPanel boardConfigPanel;
    private PlayerConfigPanel playerOneConfigPanel;
    private PlayerConfigPanel playerTwoConfigPanel;
    private SwingView swingView;
    private InputAdapter inputAdapter;

    public ConfigPanel(SwingView swingView, InputAdapter inputAdapter) {
        this.swingView = swingView;
        this.inputAdapter = inputAdapter;
        setName("configPanel");

        Dimension defaultSize = getSize();
        defaultSize.width = 350;
        defaultSize.height = 200;
        setPreferredSize(defaultSize);
        setMaximumSize(defaultSize);

        gameActionPanel = new GameActionPanel(this);
        boardConfigPanel = new BoardConfigPanel();
        playerOneConfigPanel = new PlayerConfigPanel("Player X", "playerOneConfigPanel", new Color(130, 216, 0));
        playerTwoConfigPanel = new PlayerConfigPanel("Player O", "playerTwoConfigPanel", new Color(108, 136, 255));

        add(gameActionPanel,
            boardConfigPanel,
            playerOneConfigPanel,
            playerTwoConfigPanel);

        addNewGameListener();
    }

    public Component add(Component... components) {
        for (Component component : components) {
            super.add(component);
        }
        return this;
    }

    public String boardSize() {
        return boardConfigPanel.boardSize();
    }

    public SwingView getView() {
        return swingView;
    }

    public void sendConfigInput() {
        inputAdapter.sendBoardSize(boardConfigPanel.boardSize());
        inputAdapter.sendPlayerSelection(playerOneConfigPanel.humanSelected());
        inputAdapter.sendPlayerSelection(playerTwoConfigPanel.humanSelected());
    }

    public void enableConfigButtons() {
        gameActionPanel.enableNewGameButton();
        playerOneConfigPanel.enablePlayerSelection();
        playerTwoConfigPanel.enablePlayerSelection();
        boardConfigPanel.enableSpinner();
    }


    public void addNewGameListener() {
        ActionListener newGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sendConfigInput();
                int boardSize = Integer.parseInt(boardSize());
                int newWidth = Math.max((boardSize + 1) * 50, 350);
                int newHeight = Math.max((boardSize + 1) * 50 + 200, 400);
                getView().resizeWindow(newWidth, newHeight);
                getView().enableBoard();
                getView().displayMessage(" ");
                gameActionPanel.disableNewGameButton();
                playerOneConfigPanel.disablePlayerSelection();
                playerTwoConfigPanel.disablePlayerSelection();
                boardConfigPanel.disableSpinner();
            }
        };
        gameActionPanel.setUpNewGameListener(newGameListener);
    }

}
