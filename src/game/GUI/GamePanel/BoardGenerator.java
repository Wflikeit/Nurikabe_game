package game.GUI.GamePanel;

import game.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardGenerator implements GameBoardCell {
    private final JPanel game_board;

//    private MousePressEventLoggingExample mousePressEventLoggingExample;
    public List<Integer> cellsClickedIndexes = new ArrayList<>();
    public List<Integer> stepBackIndexes = new ArrayList<>();
    public List<Integer> stepForwardIndexes = new ArrayList<>();
    public int sizeOfPreviousCellsClickedIndexes = 0;
    private final java.util.List<Cell> nurikabeBoard;
    private java.util.List<SquareCell> squareCells = new ArrayList<>();

    public SquareCell squareCell;

    public GameBoardCell gameBoardCell;
    boolean load;
    public BoardGenerator(JPanel game_board, List<Cell> nurikabeBoard, int gridSize, boolean load) {
        this.game_board = game_board;
        this.nurikabeBoard = nurikabeBoard;
        this.load = load;

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
        squareCells.get(index).getSquareClickListener().square.changeColorBackwards();
        squareCells.get(index).getSquareClickListener().square.cell.takeBack();
        sizeOfPreviousCellsClickedIndexes = cellsClickedIndexes.size();
    }
    public void performCellStepForward(){
        if(stepForwardIndexes.size() == 0) return;
        int index = stepForwardIndexes.get(stepForwardIndexes.size() - 1);
        stepBackIndexes.add(index);
        stepForwardIndexes.remove(stepForwardIndexes.size() - 1);
        squareCells.get(index).getSquareClickListener().square.changeColorForwards();
        squareCells.get(index).getSquareClickListener().square.cell.updateState();
    }
    private void createSquares(int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gameBoardCell = createGameBoardCell(nurikabeBoard
                        .get(i * gridSize + j), (i * gridSize + j),load);
                game_board.add(gameBoardCell.getComponent());
            }
        }
        getComponent();
    }

    private GameBoardCell createGameBoardCell(Cell cell, int index, boolean load) {
        GameBoardCell gameBoardCell;
        switch (cell.getState()) {
            case 0,1 -> {
                if(!load) nurikabeBoard.get(index).setState(0);
                squareCell = new SquareCell(cell.getState(), index, cell);
                SquareCell.SquareClickListener.Square square = squareCell.getSquareClickListener().square;
                square.addMouseListener(new SquareCell.SquareClickListener(square, cellsClickedIndexes));
                gameBoardCell = squareCell;
                squareCells.add(squareCell);
            }
            case 2 -> {
                if (cell.getValue() != null) {
                    gameBoardCell = new ButtonCell(cell.getValue());
                    squareCell = new SquareCell(0, 0, new Cell(new Point(-1,-1),0));
                    squareCells.add(squareCell);
                } else {
                    if(!load) nurikabeBoard.get(index).setState(0);
                    squareCell = new SquareCell(cell.getState(), index, cell);
                    SquareCell.SquareClickListener.Square square = squareCell.getSquareClickListener().square;
                    square.addMouseListener(new SquareCell.SquareClickListener(square, cellsClickedIndexes));
                    gameBoardCell = squareCell;
                    squareCells.add(squareCell);
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