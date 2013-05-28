package com.cmendenhall;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.TicTacToeSymbols.*;

public class SwingView extends JFrame implements View {
    static final int WIDTH = 350;
    static final int HEIGHT = 700;
    public BoardPanel boardPanel = new BoardPanel();
    private MessagePanel messagePanel = new MessagePanel();
    private ConfigPanel configPanel = new ConfigPanel();

    public SwingView() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        add(boardPanel);
        add(messagePanel);
        add(configPanel);
    }

    public void displayBoard(Board board) {
        boardPanel.loadBoard(board);
    }

    public void displayMessage(String message) {
        JLabel messageLabel = messagePanel.getLabel();
        messageLabel.setText(message);
    }

    public String getInput() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void endGame() throws GameOverException {
        throw new GameOverException("Game over.");
    }

    public class MessagePanel extends JPanel {
        private JLabel label;

        public MessagePanel() {
            setName("messagePanel");

            label = new JLabel("Welcome to Tic-Tac-Toe");
            label.setName("messagePanelLabel");

            add(label);
        }

        public JLabel getLabel() {
            return label;
        }

    }

    public class BoardPanel extends JPanel {
        private JTable boardTable;

        public BoardPanel() {
            setName("boardPanel");

            Dimension defaultSize = getSize();
            defaultSize.height = 150;
            setPreferredSize(defaultSize);

            boardTable = new JTable();
            loadBoard(new GameBoard());
            boardTable.setName("boardTable");
            boardTable.setShowVerticalLines(true);
            boardTable.setShowHorizontalLines(true);
            boardTable.setGridColor(new Color(0, 0, 0));
            boardTable.setColumnSelectionAllowed(false);
            boardTable.setRowSelectionAllowed(false);

            int numberOfColumns = boardTable.getColumnModel().getColumnCount();
            TableColumnModel columnModel = boardTable.getColumnModel();

            for (int column=0; column < numberOfColumns; column++) {
                TableColumn currentColumn = columnModel.getColumn(column);
                currentColumn.setPreferredWidth(50);
            }

            boardTable.setRowHeight(50);

            add(boardTable);
        }

        public void loadBoard(Board board) {
            List<Row> rows = board.getRows();
            DefaultTableModel tableData = new DefaultTableModel(rows.size(), rows.size());

            for (int row=0; row < rows.size(); row++) {
                Row boardRow = rows.get(row);
                int[] squares = boardRow.getSquares();
                for (int col=0; col < squares.length; col++) {
                    String square = boardRow.intToSymbol(squares[col]);
                    tableData.setValueAt(square, row, col);
                }
            }

            boardTable.setModel(tableData);

        }

        public void addBoardClickListener(MouseListener listener) {
            boardTable.addMouseListener(listener);
        }

    }

    public class ConfigPanel extends JPanel {
        private BoardConfigPanel boardConfigPanel;
        private PlayerConfigPanel playerOneConfigPanel;
        private PlayerConfigPanel playerTwoConfigPanel;

        public ConfigPanel() {
            setName("configPanel");

            Dimension defaultSize = getSize();
            defaultSize.width = 200;
            setPreferredSize(defaultSize);

            boardConfigPanel = new BoardConfigPanel();
            playerOneConfigPanel = new PlayerConfigPanel("Player One", "playerOneConfigPanel");
            playerTwoConfigPanel = new PlayerConfigPanel("Player Two", "playerTwoConfigPanel");

            add(new GameActionPanel());
            add(boardConfigPanel);
            add(playerOneConfigPanel);
            add(playerTwoConfigPanel);
        }

        public HashMap getConfig() {
            HashMap<String, Integer> configMap = new HashMap<String, Integer>();

            Integer boardSize = boardConfigPanel.boardSize();
            Integer playerOneHuman = (playerOneConfigPanel.humanSelected()) ? 1 : 0;
            Integer playerTwoHuman = (playerTwoConfigPanel.humanSelected()) ? 1 : 0;

            configMap.put("boardSize", boardSize);
            configMap.put("playerOneHuman", playerOneHuman);
            configMap.put("playerTwoHuman", playerTwoHuman);

            return configMap;
        }

        public class GameActionPanel extends JPanel {
            private JButton newGameButton;

            public GameActionPanel() {
                setName("gameActionPanel");

                newGameButton = new JButton("New game");
                newGameButton.setName("newGameButton");
                add(newGameButton);
            }

            public void addNewGameListener(ActionListener newGameListener) {
                newGameButton.addActionListener(newGameListener);
            }

        }

        public class PlayerConfigPanel extends JPanel {
            private ButtonGroup playerButtons;
            private JRadioButton humanButton;
            private JRadioButton computerButton;

            public PlayerConfigPanel(String label, String name) {
                setName(name);

                playerButtons = new ButtonGroup();

                JLabel playerLabel = new JLabel(label);
                playerLabel.setName("playerLabel");

                humanButton = new JRadioButton("Human", true);
                humanButton.setName("humanButton");

                computerButton = new JRadioButton("Computer", false);
                computerButton.setName("computerButton");

                playerButtons.add(humanButton);
                playerButtons.add(computerButton);

                add(playerLabel);
                add(humanButton);
                add(computerButton);
            }

            public boolean humanSelected() {
                ButtonModel selectedModel = playerButtons.getSelection();
                ButtonModel humanModel = humanButton.getModel();
                return selectedModel.equals(humanModel);
            }
        }

        public class BoardConfigPanel extends JPanel {
            private SpinnerNumberModel boardSizeSpinnerModel;

            public BoardConfigPanel() {
                setName("boardConfigPanel");

                boardSizeSpinnerModel = new SpinnerNumberModel(3, 3, 10, 1);

                JSpinner boardSizeSpinner = new JSpinner(boardSizeSpinnerModel);
                boardSizeSpinner.setName("boardSizeSpinner");

                Dimension d = boardSizeSpinner.getPreferredSize();
                d.width = 50;
                boardSizeSpinner.setPreferredSize(d);
                boardSizeSpinner.setValue(3);

                JLabel spinnerLabel = new JLabel("Board size:");
                spinnerLabel.setLabelFor(boardSizeSpinner);

                add(spinnerLabel);
                add(boardSizeSpinner);
            }

            public int boardSize() {
                return (Integer)boardSizeSpinnerModel.getValue();
            }


        }



    }
}
