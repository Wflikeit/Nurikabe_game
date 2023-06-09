package game;

import game.GUI.DecisionPanels.LevelChoosing;
import game.GUI.DecisionPanels.Menu;
import game.GUI.DecisionPanels.PausePanel;
import game.GUI.GamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final Main app;
    private int size;
    private String level;
    private final GamePanel gamePanel; // Store a reference to the current GamePanel
    private final GamePanelManager gamePanelManager; // Store a reference to the current GamePanel
    private Board board; // Store a reference to the current GamePanel
    private final LevelChoosing levelChoosing;

    public GameManager(Main app) {
        this.app = app;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        game.GUI.DecisionPanels.Menu menuPanel = new Menu(this);
        menuPanel.setLayout(cardLayout);
        levelChoosing = new LevelChoosing(this);
        PausePanel pausePanel = new PausePanel(this);

        gamePanel = new GamePanel(this);
        gamePanelManager = new GamePanelManager(gamePanel);
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(levelChoosing, "levelChoosing");
        cardPanel.add(gamePanel, "gamePanel");
        cardPanel.add(pausePanel, "pausePanel");
        app.add(cardPanel);
        gamePanel.timerListener.startTimer();
//        JComboBox
    }

    public void showGamePanel() {
        size = levelChoosing.sizeJComboBox.getSizeOfBoard();
        level = levelChoosing.getLevel();
        board = new Board(size, level); // Create an instance of the Board class
        board.fillBoard();
        gamePanelManager.setUpGameBoard(board.nurikabeBoardPanel, board.size);
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
    public void backToGame(){
        board.print();
        gamePanel.timerListener.startTimer();
        cardLayout.show(cardPanel, "gamePanel");


    }
}
