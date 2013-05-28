package com.cmendenhall;

import junit.framework.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SwingViewTest extends TicTacToeTest{

    SwingView swingView;
    private boolean newGameWasClicked;
    private boolean boardWasClicked;

    @Before
    public void setUp() {
        swingView = new SwingView();
    }

    @Test
    public void swingViewHasCorrectSize() {
        assertEquals(350, swingView.getWidth());
        assertEquals(700, swingView.getHeight());
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
        SwingView.MessagePanel messagePanel =
                (SwingView.MessagePanel)getComponent(swingView.getContentPane(), "messagePanel");
        JLabel label =
                (JLabel)getComponent(messagePanel, "messagePanelLabel");
        assertTrue(label.isVisible());
        assertEquals("Welcome to Tic-Tac-Toe", label.getText());
    }

    @Test
    public void swingViewHasBoardPanel() {
        SwingView.BoardPanel boardPanel =
                (SwingView.BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        assertTrue(boardPanel.isVisible());
    }

    @Test
    public void boardPanelContainsJTable() {
        SwingView.BoardPanel boardPanel =
                (SwingView.BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        JTable boardTable =
                (JTable)getComponent(boardPanel, "boardTable");
        assertTrue(boardTable.isVisible());
    }

    @Test
    public void boardPanelCorrectlyDisplaysBoard() {
        SwingView.BoardPanel boardPanel =
                (SwingView.BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
        boardPanel.loadBoard(noWins);
    }

    @Test
    public void swingViewHasConfigPanel() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        assertTrue(configPanel.isVisible());
    }

    @Test
    public void configPanelHasGameActionPanel() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        SwingView.ConfigPanel.GameActionPanel gameActionPanel =
                (SwingView.ConfigPanel.GameActionPanel)getComponent(configPanel, "gameActionPanel");
    }

    @Test
    public void gameActionPanelHasNewGameButton() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        SwingView.ConfigPanel.GameActionPanel gameActionPanel =
                (SwingView.ConfigPanel.GameActionPanel)getComponent(configPanel, "gameActionPanel");

        JButton newGameButton =
                (JButton)getComponent(gameActionPanel, "newGameButton");

        assertTrue(newGameButton.isVisible());
    }


    @Test
    public void newGameButtonHasCorrectLabel() {

        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        SwingView.ConfigPanel.GameActionPanel gameActionPanel =
                (SwingView.ConfigPanel.GameActionPanel)getComponent(configPanel, "gameActionPanel");

        JButton newGameButton =
                (JButton)getComponent(gameActionPanel, "newGameButton");

        assertEquals("New game", newGameButton.getText());
    }

    @Test
    public void configPanelHasPlayerOneConfigPanel() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView.ConfigPanel.PlayerConfigPanel playerOneConfigPanel =
                (SwingView.ConfigPanel.PlayerConfigPanel)getComponent(configPanel, "playerOneConfigPanel");
        assertTrue(playerOneConfigPanel.isVisible());
    }


    @Test
    public void configPanelHasPlayerTwoConfigPanel() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView.ConfigPanel.PlayerConfigPanel playerTwoConfigPanel =
                (SwingView.ConfigPanel.PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        assertTrue(playerTwoConfigPanel.isVisible());
    }

    @Test
    public void playerConfigPanelHasTwoRadioButtons() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView.ConfigPanel.PlayerConfigPanel playerTwoConfigPanel =
                (SwingView.ConfigPanel.PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JRadioButton humanButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "humanButton");
        JRadioButton computerButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "computerButton");
        assertTrue(humanButton.isVisible());
        assertTrue(computerButton.isVisible());
    }

    @Test
    public void playerConfigPanelHasCorrectLabel() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView.ConfigPanel.PlayerConfigPanel playerTwoConfigPanel =
                (SwingView.ConfigPanel.PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JLabel playerTwo =
                (JLabel)getComponent(playerTwoConfigPanel, "playerLabel");
        assertEquals("Player Two", playerTwo.getText());
    }

    @Test
    public void playerConfigPanelStoresPlayerConfigState() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView.ConfigPanel.PlayerConfigPanel playerTwoConfigPanel =
                (SwingView.ConfigPanel.PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        assertTrue(playerTwoConfigPanel.humanSelected());
    }

    @Test
    public void radioButtonsHaveCorrectLabels() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView.ConfigPanel.PlayerConfigPanel playerTwoConfigPanel =
                (SwingView.ConfigPanel.PlayerConfigPanel)getComponent(configPanel, "playerTwoConfigPanel");
        JRadioButton humanButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "humanButton");
        JRadioButton computerButton =
                (JRadioButton)getComponent(playerTwoConfigPanel, "computerButton");
        assertEquals("Human", humanButton.getText());
        assertEquals("Computer", computerButton.getText());
    }

    @Test
    public void configPanelHasBoardConfigPanel() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        SwingView.ConfigPanel.BoardConfigPanel boardConfigPanel =
                (SwingView.ConfigPanel.BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");
        assertTrue(boardConfigPanel.isVisible());
    }

    @Test
    public void boardConfigPanelHasBoardSizeSpinner() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        SwingView.ConfigPanel.BoardConfigPanel boardConfigPanel =
                (SwingView.ConfigPanel.BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");

        JSpinner boardSizeSpinner =
                (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        assertTrue(boardSizeSpinner.isVisible());
    }


    /*@Test
    public void boardSizeSpinnerHasRoomForTwoDigits() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        SwingView.ConfigPanel.BoardConfigPanel boardConfigPanel =
                (SwingView.ConfigPanel.BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");

        JSpinner boardSizeSpinner =
                (JSpinner)getComponent(boardConfigPanel, "boardSizeSpinner");

        assertEquals(50, boardSizeSpinner.getWidth());
    }*/

    @Test
    public void boardConfigPanelSpinnerStoresSpinnerState() {
        SwingView.ConfigPanel configPanel =
                (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");

        SwingView.ConfigPanel.BoardConfigPanel boardConfigPanel =
                (SwingView.ConfigPanel.BoardConfigPanel)getComponent(configPanel, "boardConfigPanel");

        assertEquals(3, boardConfigPanel.boardSize());
    }

    @Test
    public void messagePanelShouldDisplayMessage() {
        swingView.displayMessage("Shall we play a game?");

        SwingView.MessagePanel messagePanel = (SwingView.MessagePanel)getComponent(swingView.getContentPane(), "messagePanel");
        JLabel message = (JLabel)getComponent(messagePanel, "messagePanelLabel");
        assertEquals("Shall we play a game?", message.getText());
    }

    @Test
    public void messagePanelShouldDisplayBoard() {
        swingView.displayBoard(noWins);

        SwingView.BoardPanel boardPanel = (SwingView.BoardPanel)getComponent(swingView.getContentPane(), "boardPanel");
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
    }

    @Test(expected = GameOverException.class)
    public void swingViewShouldEndGames() throws GameOverException {
        swingView.endGame();
    }

    @Test
    public void swingViewShouldListenForButtonClicks() {
        SwingView.ConfigPanel configPanel = (SwingView.ConfigPanel)getComponent(swingView.getContentPane(), "configPanel");
        SwingView.ConfigPanel.GameActionPanel actionPanel = (SwingView.ConfigPanel.GameActionPanel)getComponent(configPanel, "gameActionPanel");
        JButton button = (JButton)getComponent(actionPanel, "newGameButton");

        newGameWasClicked = false;
        actionPanel.addNewGameListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGameWasClicked = true;
            }
        });

        button.doClick();
        assertTrue(newGameWasClicked);
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
