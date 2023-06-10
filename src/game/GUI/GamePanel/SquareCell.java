package game.GUI.GamePanel;

import game.Cell;
import game.GUI.Tools.ColorsEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class SquareCell implements GameBoardCell {
    private final Cell cell;
    private final SquareClickListener.Square square;
    private int state;
    private int index;
    private SquareClickListener squareClickListener;  // Add a field to store the SquareClickListener instance


    public SquareCell(int state, int index, Cell cell) {
        this.cell = cell;
        this.index = index;
        this.state = state;
        square = new SquareClickListener.Square(cell);
        squareClickListener = new SquareClickListener(square);


        square.setColor(Color.WHITE);
        square.addMouseListener(new SquareClickListener(square));
    }

    @Override
    public Component getComponent() {
        return square;
    }

//    public int getUpdatedState(int newState) {
//        state = newState;
//        square.cell.setState(newState);
//        return cell.getState();
//    }
    // Add a method to get the SquareClickListener instance
    public SquareClickListener getSquareClickListener() {
        return squareClickListener;
    }

    public static class SquareClickListener extends MouseAdapter implements GameBoardCell {
        private final Square square;

        public SquareClickListener(Square square) {
            this.square = square;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (square.color == Color.WHITE) {
                square.setColor(Color.BLACK);

            } else if (Objects.equals(square.color, ColorsEnum.BUTTON_COLOR_2.getColor())) {
                square.setColor(Color.WHITE);

            } else if (square.color == Color.BLACK) {
                square.setColor(ColorsEnum.BUTTON_COLOR_2.getColor());
            }
            square.cell.updateState();
        }

        @Override
        public Component getComponent() {
            return square;
        }

        static class Square extends JPanel {
            public int state;
            Color color;
            Cell cell;

            public Square(Cell cell) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                this.cell = cell;
            }

            public void setColor(Color color) {
                this.color = color;
                setBackground(color);
            }
        }
    }
}