package game.GUI.GamePanel;

import game.Cell;
import game.GUI.IconsGamePanel;
import game.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    public final JLabel timerLabel;
    private final Main mainInstance;
    private final java.util.List<Cell> nurikabeBoardPanel;
    public Timer timer;
    public TimerListener timerListener;
    int gridSize = 6;


    public GamePanel(Main mainInstance, java.util.List<Cell> nurikabeBoardPanel) {
        this.nurikabeBoardPanel = nurikabeBoardPanel;
        JPanel board_panel = new JPanel();
        JPanel buttons_panel = new JPanel();
        setLayout(new BorderLayout());


        BoardGenerator boardGenerator = new BoardGenerator(board_panel, nurikabeBoardPanel);
        boardGenerator.generateVisualBoard();


        timerListener = new TimerListener(); // Initialize the timerListener field
        this.mainInstance = mainInstance;

        timer = new Timer(1, timerListener);
        buttons_panel.setLayout(new FlowLayout());
        buttons_panel.setBackground(Color.BLACK);
        timerLabel = new JLabel("00:00:00:00"); // Assign to the class-level field
        timerLabel.setFont(new Font("Nunito", Font.PLAIN, 88));
        ImageIcon stepBackButtonImageIconOriginal = new ImageIcon("src/gameResources/stepBackButton.png");
        Image stepBackButtonImage = stepBackButtonImageIconOriginal.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH); // Specify the desired width and height
        ImageIcon stepBackButtonImageIcon = new ImageIcon(stepBackButtonImage);
        JButton stepBackButton = new JButton(stepBackButtonImageIcon);


        String saveGameButtonImagePath = "src/gameResources/saveGameButton.png";
        ImageIcon saveGameButtonImageIcon = new ImageIcon(new ImageIcon(saveGameButtonImagePath).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        JButton saveGameButton = new JButton(saveGameButtonImageIcon);

        String checkButtonImagePath = "src/gameResources/checkButton.png";
        ImageIcon checkButtonButtonImageIcon = new ImageIcon(new ImageIcon(checkButtonImagePath).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        JButton checkGameButton = new JButton(checkButtonButtonImageIcon);

        String pauseGameButtonImagePath = "src/gameResources/pauseGameButtonImage.png";
        ImageIcon pauseGameButtonImageIcon = new ImageIcon(new ImageIcon(pauseGameButtonImagePath).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        JButton pauseGameButton = new JButton(pauseGameButtonImageIcon);


        IconsGamePanel.prepareGameIcon(stepBackButton);
        IconsGamePanel.prepareGameIcon(saveGameButton);
        IconsGamePanel.prepareGameIcon(checkGameButton);
        IconsGamePanel.prepareGameIcon(pauseGameButton);


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

