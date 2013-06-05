package com.cmendenhall.tests;

import com.cmendenhall.views.swing.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.swing.*;
import java.awt.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SwingViewTest {

    private SwingView swingView;

    @Before
    public void setUp() {
        swingView = new SwingView();
    }

    @Test
    public void swingViewHasCorrectSize() {
        assertEquals(350, swingView.getWidth());
        assertEquals(400, swingView.getHeight());
    }

    private Component getComponent(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
        }
        return null;
    }

    @Test
    public void swingViewHasMessagePanel() {
        MessagePanel messagePanel =
                (MessagePanel)getComponent(swingView.getContentPane(), "messagePanel");
        JLabel label =
                (JLabel)getComponent(messagePanel, "messagePanelLabel");
        assertTrue(label.isVisible());
        assertEquals("Welcome to Tic-Tac-Toe", label.getText());
    }

    @Test
    public void swingViewHasBoardPanel() {
        BoardPanel boardPanel =
                (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        assertTrue(boardPanel.isVisible());
    }

    @Test
    public void boardPanelContainsJTable() {
        BoardPanel boardPanel =
                (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        JTable boardTable =
                (JTable)getComponent(boardPanel, "boardTable");
        assertTrue(boardTable.isVisible());
    }

    @Test
    public void boardPanelCorrectlyDisplaysBoard() {
        BoardPanel boardPanel =
                (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        boardPanel.loadBoard(TicTacToeTestHelper.noWins);
    }

    @Test
    public void swingViewHasConfigPanel() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        assertTrue(configPanel.isVisible());
    }

    @Test
    public void configPanelHasGameActionPanel() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        GameActionPanel gameActionPanel =
                (GameActionPanel)getComponent(configPanel, "gameActionPanel");
    }

    @Test
    public void gameActionPanelHasNewGameButton() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        GameActionPanel gameActionPanel =
                (GameActionPanel)getComponent(configPanel, "gameActionPanel");

        JButton newGameButton =
                (JButton)getComponent(gameActionPanel, "newGameButton");

        assertTrue(newGameButton.isVisible());
    }


    @Test
    public void newGameButtonHasCorrectLabel() {

        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        GameActionPanel gameActionPanel =
                (GameActionPanel)getComponent(configPanel, "gameActionPanel");

        JButton newGameButton =
                (JButton)getComponent(gameActionPanel, "newGameButton");

        assertEquals("New game", newGameButton.getText());
    }

    @Test
    public void configPanelHasPlayerOneConfigPanel() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        PlayerConfigPanel playerOneConfigPanel =
                (PlayerConfigPanel)getComponent(configPanel, "playerOneConfigPanel");
        assertTrue(playerOneConfigPanel.isVisible());
    }


    @Test
    public void configPanelHasPlayerTwoConfigPanel() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        PlayerConfigPanel playerTwoConfigPanel =
                (PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        assertTrue(playerTwoConfigPanel.isVisible());
    }

    @Test
    public void playerConfigPanelHasTwoRadioButtons() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        PlayerConfigPanel playerTwoConfigPanel =
                (PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JRadioButton humanButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "humanButton");
        JRadioButton computerButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "computerButton");
        assertTrue(humanButton.isVisible());
        assertTrue(computerButton.isVisible());
    }

    @Test
    public void playerConfigPanelHasCorrectLabel() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        PlayerConfigPanel playerTwoConfigPanel =
                (PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JLabel playerTwo =
                (JLabel)getComponent(playerTwoConfigPanel, "playerLabel");
        assertEquals("Player O", playerTwo.getText());
    }

    @Test
    public void playerConfigPanelStoresPlayerConfigState() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        PlayerConfigPanel playerTwoConfigPanel =
                (PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        assertTrue(playerTwoConfigPanel.humanSelected());
    }

    @Test
    public void radioButtonsHaveCorrectLabels() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        PlayerConfigPanel playerTwoConfigPanel =
                (PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JRadioButton humanButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "humanButton");
        JRadioButton computerButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "computerButton");
        assertEquals("Human", humanButton.getText());
        assertEquals("Computer", computerButton.getText());
    }

    @Test
    public void configPanelHasBoardConfigPanel() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        BoardConfigPanel boardConfigPanel =
                (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");
        assertTrue(boardConfigPanel.isVisible());
    }

    @Test
    public void boardConfigPanelHasBoardSizeSpinner() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        BoardConfigPanel boardConfigPanel =
                (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");

        JSpinner boardSizeSpinner =
                (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        assertTrue(boardSizeSpinner.isVisible());
    }

    @Test
    public void boardConfigPanelSpinnerStoresSpinnerState() {
        ConfigPanel configPanel =
                (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        BoardConfigPanel boardConfigPanel =
                (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");

        assertEquals("3", boardConfigPanel.boardSize());
    }

    @Test
    public void messagePanelShouldDisplayMessage() {
        swingView.displayMessage("Shall we play a game?");

        MessagePanel messagePanel = (MessagePanel)getComponent(swingView.getContentPane(), "messagePanel");
        JLabel message = (JLabel)getComponent(messagePanel, "messagePanelLabel");
        assertEquals("Shall we play a game?", message.getText());
    }

    @Test
    public void boardSizeSpinnerCanBeEnabled() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        BoardConfigPanel boardConfigPanel = (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");
        JSpinner spinner = (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        boardConfigPanel.enableSpinner();
        assertTrue(spinner.isEnabled());
    }

    @Test
    public void boardSizeSpinnerCanBeDisabled() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        BoardConfigPanel boardConfigPanel = (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");
        JSpinner spinner = (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        boardConfigPanel.disableSpinner();
        assertFalse(spinner.isEnabled());
    }

    @Test
    public void newGameButtonCanBeDisabled() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        GameActionPanel gameActionPanel = (GameActionPanel)getComponent(configPanel, "gameActionPanel");
        JButton newGameButton = (JButton)getComponent(gameActionPanel, "newGameButton");

        gameActionPanel.disableNewGameButton();

        assertFalse(newGameButton.isEnabled());
    }

    @Test
    public void newGameButtonCanBeEnabled() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        GameActionPanel gameActionPanel = (GameActionPanel)getComponent(configPanel, "gameActionPanel");
        JButton newGameButton = (JButton)getComponent(gameActionPanel, "newGameButton");

        gameActionPanel.disableNewGameButton();
        gameActionPanel.enableNewGameButton();

        assertTrue(newGameButton.isEnabled());
    }

    @Test
    public void configButtonsCanBeEnabled() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        BoardConfigPanel boardConfigPanel = (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");
        JSpinner spinner = (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        PlayerConfigPanel playerOneConfigPanel = (PlayerConfigPanel)getComponent(configPanel, "playerOneConfigPanel");
        JRadioButton humanButtonOne = (JRadioButton)getComponent(playerOneConfigPanel, "humanButton");
        JRadioButton computerButtonOne = (JRadioButton)getComponent(playerOneConfigPanel, "computerButton");

        PlayerConfigPanel playerTwoConfigPanel = (PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JRadioButton humanButtonTwo = (JRadioButton)getComponent(playerTwoConfigPanel, "humanButton");
        JRadioButton computerButtonTwo = (JRadioButton)getComponent(playerTwoConfigPanel, "computerButton");

        GameActionPanel gameActionPanel = (GameActionPanel)getComponent(configPanel, "gameActionPanel");
        JButton newGameButton = (JButton)getComponent(gameActionPanel, "newGameButton");

        configPanel.addNewGameListener();
        newGameButton.doClick();

        configPanel.enableConfigButtons();

        assertTrue(spinner.isEnabled());

        assertTrue(humanButtonOne.isEnabled());
        assertTrue(humanButtonTwo.isEnabled());

        assertTrue(computerButtonOne.isEnabled());
        assertTrue(computerButtonTwo.isEnabled());

        assertTrue(newGameButton.isEnabled());
    }

    @Test
    public void boardCanBeDisabled() {
        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        boardPanel.disableBoard();

        JTable boardTable = (JTable)getComponent(boardPanel, "boardTable");

        assertFalse(boardTable.isEnabled());
    }

    @Test
    public void boardCanBeEnabled() {
        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        boardPanel.disableBoard();
        boardPanel.enableBoard();

        JTable boardTable = (JTable)getComponent(boardPanel, "boardTable");

        assertTrue(boardTable.isEnabled());
    }

    @Test
    public void boardCanBeEnabledByView() {
        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        boardPanel.disableBoard();
        swingView.enableBoard();

        JTable boardTable = (JTable)getComponent(boardPanel, "boardTable");

        assertTrue(boardTable.isEnabled());
    }

    @Test
    public void enabledBoardHasWhiteBackground() {
        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        boardPanel.enableBoard();

        JTable boardTable = (JTable)getComponent(boardPanel, "boardTable");

        Color backgroundColor = boardTable.getBackground();
        assertEquals(backgroundColor, Color.WHITE);
    }


    @Test
    public void viewDisplaysCorrectBoards() {
        swingView.displayBoard(TicTacToeTestHelper.noWins);

        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        JTable board = (JTable)getComponent(boardPanel, "boardTable");

        /*
        String topLeft      = (String)board.getValueAt(0, 0);
        String topMiddle    = (String)board.getValueAt(0, 1);
        String topRight     = (String)board.getValueAt(0, 2);
        String middleLeft   = (String)board.getValueAt(1, 0);
        String middleCenter = (String)board.getValueAt(1, 1);
        String middleRight  = (String)board.getValueAt(1, 2);
        String lowerLeft    = (String)board.getValueAt(2, 0);
        String lowerCenter  = (String)board.getValueAt(2, 1);
        String lowerRight   = (String)board.getValueAt(2, 2);

        assertEquals("O", topLeft);
        assertEquals("O", topMiddle);
        assertEquals("X", topRight);
        assertEquals("X", middleLeft);
        assertEquals("X", middleCenter);
        assertEquals("O", middleRight);
        assertEquals("O", lowerLeft);
        assertEquals("X", lowerCenter);
        assertEquals("X", lowerRight);*/

    }

    @Test
    public void viewCanResizeWindow() {
        swingView.resizeWindow(500, 500);

        Dimension viewSize = swingView.getSize();

        assertEquals(500, viewSize.getHeight(), 0);
        assertEquals(500, viewSize.getWidth(), 0);

    }

    @Test
    public void configPanelReturnsCorrectView() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView view = configPanel.getView();

        assertEquals(swingView, view);
    }

    @Test
    public void configPanelEnqueuesCorrectInput() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        configPanel.sendConfigInput();

        assertEquals("3", swingView.getInput());
        assertEquals("h", swingView.getInput());
        assertEquals("h", swingView.getInput());
    }

    @Test
    public void viewClearsInputOnReload() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        configPanel.sendConfigInput();
        swingView.reload();

        assertEquals("", swingView.getInput());
    }

    @Test
    public void viewDisablesBoardOnReload() {
        swingView.reload();

        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        JTable board = (JTable)getComponent(boardPanel, "boardTable");

        assertFalse(board.isEnabled());
    }

    @Test
    public void viewEnablesConfigButtonsOnReload() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        BoardConfigPanel boardConfigPanel = (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");
        JSpinner spinner = (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        PlayerConfigPanel playerOneConfigPanel = (PlayerConfigPanel)getComponent(configPanel, "playerOneConfigPanel");
        JRadioButton humanButtonOne = (JRadioButton)getComponent(playerOneConfigPanel, "humanButton");
        JRadioButton computerButtonOne = (JRadioButton)getComponent(playerOneConfigPanel, "computerButton");

        PlayerConfigPanel playerTwoConfigPanel = (PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JRadioButton humanButtonTwo = (JRadioButton)getComponent(playerTwoConfigPanel, "humanButton");
        JRadioButton computerButtonTwo = (JRadioButton)getComponent(playerTwoConfigPanel, "computerButton");

        GameActionPanel gameActionPanel = (GameActionPanel)getComponent(configPanel, "gameActionPanel");
        JButton newGameButton = (JButton)getComponent(gameActionPanel, "newGameButton");

        swingView.reload();

        assertTrue(spinner.isEnabled());

        assertTrue(humanButtonOne.isEnabled());
        assertTrue(humanButtonTwo.isEnabled());

        assertTrue(computerButtonOne.isEnabled());
        assertTrue(computerButtonTwo.isEnabled());

        assertTrue(newGameButton.isEnabled());
    }

    @Test
    public void boardTableStylerAppliesCorrectStyle() {
        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        JTable board = (JTable)getComponent(boardPanel, "boardTable");

        BoardTableStyler.applyStyle(board);

        assertEquals("boardTable", board.getName());
        assertTrue(board.getShowVerticalLines());
        assertTrue(board.getShowHorizontalLines());
        assertFalse(board.getColumnSelectionAllowed());
        assertFalse(board.getRowSelectionAllowed());
    }

    @Test
    public void windowSizeChangesWhenBoardIsResized() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        BoardConfigPanel boardConfigPanel = (BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");
        JSpinner spinner = (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        GameActionPanel gameActionPanel = (GameActionPanel)getComponent(configPanel, "gameActionPanel");
        JButton newGameButton = (JButton)getComponent(gameActionPanel, "newGameButton");

        spinner.setValue(6);
        configPanel.addNewGameListener();
        newGameButton.doClick();

        Dimension viewSize = swingView.getSize();

        assertEquals(350, viewSize.getWidth(), 0);
        assertEquals(550, viewSize.getHeight(), 0);

    }
}
