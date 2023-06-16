package game.GUI.GamePanel;

import game.Cell;
import game.Solver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardGenerator implements GameBoardCell {
    private final JPanel game_board;

//    private MousePressEventLoggingExample mousePressEventLoggingExample;

    public Solver solver;
    public List<Integer> cellsClickedIndexes = new ArrayList<>();
    public List<Integer> stepBackIndexes = new ArrayList<>();
    public List<Integer> stepForwardIndexes = new ArrayList<>();
    public int sizeOfPreviousCellsClickedIndexes = 0;
    private final List<Cell> nurikabeBoard;
    private List<SqrOrButtonCell> sqrOrButtonCells = new ArrayList<>();
    public BoardGenerator(JPanel game_board, List<Cell> nurikabeBoard, int gridSize) {
        this.game_board = game_board;
        this.nurikabeBoard = nurikabeBoard;
        generateVisualBoard(gridSize);
    }

    private void generateVisualBoard(int gridSize) {
        createSquares(gridSize);
    }

    public void performCellStepBack(){
        List<Integer> subtraction = cellsClickedIndexes.subList(sizeOfPreviousCellsClickedIndexes, cellsClickedIndexes.size());
        stepBackIndexes.addAll(subtraction);
        if(stepBackIndexes.size() == 0) return;
        int index = stepBackIndexes.get(stepBackIndexes.size() - 1);
        stepForwardIndexes.add(index);
        stepBackIndexes.remove(stepBackIndexes.size() - 1);
        SquareCell squareCell = sqrOrButtonCells.get(index).squareCell;
        squareCell.getSquareClickListener().square.changeColorBackwards();
        sizeOfPreviousCellsClickedIndexes = cellsClickedIndexes.size();
    }
    public void performCellStepForward(){
        if(stepForwardIndexes.size() == 0) return;
        int index = stepForwardIndexes.get(stepForwardIndexes.size() - 1);
        stepBackIndexes.add(index);
        stepForwardIndexes.remove(stepForwardIndexes.size() - 1);
        SquareCell squareCell = sqrOrButtonCells.get(index).squareCell;
        squareCell.getSquareClickListener().square.changeColorForwards();
    }
    private void createSquares(int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                GameBoardCell gameBoardCell = createGameBoardCell(nurikabeBoard
                        .get(i * gridSize + j), (i * gridSize + j));
                game_board.add(gameBoardCell.getComponent());
            }
        }
        solver = new Solver(this.nurikabeBoard, sqrOrButtonCells);
        getComponent();
    }

    private GameBoardCell createGameBoardCell(Cell cell, int index) {
        GameBoardCell gameBoardCell;
        switch (cell.getState()) {
            case 1 -> {
                SquareCell squareCell = new SquareCell(cell.getState(), index, cell);
                SquareCell.SquareClickListener.Square square = squareCell.getSquareClickListener().square;
                square.addMouseListener(new SquareCell.SquareClickListener(square, cellsClickedIndexes));
                gameBoardCell = squareCell;
                nurikabeBoard.get(index).setState(0);
                sqrOrButtonCells.add(new SqrOrButtonCell(squareCell));
            }
            case 2 -> {
                if (cell.getValue() != null) {
                    ButtonCell buttonCell = new ButtonCell(cell.getValue(), index, cell.getLoc());
                    gameBoardCell = buttonCell;
                    sqrOrButtonCells.add(new SqrOrButtonCell(buttonCell));

                } else {
                    SquareCell squareCell = new SquareCell(cell.getState(), index, cell);
                    SquareCell.SquareClickListener.Square square = squareCell.getSquareClickListener().square;
                    square.addMouseListener(new SquareCell.SquareClickListener(square, cellsClickedIndexes));
                    gameBoardCell = squareCell;
                    nurikabeBoard.get(index).setState(0);
                    sqrOrButtonCells.add(new SqrOrButtonCell(squareCell));
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