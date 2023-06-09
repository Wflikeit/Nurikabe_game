package game;

import game.GUI.GamePanel.BoardGenerator;
import game.GUI.GamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GamePanelManager {
    private final GamePanel gamePanel;

    public GamePanelManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setUpGameBoard( java.util.List<Cell> nurikabeBoardPanel, int size){
        JPanel boardPanel = new JPanel();
        BoardGenerator boardGenerator = new BoardGenerator(boardPanel, nurikabeBoardPanel);
        boardGenerator.generateVisualBoard(size);
        boardPanel.setLayout(new GridLayout(size, size));
        gamePanel.add(boardPanel, BorderLayout.CENTER);

    }
    public void setupButtonListeners() {
        JButton pauseButton = gamePanel.getPauseGameButton();
        JButton saveButton = gamePanel.getSaveGameButton();
        JButton stepBackButton = gamePanel.getStepBackButton();
        JButton checkButton = gamePanel.getCheckGameButton();

        pauseButton.addActionListener(e -> handlePauseGame());

        saveButton.addActionListener(e -> handleSaveGame());

        stepBackButton.addActionListener(e -> handleStepBack());

        checkButton.addActionListener(e -> handleCheckGame());
    }

    private void handlePauseGame() {
        // Logic for pausing the game
        gamePanel.gameManager.showPausePanel();


    }

    private void handleSaveGame() {
        // Logic for saving the game
        System.out.println("Saving the game to the file!");
    }

    private void handleStepBack() {
        // Logic for stepping back in the game
        gamePanel.gameManager.showMenuPanel();
    }

    public void handleCheckGame() {
        // Logic for checking the game state
    }

}
