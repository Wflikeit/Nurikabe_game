package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class GamePanel extends JPanel {
    private final Main mainInstance;
    Board board;
    int gridSize = 6;


    public GamePanel(Main mainInstance, Board board) {
        this.board = board;
        board.setLevel("hard");
        board.createBoard();
        Container mainContainer = new Container();
        setLayout(new GridLayout(gridSize, gridSize));

        createSquares();

        this.mainInstance = mainInstance;

    }

    public static void main(String[] args) {
    }

    private void createSquares() {
        int gridSize = 6;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Square square = new Square();

                switch (board.nurikabeBoardPanel.get(i * gridSize + j).getState()) {
                    case 1 -> {
                        square.setColor(Color.WHITE);
                        square.addMouseListener(new SquareClickListener(square));
                        add(square);
                    }
                    case 2 -> {
                        if (board.nurikabeBoardPanel.get(i * gridSize + j).getValue() != null) {
                            JButton button1 = new JButton(board.nurikabeBoardPanel.get(i * gridSize + j).getValue());

                            button1.setBackground(ColorsEnum.BUTTON_COLOR_2.getColor());
                            button1.setForeground(Color.WHITE);
                            button1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                            add(button1);
                        } else {

                            square.setColor(Color.WHITE);
                            square.addMouseListener(new SquareClickListener(square));
                            add(square);
                        }
                    }
                }
            }
        }
    }


    private static class Square extends JPanel {
        private Color color;

        public Square() {
            color = Color.WHITE;
            setBackground(color);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }

        public void setColor(Color color) {
            this.color = color;
            setBackground(color);
        }
    }

    private static class SquareClickListener extends MouseAdapter {
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
        }
    }
}

