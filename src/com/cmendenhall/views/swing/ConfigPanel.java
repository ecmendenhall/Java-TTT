package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;

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
        playerOneConfigPanel = new PlayerConfigPanel("Player X", "playerOneConfigPanel");
        playerTwoConfigPanel = new PlayerConfigPanel("Player O", "playerTwoConfigPanel");

        add(gameActionPanel,
            boardConfigPanel,
            playerOneConfigPanel,
            playerTwoConfigPanel);
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

    public void enableNewGameButton() {
        gameActionPanel.enableNewGameButton();
    }

}
