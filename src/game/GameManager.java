package game;

import game.GUI.DecisionPanels.LevelChoosing;
import game.GUI.DecisionPanels.Menu;
import game.GUI.DecisionPanels.PausePanel;
import game.GUI.GamePanel.GamePanel;
import game.GUI.Tools.ColorsEnum;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final Main app;
    private final GamePanel gamePanel; // Store a reference to the current GamePanel
    private final GamePanelManager gamePanelManager; // Store a reference to the current GamePanel
    private final LevelChoosing levelChoosing;
    private final BoardFilePrinterForHuman boardFilePrinterForHuman;
    private Board board; // Store a reference to the current GamePanel
    private int fileForHumanCounter = 0;


    public GameManager(Main app) {
        this.app = app;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        game.GUI.DecisionPanels.Menu menuPanel = new Menu(this);
        menuPanel.setLayout(cardLayout);
        levelChoosing = new LevelChoosing(this);
        PausePanel pausePanel = new PausePanel(this);
        boardFilePrinterForHuman = new BoardFilePrinterForHuman();
        gamePanel = new GamePanel(this);
        gamePanelManager = new GamePanelManager(gamePanel);
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(levelChoosing, "levelChoosing");
        cardPanel.add(gamePanel, "gamePanel");
        cardPanel.add(pausePanel, "pausePanel");
        app.add(cardPanel);
        gamePanel.timerListener.startTimer();
    }

    public void showGamePanel() {
        int size = levelChoosing.sizeJComboBox.getSizeOfBoard();
        String level = levelChoosing.getLevel();
        board = new Board(size, level); // Create an instance of the Board class
        board.fillBoard();
//        Solver solver = new Solver(board.getNurikabeBoardPanel());
        gamePanelManager.setUpGameBoard(board.getNurikabeBoardPanel(), board.size);
        // Populate the board and retrieve the nurikabeBoardPanel list
        board.print();


        startGame(); // Access the stored GamePanel instance
        cardLayout.show(cardPanel, "gamePanel");
        app.pack();
    }

    public void showMenuPanel() {

        cardLayout.show(cardPanel, "menu");
        app.pack();
    }

    public void showLevelChoosing() {
        cardLayout.show(cardPanel, "levelChoosing");
        app.pack();
    }

    public void showPausePanel() {
        cardLayout.show(cardPanel, "pausePanel");
        gamePanel.timerListener.stopTimer();
        app.pack();
    }

    public void startGame() {
        gamePanel.timerListener.resetTimer(); // Reset the timer in the GamePanel
        gamePanel.timerListener.startTimer(); // Start the timer from the initial value
        app.pack();
    }

    public void backToGame() {
        board.print();
        gamePanel.timerListener.startTimer();
        cardLayout.show(cardPanel, "gamePanel");
    }

    public void saveBoardForHuman() {
        fileForHumanCounter += 1;
        String filenameForHuman = "board_for_human" + fileForHumanCounter;
        boardFilePrinterForHuman.setBoard(board.getNurikabeBoardPanel());
        boardFilePrinterForHuman.saveBoardToFile(filenameForHuman);
        JOptionPane optionPane = new JOptionPane("No records of game found", JOptionPane.INFORMATION_MESSAGE);
        optionPane.setBorder(null);
        UIManager.put("Button.background", ColorsEnum.BUTTON_COLOR.getColor());
        JOptionPane.showMessageDialog(optionPane, "file saved under the name: " + filenameForHuman,
                "Select Size", JOptionPane.PLAIN_MESSAGE);

    }
}
