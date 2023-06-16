package game.GUI.GamePanel;

import game.Cell;
import game.GUI.Tools.ColorsEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.List;

public class SquareCell implements GameBoardCell {
    private final Cell cell;
    private final SquareClickListener.Square square;
    private int state;
    public int index;
    public SquareClickListener squareClickListener;  // Add a field to store the SquareClickListener instance


    public SquareCell(int state, int index, Cell cell) {
        this.cell = cell;
        this.index = index;
        this.state = state;
        square = new SquareClickListener.Square(cell, index);
        squareClickListener = new SquareClickListener(square);
        switch (state) {
            case 0 -> square.setColor(Color.WHITE);
            case 1 -> square.setColor(Color.BLACK);
            case 2 -> square.setColor(ColorsEnum.BUTTON_COLOR_2.getColor());
        }
    }

    @Override
    public Component getComponent() {
        return square;
    }

    // Add a method to get the SquareClickListener instance
    public SquareClickListener getSquareClickListener() {
        return squareClickListener;
    }

    public static class SquareClickListener extends MouseAdapter implements GameBoardCell {
        public final Square square;
        private List<Integer> cellsClickedIndexes;

        public SquareClickListener(Square square, List<Integer> cellsClickedIndexes) {
            this.square = square;
            this.cellsClickedIndexes = cellsClickedIndexes;
        }
        public SquareClickListener(Square square) {
            this.square = square;
        }
        @Override
        public void mousePressed(MouseEvent e) {
            square.changeColorForwards();
            square.cell.updateState();
            SquareCell.SquareClickListener.Square square = (SquareCell.SquareClickListener.Square) e.getSource();
            cellsClickedIndexes.add(square.index);
        }

        @Override
        public Component getComponent() {
            return square;
        }

        static class Square extends JPanel {
            public int index;
            Color color;
            Cell cell;

            public Square(Cell cell, int index) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                this.cell = cell;
                this.index = index;
            }

            public void setColor(Color color) {
                this.color = color;
                setBackground(color);
            }
            public void changeColorBackwards(){
                if (Objects.equals(color, ColorsEnum.BUTTON_COLOR_2.getColor())) {
                    setColor(Color.BLACK);

                } else if (color == Color.BLACK) {
                    setColor(Color.WHITE);
                }else if(color == Color.WHITE){
                    setColor(ColorsEnum.BUTTON_COLOR_2.getColor());
                }
            }
            public void changeColorForwards(){
                if (color == Color.WHITE) {
                    setColor(Color.BLACK);

                } else if (Objects.equals(color, ColorsEnum.BUTTON_COLOR_2.getColor())) {
                    setColor(Color.WHITE);

                } else if (color == Color.BLACK) {
                    setColor(ColorsEnum.BUTTON_COLOR_2.getColor());
                }
            }
        }
    }
}