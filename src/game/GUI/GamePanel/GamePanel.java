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
        this.nurikabeBoardPanel = nurikabeBoardPanel;
        JPanel board_panel = new JPanel();
        JPanel buttons_panel = new JPanel();
        setLayout(new BorderLayout());

        createSquares(board_panel);

        timerListener = new TimerListener(); // Initialize the timerListener field
        this.mainInstance = mainInstance;

        timer = new Timer(1, timerListener);
        buttons_panel.setLayout(new FlowLayout());
        buttons_panel.setBackground(Color.BLACK);
        timerLabel = new JLabel("00:00:00:00"); // Assign to the class-level field
        timerLabel.setFont(new Font("Nunito", Font.PLAIN, 88));
        ImageIcon stepBackButtonImageIconOriginal = new ImageIcon("src/gameResources/left-arrow.png");
        Image stepBackButtonImage = stepBackButtonImageIconOriginal.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH); // Specify the desired width and height
        ImageIcon stepBackButtonImageIcon = new ImageIcon(stepBackButtonImage);
        JButton stepBackButton = new JButton(stepBackButtonImageIcon);
        stepBackButton.setBorderPainted(false); // Remove border painting
        stepBackButton.setContentAreaFilled(false); // Remove default content area fill
        stepBackButton.setFocusPainted(false); // Remove focus painting
        stepBackButton.setBackground(Color.BLACK);

        ImageIcon saveGameButtonImageIconOriginal = new ImageIcon("src/gameResources/diskette.png"); // Replace with the path to your image
        Image saveGameButtonImage = saveGameButtonImageIconOriginal.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH); // Specify the desired width and height
        ImageIcon saveGameButtonImageIcon = new ImageIcon(saveGameButtonImage);
        JButton saveGameButton = new JButton(saveGameButtonImageIcon);
        saveGameButton.setBorderPainted(false); // Remove border painting
        saveGameButton.setContentAreaFilled(false); // Remove default content area fill
        saveGameButton.setFocusPainted(false); // Remove focus painting
        saveGameButton.setBackground(Color.BLACK);

        ImageIcon pauseGameButtonImageIconOriginal = new ImageIcon("src/gameResources/pauseGameButtonImage.png"); // Replace with the path to your image
        Image pauseGameButtonImage = pauseGameButtonImageIconOriginal.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH); // Specify the desired width and height
        ImageIcon pauseGameButtonImageIcon = new ImageIcon(pauseGameButtonImage);
        JButton pauseGameButton = new JButton(pauseGameButtonImageIcon);
        pauseGameButton.setBorderPainted(false); // Remove border painting
        pauseGameButton.setContentAreaFilled(false); // Remove default content area fill
        pauseGameButton.setFocusPainted(false); // Remove focus painting
        pauseGameButton.setBackground(Color.BLACK);

        ImageIcon checkButtonImageIconOriginal = new ImageIcon("src/gameResources/checkButton.png"); // Replace with the path to your image
        Image checkButtonImage = checkButtonImageIconOriginal.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH); // Specify the desired width and height
        ImageIcon checkButtonImageIcon = new ImageIcon(checkButtonImage);
        JButton checkGameButton = new JButton(checkButtonImageIcon);
        checkGameButton.setBorderPainted(false); // Remove border painting
        checkGameButton.setContentAreaFilled(false); // Remove default content area fill
        checkGameButton.setFocusPainted(false); // Remove focus painting
        checkGameButton.setBackground(Color.BLACK);



        buttons_panel.add(timerLabel);
        buttons_panel.add(stepBackButton);
        buttons_panel.add(checkGameButton);
        buttons_panel.add(saveGameButton);
        buttons_panel.add(pauseGameButton);

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

