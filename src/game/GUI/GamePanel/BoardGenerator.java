package game.GUI.GamePanel;

import game.Cell;

import javax.swing.*;
import java.awt.*;

public class BoardGenerator implements GameBoardCell {
    private final JPanel game_board;
    private final java.util.List<Cell> nurikabeBoardPanel;

    public BoardGenerator(JPanel game_board, java.util.List<Cell> nurikabeBoardPanel) {
        this.game_board = game_board;
        this.nurikabeBoardPanel = nurikabeBoardPanel;

    }

    public void generateVisualBoard(int gridSize) {
        createSquares(game_board, gridSize);
    }

    private void createSquares(JPanel boardPanel, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Cell cell = nurikabeBoardPanel.get(i * gridSize + j);
                GameBoardCell gameBoardCell = createGameBoardCell(cell);
                boardPanel.add(gameBoardCell.getComponent());
            }
        }
    }

    private GameBoardCell createGameBoardCell(Cell cell) {
        GameBoardCell gameBoardCell;
        switch (cell.getState()) {
            case 1 -> gameBoardCell = new SquareCell();
            case 2 -> {
                if (cell.getValue() != null) {
                    gameBoardCell = new ButtonCell(cell.getValue());
                } else {
                    gameBoardCell = new SquareCell();
                }
            }
            default -> throw new IllegalArgumentException("Invalid cell state: " + cell.getState());
        }
        return gameBoardCell;
    }

    @Override
    public Component getComponent() {
        return game_board;
    }
}
