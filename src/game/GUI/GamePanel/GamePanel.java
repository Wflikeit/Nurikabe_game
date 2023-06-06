package game.GUI.GamePanel;

import game.Cell;
import game.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final Main mainInstance;
    private final java.util.List<Cell> nurikabeBoardPanel;
    int gridSize = 6;
    public final JLabel timerLabel;
    public Boolean timerFlag;
    public Timer timer;
    public TimerListener timerListener;




    public GamePanel(Main mainInstance, java.util.List<Cell> nurikabeBoardPanel) {
//        this.board = board;
        this.nurikabeBoardPanel = nurikabeBoardPanel;
        JPanel board_panel = new JPanel();
        JPanel buttons_panel = new JPanel();
        setLayout(new BorderLayout());

        createSquares(board_panel);

        timerListener = new TimerListener(); // Initialize the timerListener field
        this.mainInstance = mainInstance;

        timer = new Timer(1, timerListener);
        timerFlag = true;
        buttons_panel.setLayout(new FlowLayout());
        buttons_panel.setBackground(Color.BLACK);
        timerLabel = new JLabel("00:00:00:00"); // Assign to the class-level field
        timerLabel.setFont(new Font("Nunito", Font.PLAIN, 88));
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



        buttons_panel.add(timerLabel);
        buttons_panel.add(stepBackButton);
        buttons_panel.add(saveGameButton);

        board_panel.setLayout(new GridLayout(gridSize, gridSize));
        add(board_panel, BorderLayout.CENTER);
        add(buttons_panel, BorderLayout.NORTH);

        stepBackButton.addActionListener(e -> mainInstance.showMenuPanel());
        saveGameButton.addActionListener(e -> System.out.println("Saving the game to the file!"));
    }

    public static void main(String[] args) {
    }
    private void createSquares(JPanel boardPanel) {
        int gridSize = 6;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Cell cell = nurikabeBoardPanel.get(i * gridSize + j);
                GameBoardCell gameBoardCell = createGameBoardCell(cell);
                boardPanel.add(gameBoardCell.getComponent());
            }
        }
    }
    private GameBoardCell createGameBoardCell(Cell cell) {
        GameBoardCell gameBoardCell;
        switch (cell.getState()) {
            case 1 -> gameBoardCell = new SquareCell();
            case 2 -> {
                if (cell.getValue() != null) {
                    gameBoardCell = new ButtonCell(cell.getValue());
                } else {
                    gameBoardCell = new SquareCell();
                }
            }
            default -> throw new IllegalArgumentException("Invalid cell state: " + cell.getState());
        }
        return gameBoardCell;
    }

    public class TimerListener implements ActionListener {
        private long startTime;

        public TimerListener() {
            startTime = System.currentTimeMillis();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Calculate the elapsed time
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;

            // Format the elapsed time as HH:mm:ss
            long hours = elapsedTime / (1000 * 60 * 60);
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            long seconds = (elapsedTime / 1000) % 60;
            long milliseconds = elapsedTime % 1000;
            String formattedTime = String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);

            // Update the timer label
            timerLabel.setText(formattedTime);
        }

        public void startTimer() {
            startTime = System.currentTimeMillis();
            timer.start();
        }


    }

}

