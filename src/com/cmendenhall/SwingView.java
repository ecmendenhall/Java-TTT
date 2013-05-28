package com.cmendenhall;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static com.cmendenhall.TicTacToeSymbols.*;

public class SwingView extends JFrame implements View {
    static final int WIDTH = 350;
    static final int HEIGHT = 700;
    public BoardPanel boardPanel = new BoardPanel();
    private MessagePanel messagePanel = new MessagePanel();
    private ConfigPanel configPanel = new ConfigPanel();
    private Queue<String> inputQ = new LinkedBlockingQueue<String>();

    private String welcome;
    private String divider;
    private String yourMove;
    private String yourMoveThreeSquares;
    private String playAgain;
    private String gameOverDraw;
    private String gameOverWin;
    private String xWins;
    private String oWins;
    private String choosePlayerOne;
    private String choosePlayerTwo;
    private String boardSize;

    private Set<String> ignoreThese = new HashSet<String>();

    public SwingView() {
        loadViewStrings();
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

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

        welcome = viewStrings.getProperty("welcome");
        divider = viewStrings.getProperty("divider");
        yourMove = viewStrings.getProperty("yourmove");
        yourMoveThreeSquares = viewStrings.getProperty("yourmovethreesquares");
        playAgain = viewStrings.getProperty("playagain");
        gameOverDraw = viewStrings.getProperty("gameoverdraw");
        gameOverWin = viewStrings.getProperty("gameoverwin");
        xWins = viewStrings.getProperty("xwins");
        oWins = viewStrings.getProperty("owins");
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
            boardTable.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));

            formatBoard();

            add(boardTable);
            addBoardClickListener();
        }

        private void formatBoard() {
            DefaultTableCellRenderer centeredRenderer = new DefaultTableCellRenderer();
            centeredRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);


            int numberOfColumns = boardTable.getColumnModel().getColumnCount();
            TableColumnModel columnModel = boardTable.getColumnModel();

            for (int column=0; column < numberOfColumns; column++) {
                TableColumn currentColumn = columnModel.getColumn(column);
                currentColumn.setPreferredWidth(50);
                currentColumn.setCellRenderer(centeredRenderer);
            }

            boardTable.setRowHeight(50);
        }

        public void loadBoard(Board board) {
            List<Row> rows = board.getRows();
            DefaultTableModel tableData = new DefaultTableModel(rows.size(), rows.size()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for (int row=0; row < rows.size(); row++) {
                Row boardRow = rows.get(row);
                int[] squares = boardRow.getSquares();
                for (int col=0; col < squares.length; col++) {
                    String square = boardRow.intToSymbol(squares[col]);
                    tableData.setValueAt(square, row, col);
                }
            }

            boardTable.setModel(tableData);
            formatBoard();

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
            defaultSize.width = 200;
            setPreferredSize(defaultSize);

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
