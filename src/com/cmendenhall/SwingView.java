package com.cmendenhall;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SwingView extends JFrame implements View {
    static final int WIDTH = 350;
    static final int HEIGHT = 400;

    public BoardPanel boardPanel = new BoardPanel();
    private MessagePanel messagePanel = new MessagePanel();
    private ConfigPanel configPanel = new ConfigPanel();
    private Queue<String> inputQ = new LinkedBlockingQueue<String>();

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
        if (message.indexOf("move") != -1) {
            int endStart = message.indexOf("player") + 6;
            String ending = message.substring(endStart);
            message = "Your move, player" + ending;
        } else if (ignoreThese.contains(message)) {
            return;
        }
        JLabel messageLabel = messagePanel.getLabel();
        messageLabel.setText(message);
    }

    private void enqueueInput(String input) {
        inputQ.add(input);
    }

    private void clearInput() {
        inputQ.clear();
    }

    public String getInput() {
        try {
            return inputQ.remove();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }

    public void endGame() throws GameOverException {
        throw new GameOverException("Game over.");
    }

    public void reload() {
        clearInput();
        configPanel.enableNewGameButton();
    }

    public void resizeWindow(int width, int height) {
        setSize(width, height);
    }

    public class MessagePanel extends JPanel {
        private JLabel label;

        public MessagePanel() {
            setName("messagePanel");

            label = new JLabel("Welcome to Tic-Tac-Toe");
            label.setName("messagePanelLabel");

            Dimension defaultSize = getSize();
            defaultSize.height = 100;
            defaultSize.width = 350;
            setPreferredSize(defaultSize);
            setMaximumSize(defaultSize);

            add(label);
        }

        public JLabel getLabel() {
            return label;
        }

    }

    public class BoardPanel extends JPanel {
        private JTable boardTable;
        private DefaultTableModel boardData;
        private DefaultTableCellRenderer centeredRenderer;

        public BoardPanel() {
            setName("boardPanel");

            Dimension maxSize = new Dimension(600, 600);
            setMaximumSize(maxSize);

            centeredRenderer = new DefaultTableCellRenderer();
            centeredRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

            boardTable = new JTable();

            boardTable.setName("boardTable");
            boardTable.setShowVerticalLines(true);
            boardTable.setShowHorizontalLines(true);
            boardTable.setGridColor(new Color(0, 0, 0));
            boardTable.setColumnSelectionAllowed(false);
            boardTable.setRowSelectionAllowed(false);
            boardTable.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
            boardTable.setAutoCreateColumnsFromModel(true);

            boardData = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            boardTable.setModel(boardData);

            loadBoard(new GameBoard());
            formatBoard();

            add(boardTable);
            addBoardClickListener();
        }

        private void formatBoard() {

            TableColumnModel columnModel = boardTable.getColumnModel();
            int numberOfColumns = columnModel.getColumnCount();

            boardTable.setRowHeight(50);

            for (int column=0; column < numberOfColumns; column++) {
                TableColumn currentColumn = columnModel.getColumn(column);
                currentColumn.setPreferredWidth(50);
                currentColumn.setCellRenderer(centeredRenderer);
            }

        }

        public void loadBoard(Board board) {
            setVisible(false);
            List<Row> rows = board.getRows();
            int boardSize = rows.size();

            boardData.setColumnCount(boardSize);
            boardData.setRowCount(boardSize);

            for (int row=0; row < rows.size(); row++) {
                Row boardRow = rows.get(row);
                int[] squares = boardRow.getSquares();
                for (int col=0; col < squares.length; col++) {
                    String square = boardRow.intToSymbol(squares[col]);
                    boardData.setValueAt(square, row, col);
                }
            }

            formatBoard();
            setVisible(true);

        }

        public void addBoardClickListener() {
            MouseAdapter boardClickListener = new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    Integer row = boardTable.rowAtPoint(event.getPoint());
                    Integer column = boardTable.columnAtPoint(event.getPoint());
                    if (row >= 0 && column >= 0) {
                        enqueueInput(row.toString() + "," + column.toString());
                    }
                }

            };
            boardTable.addMouseListener(boardClickListener);
        }

    }

    public class ConfigPanel extends JPanel {
        private GameActionPanel gameActionPanel;
        private BoardConfigPanel boardConfigPanel;
        private PlayerConfigPanel playerOneConfigPanel;
        private PlayerConfigPanel playerTwoConfigPanel;

        public ConfigPanel() {
            setName("configPanel");

            Dimension defaultSize = getSize();
            defaultSize.width = 350;
            defaultSize.height = 200;
            setPreferredSize(defaultSize);
            setMaximumSize(defaultSize);

            gameActionPanel = new GameActionPanel();
            boardConfigPanel = new BoardConfigPanel();
            playerOneConfigPanel = new PlayerConfigPanel("Player X", "playerOneConfigPanel");
            playerTwoConfigPanel = new PlayerConfigPanel("Player O", "playerTwoConfigPanel");

            add(gameActionPanel);
            add(boardConfigPanel);
            add(playerOneConfigPanel);
            add(playerTwoConfigPanel);
        }

        public void enqueueConfig() {
            enqueueInput(boardConfigPanel.boardSize());
            enqueueInput((playerOneConfigPanel.humanSelected()) ? "h" : "c");
            enqueueInput((playerTwoConfigPanel.humanSelected()) ? "h" : "c");
        }

        public void enableNewGameButton() {
            gameActionPanel.enableNewGameButton();
        }

        public class GameActionPanel extends JPanel {
            private JButton newGameButton;

            public GameActionPanel() {
                setName("gameActionPanel");

                newGameButton = new JButton("New game");
                newGameButton.setName("newGameButton");
                add(newGameButton);
                addNewGameListener();
            }

            public void enableNewGameButton() {
                newGameButton.setEnabled(true);
            }

            public void addNewGameListener() {
                ActionListener newGameListener = new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        enqueueConfig();
                        int boardSize = Integer.parseInt(boardConfigPanel.boardSize());
                        int newWidth = Math.max((boardSize + 1) * 50, 350);
                        int newHeight = Math.max((boardSize + 1) * 50 + 200, 400);
                        resizeWindow(newWidth, newHeight);
                        newGameButton.setEnabled(false);
                    }
                };
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

            public String boardSize() {
                return boardSizeSpinnerModel.getValue().toString();
            }


        }

    }
}
