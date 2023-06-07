package game;

import game.GUI.GamePanel.GamePanel;

import javax.swing.*;

public class GamePanelManager {
    private GamePanel gamePanel;

    public GamePanelManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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
