package game.GUI.GamePanel;

import game.GUI.Visuals.ColorsEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class SquareClickListener extends MouseAdapter {
    private final Square square;

    public SquareClickListener(game.GUI.GamePanel.SquareClickListener.Square square) {
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
