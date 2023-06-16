package game.GUI.GamePanel;

import game.GUI.Tools.IconsGamePanel;
import game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final JLabel timerLabel;
    private final Timer timer;
    private final JButton pauseGameButton;
    private final JButton saveGameButton;
    private final JButton stepBackButton;
    private final JButton stepForwardButton;
    private final JButton checkGameButton;
    public TimerListener timerListener;
    public GameManager gameManager;

    public GamePanel(GameManager mainInstance) {
        setLayout(new BorderLayout());
        this.gameManager = mainInstance;


        timerLabel = new JLabel("00:00:00:00");
        timerLabel.setFont(new Font("Nunito", Font.PLAIN, 88));

        String stepBackButtonPath = "img/stepBackButton.png";
        String stepForwardButtonPath = "img/stepForwardButton.png";
        String saveGameButtonPath = "img/saveGameButton.png";
        String checkGameButtonPath = "img/checkButton.png";
        String pauseGameButtonPath = "img/pauseGameButton.png";

        IconsGamePanel.prepareGamePanelButtonVisuals(stepBackButton =
                new JButton(IconsGamePanel.prepareGameIcon(stepBackButtonPath)));
        IconsGamePanel.prepareGamePanelButtonVisuals(stepForwardButton =
                new JButton(IconsGamePanel.prepareGameIcon(stepForwardButtonPath)));
        IconsGamePanel.prepareGamePanelButtonVisuals(saveGameButton =
                new JButton(IconsGamePanel.prepareGameIcon(saveGameButtonPath)));
        IconsGamePanel.prepareGamePanelButtonVisuals(checkGameButton =
                new JButton(IconsGamePanel.prepareGameIcon(checkGameButtonPath)));
        IconsGamePanel.prepareGamePanelButtonVisuals(pauseGameButton =
                new JButton(IconsGamePanel.prepareGameIcon(pauseGameButtonPath)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(Color.BLACK);


        buttonsPanel.add(timerLabel);
        buttonsPanel.add(stepBackButton);
        buttonsPanel.add(stepForwardButton);
        buttonsPanel.add(checkGameButton);
        buttonsPanel.add(saveGameButton);
        buttonsPanel.add(pauseGameButton);

        add(buttonsPanel, BorderLayout.NORTH);

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
    public JButton getStepForwardButton() {
        return stepForwardButton;
    }


    public JButton getCheckGameButton() {
        return checkGameButton;
    }

    public class TimerListener implements ActionListener {
        private long startTime;
        private long elapsedTime;
        private boolean isRunning;


        public TimerListener() {
            startTime = System.currentTimeMillis();
            elapsedTime = 0;
            isRunning = false;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String formattedTime = getTime();

            timerLabel.setText(formattedTime);
        }

        public String getTime() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;

            long hours = elapsedTime / (1000 * 60 * 60);
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            long seconds = (elapsedTime / 1000) % 60;
            long milliseconds = elapsedTime % 1000;
            return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
        }

        public void startTimer() {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - elapsedTime; // Adjust the start time based on the elapsed time
                timer.start();
                isRunning = true;
            }
        }
        public void stopTimer() {
            if (isRunning) {
                timer.stop();
                isRunning = false;
            }
        }

        public void resetTimer() {
            startTime = System.currentTimeMillis(); // Reset the start time to the current time
            elapsedTime = 0; // Reset the elapsed time
        }

        public void setTimer(int[] time) {
            long hours = (time[0]* 10L +time[1])*1000*3600;
            long minutes = (time[3]* 10L +time[4])*1000*60;
            long seconds = (time[6]* 10L +time[7])*1000;
            long miliseconds = time[9]* 100L +time[10]* 10L +time[11];
            elapsedTime =hours+minutes+seconds+miliseconds;
            isRunning = false;
        }
    }
}