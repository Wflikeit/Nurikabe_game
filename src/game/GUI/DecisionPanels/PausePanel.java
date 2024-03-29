package game.GUI.DecisionPanels;

import game.GUI.Tools.ButtonUtils;
import game.GUI.Tools.ColorsEnum;
import game.GameManager;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {

    public PausePanel(GameManager gameManager) {
        JLabel welcomingText = new JLabel("Game Paused");
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        JPanel panel1 = new JPanel();
        JButton button1 = ButtonUtils.prepareButtons(new JButton("Continue"));
        JButton button2 = ButtonUtils.prepareButtons(new JButton("Save Board"));
        JButton button3 = ButtonUtils.prepareButtons(new JButton("Show solution "));
        JButton button4 = ButtonUtils.prepareButtons(new JButton("Menu"));

        welcomingText.setFont(new Font("Font1", Font.PLAIN, 56));
        welcomingText.setHorizontalAlignment(SwingConstants.CENTER);
        welcomingText.setForeground(ColorsEnum.BUTTON_COLOR.getColor());


        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(button4);
        panel1.setBackground(Color.BLACK);
        panel1.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 55, 15, 55);

        gbc.gridx = 0;
        gbc.gridy = 0;

        // resizing buttons when resizing screen
        gbc.weightx = 0.25;

        gbc.anchor = GridBagConstraints.CENTER;

        panel1.add(welcomingText, gbc);

        gbc.gridy = 1;
        panel1.add(button1, gbc);

        gbc.gridy = 2;
        panel1.add(button2, gbc);

        gbc.gridy = 3;
        panel1.add(button3, gbc);

        gbc.gridy = 4;
        panel1.add(button4, gbc);


        add(panel1, BorderLayout.CENTER);
        button4.addActionListener(e -> gameManager.showMenuPanel()

        );
        button1.addActionListener(e -> gameManager.backToGame());
        button2.addActionListener(e -> gameManager.saveBoardForHuman());
        button3.addActionListener(e -> gameManager.showSolution());
        button4.addActionListener(e -> {
//            JOptionPane.showMessageDialog(null, sizeJComboBox, "Select Size", JOptionPane.PLAIN_MESSAGE);
            gameManager.showMenuPanel();
        });

    }

}
