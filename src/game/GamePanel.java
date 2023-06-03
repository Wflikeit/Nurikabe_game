    package game;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.util.List;

    public class GamePanel extends JPanel {
        Board board;
        private final Main mainInstance;
        int gridSize = 6;



        public GamePanel(Main mainInstance, Board board) {
            this.board = board;
            board.setLevel("hard");
            board.createBoard();
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
                        case 0: {
                            square.setColor(Color.GRAY);
                            break;
                        }

                        case 1: {
                            square.setColor(Color.WHITE);
                            break;
                        }
                        case 2: {
                            square.setColor(Color.WHITE);
                            if (board.nurikabeBoardPanel.get(i * gridSize + j).getValue() != null){
                                square.setColor(ColorsEnum.BUTTON_COLOR_2.getColor());
                                JButton button1 = new JButton(board.nurikabeBoardPanel.get(i * gridSize + j).getValue());
                                square.add(button1);
                            }
                            break;
                        }
//                        case 3:{
//                            square.setColor(ColorsEnum.BUTTON_COLOR_2.getColor());
//                            JButton button1 = new JButton(board.numbList.get(1).getValue());
//                            square.add(button1);
//                            break;
//                        }

                    }
                    square.addMouseListener(new SquareClickListener(square));
                    add(square);
                }
            }
        }


        private static class Square extends JPanel {
            private Color color;

            public Square() {
                color = Color.WHITE;
                setBackground(color);
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
            public void mouseClicked(MouseEvent e) {
                if (square.color == Color.WHITE) {
                    square.setColor(Color.BLACK);
                } else {
                    square.setColor(Color.WHITE);
                }
            }
        }


    }




