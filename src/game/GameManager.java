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
    private final GamePanel gamePanel; // Store a reference to the current GamePanel

    public GameManager(Main app) {
        this.app = app;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        game.GUI.DecisionPanels.Menu menuPanel = new Menu(this);
        menuPanel.setLayout(cardLayout);
        LevelChoosing levelChoosing = new LevelChoosing(this);
        PausePanel pausePanel = new PausePanel(this);
        Board board = new Board(12, "easy"); // Create an instance of the Board class
        // Populate the board and retrieve the nurikabeBoardPanel list
        board.createBoard();
        gamePanel = new GamePanel(this, board.nurikabeBoardPanel, board.getSize());
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(levelChoosing, "levelChoosing");
        cardPanel.add(gamePanel, "gamePanel");
        cardPanel.add(pausePanel, "pausePanel");
        app.add(cardPanel);
        board.print();
        gamePanel.timerListener.startTimer();
//        JComboBox
    }

    public void showGamePanel() {
        gamePanel.timerListener.startTimer(); // Restart the timer
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
        app.pack();
    }

    public void startGame() {
        gamePanel.timerListener.resetTimer(); // Reset the timer in the GamePanel
        gamePanel.timerListener.startTimer(); // Start the timer from the initial value
        app.pack();
    }
}
