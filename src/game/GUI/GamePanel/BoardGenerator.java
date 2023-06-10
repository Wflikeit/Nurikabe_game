package game.GUI.GamePanel;

import game.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoardGenerator implements GameBoardCell {
    private final JPanel game_board;
    private final java.util.List<Cell> nurikabeBoard;
    public GameBoardCell gameBoardCell;

    public BoardGenerator(JPanel game_board, List<Cell> nurikabeBoard, int gridSize) {
        this.game_board = game_board;
        this.nurikabeBoard = nurikabeBoard;
        generateVisualBoard(gridSize);

    }

    private void generateVisualBoard(int gridSize) {
        createSquares(game_board, gridSize);
    }

    private void createSquares(JPanel boardPanel, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gameBoardCell = createGameBoardCell(nurikabeBoard
                        .get(i * gridSize + j), (i * gridSize + j));
                boardPanel.add(gameBoardCell.getComponent());
            }
        }
        getComponent();
    }

    private GameBoardCell createGameBoardCell(Cell cell, int index) {
        GameBoardCell gameBoardCell;
        switch (cell.getState()) {
            case 1 -> {
                gameBoardCell = new SquareCell(cell.getState(), index, cell);
                nurikabeBoard.get(index).setState(0);
            }
            case 2 -> {
                if (cell.getValue() != null) {
                    gameBoardCell = new ButtonCell(cell.getValue());
                } else {
                    gameBoardCell = new SquareCell(cell.getState(), index, cell);
                    nurikabeBoard.get(index).setState(0);
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