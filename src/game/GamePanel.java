package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class GamePanel extends JPanel {
    private final Main mainInstance;
    private final java.util.List<Cell> nurikabeBoardPanel;
    Board board;
    int gridSize = 6;
    private JLabel timerLabel;


    private long startTime;


    public GamePanel(Main mainInstance, java.util.List<Cell> nurikabeBoardPanel) {
//        this.board = board;
        this.nurikabeBoardPanel = nurikabeBoardPanel;
        JPanel board_panel = new JPanel();
        JPanel buttons_panel = new JPanel();
        setLayout(new BorderLayout());

        createSquares(board_panel);

        this.mainInstance = mainInstance;

        Timer timer = new Timer(1, new TimerListener());
        buttons_panel.setLayout(new FlowLayout());
        buttons_panel.setBackground(Color.BLACK);
        timerLabel = new JLabel("00:00:00:00"); // Assign to the class-level field
        timerLabel.setFont(new Font("Nunito", Font.PLAIN, 88));
        startTime = System.currentTimeMillis();

        ImageIcon stepBackIconOriginal = new ImageIcon("src/gameResources/left-arrow.png");
        Image stepBackIconImage = stepBackIconOriginal.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH); // Specify the desired width and height
        ImageIcon stepBackIcon = new ImageIcon(stepBackIconImage);
        JButton stepBackButton = new JButton(stepBackIcon);
        stepBackButton.setBorderPainted(false); // Remove border painting
        stepBackButton.setContentAreaFilled(false); // Remove default content area fill
        stepBackButton.setFocusPainted(false); // Remove focus painting
        stepBackButton.setBackground(Color.BLACK);

        ImageIcon saveGameIconOriginal = new ImageIcon("src/gameResources/diskette.png"); // Replace with the path to your image
        Image saveGameIconImage = saveGameIconOriginal.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH); // Specify the desired width and height
        ImageIcon saveGameIcon = new ImageIcon(saveGameIconImage);
        JButton saveGameButton = new JButton(saveGameIcon);
        saveGameButton.setBorderPainted(false); // Remove border painting
        saveGameButton.setContentAreaFilled(false); // Remove default content area fill
        saveGameButton.setFocusPainted(false); // Remove focus painting
        saveGameButton.setBackground(Color.BLACK);


        timer.start();

        buttons_panel.add(timerLabel);
        buttons_panel.add(stepBackButton);
        buttons_panel.add(saveGameButton);

        board_panel.setLayout(new GridLayout(gridSize, gridSize));
        add(board_panel, BorderLayout.CENTER);
        add(buttons_panel, BorderLayout.NORTH);

        stepBackButton.addActionListener(e -> {
            timer.stop();
            timerLabel.setText("00:00:00:00");
            mainInstance.showMenuPanel();
        });
        saveGameButton.addActionListener(e -> System.out.println("Saving the game to the file!"));
    }

    public static void main(String[] args) {
    }

    private void createSquares(JPanel board_panel) {
        int gridSize = 6;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Square square = new Square();

                switch (nurikabeBoardPanel.get(i * gridSize + j).getState()) {
                    case 1 -> {
                        square.setColor(Color.WHITE);
                        square.addMouseListener(new SquareClickListener(square));
                        board_panel.add(square);
                    }
                    case 2 -> {
                        if (nurikabeBoardPanel.get(i * gridSize + j).getValue() != null) {
                            JButton button1 = new JButton(nurikabeBoardPanel.get(i * gridSize + j).getValue());

                            button1.setBackground(ColorsEnum.BUTTON_COLOR_2.getColor());
                            button1.setForeground(Color.WHITE);
                            button1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                            board_panel.add(button1);
                        } else {

                            square.setColor(Color.WHITE);
                            square.addMouseListener(new SquareClickListener(square));
                            board_panel.add(square);
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

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Calculate the elapsed time
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;

            // Format the elapsed time as HH:mm:ss
            long hours = elapsedTime / (1000 * 60 * 60);
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            long seconds = (elapsedTime / 1000) % 60;
            long miliseconds = (elapsedTime) % 60;
            String formattedTime = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, miliseconds);

            // Update the timer label
            timerLabel.setText(formattedTime);
        }
    }
}

