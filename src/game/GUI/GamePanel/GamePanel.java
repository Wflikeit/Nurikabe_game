package game.GUI.GamePanel;

import game.Cell;
import game.GUI.Tools.IconsGamePanel;
import game.GameManager;
import game.GamePanelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final JLabel timerLabel;
    private final Timer timer;
    private final JButton pauseGameButton;
//    udhuvdhid
    private final JButton saveGameButton;
    private final JButton stepBackButton;
    private final JButton checkGameButton;
    public TimerListener timerListener;
//    dggcohipc
    public GamePanelManager gamePanelManager;
    public GameManager gameManager;

    public GamePanel(GameManager mainInstance) {
        setLayout(new BorderLayout());
        this.gameManager = mainInstance;


        timerLabel = new JLabel("00:00:00:00");
        timerLabel.setFont(new Font("Nunito", Font.PLAIN, 88));

        String stepBackButtonPath = "src/gameResources/stepBackButton.png";
        String saveGameButtonPath = "src/gameResources/saveGameButton.png";
        String checkGameButtonPath = "src/gameResources/checkButton.png";
        String pauseGameButtonPath = "src/gameResources/pauseGameButton.png";

        IconsGamePanel.prepareGamePanelButtonVisuals(stepBackButton = new JButton(IconsGamePanel.prepareGameIcon(stepBackButtonPath)));
        IconsGamePanel.prepareGamePanelButtonVisuals(saveGameButton = new JButton(IconsGamePanel.prepareGameIcon(saveGameButtonPath)));
        IconsGamePanel.prepareGamePanelButtonVisuals(checkGameButton = new JButton(IconsGamePanel.prepareGameIcon(checkGameButtonPath)));
        IconsGamePanel.prepareGamePanelButtonVisuals(pauseGameButton = new JButton(IconsGamePanel.prepareGameIcon(pauseGameButtonPath)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(Color.BLACK);


        buttonsPanel.add(timerLabel);
        buttonsPanel.add(stepBackButton);
        buttonsPanel.add(checkGameButton);
        buttonsPanel.add(saveGameButton);
        buttonsPanel.add(pauseGameButton);

//        System.out.println(nurikabeBoardPanel.);

        add(buttonsPanel, BorderLayout.NORTH);

        gamePanelManager = new GamePanelManager(this);
        gamePanelManager.setupButtonListeners();
        timerListener = new TimerListener();
        timer = new Timer(1, timerListener);
        timerListener.startTimer();
    }

    public JButton getPauseGameButton() {
        return pauseGameButton;
    }

    public JButton getSaveGameButton() {
        return saveGameButton;
    }

    public JButton getStepBackButton() {
        return stepBackButton;
    }

    public JButton getCheckGameButton() {
        return checkGameButton;
    }

    public class TimerListener implements ActionListener {
        private long startTime;
        private long elapsedTime;

        public TimerListener() {
            startTime = System.currentTimeMillis();
            elapsedTime = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String formattedTime = getTime();

            timerLabel.setText(formattedTime);
        }

        private String getTime() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;

            long hours = elapsedTime / (1000 * 60 * 60);
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            long seconds = (elapsedTime / 1000) % 60;
            long milliseconds = elapsedTime % 1000;
            return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
        }

        public void startTimer() {
            startTime = System.currentTimeMillis() - elapsedTime; // Adjust the start time based on the elapsed time
            timer.start();
        }

        public void resetTimer() {
            startTime = System.currentTimeMillis(); // Reset the start time to the current time
            elapsedTime = 0; // Reset the elapsed time
        }
    }
}