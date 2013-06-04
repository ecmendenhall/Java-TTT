package com.cmendenhall.tests;

import com.cmendenhall.views.swing.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

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

    /*@Test
    public void messagePanelShouldDisplayBoard() {
        swingView.displayBoard(TicTacToeTestHelper.noWins);

        BoardPanel boardPanel = (BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        JTable board = (JTable)getComponent(boardPanel, "boardTable");

        TableModel boardData = board.getModel();
        String topLeft      = (String)boardData.getValueAt(0, 0);
        String topMiddle    = (String)boardData.getValueAt(0, 1);
        String topRight     = (String)boardData.getValueAt(0, 2);
        String middleLeft   = (String)boardData.getValueAt(1, 0);
        String middleCenter = (String)boardData.getValueAt(1, 1);
        String middleRight  = (String)boardData.getValueAt(1, 2);
        String lowerLeft    = (String)boardData.getValueAt(2, 0);
        String lowerCenter  = (String)boardData.getValueAt(2, 1);
        String lowerRight   = (String)boardData.getValueAt(2, 2);

        assertEquals("O", topLeft);
        assertEquals("O", topMiddle);
        assertEquals("X", topRight);
        assertEquals("X", middleLeft);
        assertEquals("X", middleCenter);
        assertEquals("O", middleRight);
        assertEquals("O", lowerLeft);
        assertEquals("X", lowerCenter);
        assertEquals("X", lowerRight);
    }*/

    @Test
    public void swingViewShouldListenForButtonClicks() {
        ConfigPanel configPanel = (ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        GameActionPanel actionPanel = (GameActionPanel)getComponent(configPanel, "gameActionPanel");
        JButton button = (JButton)getComponent(actionPanel, "newGameButton");

        button.doClick();
        //assertTrue(newGameWasClicked);
    }

    /* @Test
    public void swingViewShouldListenForBoardClicks() throws AWTException, InterruptedException {
        SwingView.BoardPanel boardPanel = (SwingView.BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        final JTable boardTable = (JTable)getComponent(boardPanel, "boardTable");

        boardPanel.addBoardClickListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = boardTable.rowAtPoint(e.getPoint());
                    int column = boardTable.columnAtPoint(e.getPoint());
                    if (row >= 0 && column >= 0) {
                        assertEquals(1, row);
                        assertEquals(1, column);
                    }
                }
        });

        swingView.setVisible(true);
        Robot robot = new Robot();
        robot.delay(500);
        robot.mouseMove(160, 100);
        robot.waitForIdle();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

    } */
}
