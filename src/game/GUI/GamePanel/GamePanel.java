package game.GUI.GamePanel;

import game.Cell;
import game.GUI.Visuals.IconsGamePanel;
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
    private final JButton saveGameButton;
    private final JButton stepBackButton;
    private final JButton checkGameButton;
    public TimerListener timerListener;
    public GamePanelManager gamePanelManager;
    public GameManager gameManager;

    public GamePanel(GameManager mainInstance, java.util.List<Cell> nurikabeBoardPanel, int size) {
        setLayout(new BorderLayout());
        this.gameManager = mainInstance;

        JPanel boardPanel = new JPanel();
        BoardGenerator boardGenerator = new BoardGenerator(boardPanel, nurikabeBoardPanel);
        boardGenerator.generateVisualBoard(size);

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
        boardPanel.setLayout(new GridLayout(size, size));
        add(boardPanel, BorderLayout.CENTER);
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
        private final long startTime;

        public TimerListener() {
            startTime = System.currentTimeMillis();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;

            long hours = elapsedTime / (1000 * 60 * 60);
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            long seconds = (elapsedTime / 1000) % 60;
            long milliseconds = elapsedTime % 1000;
            String formattedTime = String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);

            timerLabel.setText(formattedTime);
        }

        public void startTimer() {
            timer.start();
        }
    }
}