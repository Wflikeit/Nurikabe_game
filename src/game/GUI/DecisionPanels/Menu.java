package game.GUI.DecisionPanels;

import game.GUI.Visuals.ButtonUtils;
import game.GUI.Visuals.ColorsEnum;
import game.GameManager;

import javax.swing.*;
import java.awt.*;


public class Menu extends JPanel {

    public Menu(GameManager gameManager) {
        setLayout(new BorderLayout());


        Container container = new Container();

        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton exitButton = new JButton("Quit Game");
        JLabel welcomingText = new JLabel("NURIKABE GAME");

        setBackground(Color.BLACK);

        ButtonUtils.prepareButtons(newGameButton);
        ButtonUtils.prepareButtons(loadGameButton);
        ButtonUtils.prepareButtons(exitButton);
        ButtonUtils.prepareButtons(newGameButton);

        welcomingText.setForeground(ColorsEnum.BUTTON_COLOR.getColor());
        welcomingText.setHorizontalAlignment(SwingConstants.CENTER);

        welcomingText.setFont(new Font("Font1", Font.PLAIN, 64));


        newGameButton.addActionListener(e -> {
            System.out.println("Rozpoczęcie nowej gry...");
            gameManager.showLevelChoosing(); // Wywołanie metody showLevelChoosing() na obiekcie Main
        });
        loadGameButton.addActionListener(e -> {
            System.out.println("Wczytywanie...");
            FileChooser fileChooser = new FileChooser(this);
            fileChooser.showFileChooser();

        });

        exitButton.addActionListener(e -> System.exit(0));


        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 55, 15, 55);


        gbc.gridx = 0;
        gbc.gridy = 0;

        // resizing buttons when resizing screen
        gbc.weightx = 0.25;

        gbc.anchor = GridBagConstraints.CENTER;

        container.add(welcomingText, gbc);

        gbc.gridy = 1;
        container.add(newGameButton, gbc);

        gbc.gridy = 2;
        container.add(loadGameButton, gbc);

        gbc.gridy = 3;

        container.add(exitButton, gbc);

        add(container, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

    }

    private void start() {

    }

    private void chooseSettings() {
    }

    private void chooseFile() {
    }

}




