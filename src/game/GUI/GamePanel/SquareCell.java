package game.GUI.GamePanel;

import game.GUI.Tools.ColorsEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class SquareCell implements GameBoardCell {
    private static int state;
    private final SquareClickListener.Square square;

    public SquareCell(int state) {
        square = new SquareClickListener.Square();
        this.state = state;
        square.setColor(Color.WHITE);
        square.addMouseListener(new SquareClickListener(square));
    }

    @Override
    public Component getComponent() {
        return square;
    }

    public static class SquareClickListener extends MouseAdapter {
        private final Square square;

        public SquareClickListener(Square square) {
            this.square = square;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (square.color == Color.WHITE) {
                square.setColor(Color.BLACK);
                state = 0;

            } else if (Objects.equals(square.color, ColorsEnum.BUTTON_COLOR_2.getColor())) {
                square.setColor(Color.WHITE);
                state = 1;

            } else if (square.color == Color.BLACK) {
                square.setColor(ColorsEnum.BUTTON_COLOR_2.getColor());
                state = 2;
            }
        }

        static class Square extends JPanel {
            Color color;

            public Square() {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }

            public void setColor(Color color) {
                this.color = color;
                setBackground(color);
            }
        }
    }
}
