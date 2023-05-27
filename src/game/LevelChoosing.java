package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelChoosing extends JPanel {
    private Main mainInstance;

    public LevelChoosing(Main mainInstance) {
        JLabel welcomingText = new JLabel("Choose Level");
        this.mainInstance = mainInstance;

        ImageIcon imageIcon = new ImageIcon("src/gameResources/nurikabe.png");

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        JPanel panel1 = new JPanel();
        JLabel picture1 = new JLabel(imageIcon);
        JButton button1 = new JButton("Easy");
        JButton button2 = new JButton("Hard");
        JButton button3 = new JButton("Hell");
        JButton button4 = new JButton("Menu");

        Font Font1 = new Font("Font1", Font.BOLD, 28);


        ButtonUtils.prepareButtons(button1);
        ButtonUtils.prepareButtons(button2);
        ButtonUtils.prepareButtons(button3);
        ButtonUtils.prepareButtons(button4);
        welcomingText.setFont(new Font("Font1", Font.PLAIN, 56));
        welcomingText.setHorizontalAlignment(SwingConstants.CENTER);
        welcomingText.setForeground(ColorsEnum.BUTTON_COLOR.getColor());


        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(button4);
        add(picture1, BorderLayout.EAST);
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
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainInstance.showMenuPanel();
            }
        });


    }

    public void typeInSize() {
    }

    public void pickDifficulty() {
    }
}
