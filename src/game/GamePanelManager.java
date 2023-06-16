package game;

import game.GUI.GamePanel.BoardGenerator;
import game.GUI.GamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanelManager {
    private final GamePanel gamePanel;
    private  BoardGenerator boardGenerator;

    public GamePanelManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setupButtonListeners();
    }

    public void setUpGameBoard( java.util.List<Cell> nurikabeBoardPanel, int size, boolean load){
        if (gamePanel.getComponentCount() == 2){
            gamePanel.remove(1);
        }
        JPanel boardPanel = new JPanel();


        boardGenerator = new BoardGenerator(boardPanel, nurikabeBoardPanel, size, load);
        boardPanel.setLayout(new GridLayout(size, size));
        gamePanel.add(boardPanel, BorderLayout.CENTER);

    }
    public void setupButtonListeners() {
        JButton pauseButton = gamePanel.getPauseGameButton();
        JButton saveButton = gamePanel.getSaveGameButton();
        JButton stepBackButton = gamePanel.getStepBackButton();
        JButton stepForwardButton = gamePanel.getStepForwardButton();
        JButton checkButton = gamePanel.getCheckGameButton();

        pauseButton.addActionListener(e -> handlePauseGame());

        saveButton.addActionListener(e -> {
            try {
                handleSaveGame();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to save the game");
            }
        });

        stepBackButton.addActionListener(e -> handleStepBack());

        stepForwardButton.addActionListener(e -> handleStepForward());

        checkButton.addActionListener(e -> handleCheckGame());
    }

    private void handlePauseGame() {
        // Logic for pausing the game
        gamePanel.gameManager.showPausePanel();
    }

    private void handleSaveGame() throws IOException {
        // Logic for saving the game
        gamePanel.gameManager.saveGame();
    }

    private void handleStepBack() {
        // Logic for stepping back in the game
        boardGenerator.performCellStepBack();
    }

    private void handleStepForward() {
        // Logic for stepping forward in the game
        boardGenerator.performCellStepForward();
    }
    public void handleCheckGame() {
        // Logic for checking the game state
        gamePanel.gameManager.checkGame();
    }

}
