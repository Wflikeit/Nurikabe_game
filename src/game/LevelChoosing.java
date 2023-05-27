package game;

import javax.swing.*;
import java.awt.*;

public class LevelChoosing extends JPanel {
    private Main mainInstance;
    public LevelChoosing(){
        JLabel welcomingText = new JLabel("Choose Level");




//
//
//        JButton loadGameButton = new JButton("Load Game");
//        Menu menu = new Menu(mainInstance);
//
//        GroupLayout layout = new GroupLayout(this);
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
//
//        Dimension buttonSize = new Dimension(500, 100);
//        Font Font1 = new Font("Font1", Font.BOLD, 28);
//        welcomingText.setFont(Font1);
//        welcomingText.setSize(buttonSize);
//
//        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
//        hGroup.addComponent(welcomingText);
//        hGroup.addComponent(levelTextArea);
//        hGroup.addComponent(levelTextArea1);
//        hGroup.addComponent(newGameButton);
//        layout.setHorizontalGroup(hGroup);
//
//        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
//        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                .addComponent(welcomingText));
//        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                .addComponent(levelTextArea))
//                .addComponent(levelTextArea1)
//                .addComponent(newGameButton);
//        layout.setVerticalGroup(vGroup);
//
//        setLayout(layout);
        ImageIcon imageIcon = new ImageIcon("src/gameResources/nurikabe.png");

        setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        JLabel picture1 = new JLabel(imageIcon);

        Container container = new Container();
        container.setBackground(Color.BLACK);
        JButton button1 = new JButton("Easy");
        JButton button2 = new JButton("Hard");
        JButton button3 = new JButton("Hell");

        Color buttonColor = new Color(0x8694B3);
        Color Color4 = new Color(0xFFF7EF);
        Dimension buttonSize = new Dimension(500, 100);
        Font Font1 = new Font("Font1", Font.BOLD, 28);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        button1.setBackground(buttonColor);
        button2.setBackground(buttonColor);
        button3.setBackground(buttonColor);

        welcomingText.setHorizontalAlignment(SwingConstants.CENTER);
        button1.setForeground(Color.BLACK);
        button2.setForeground(Color.BLACK);
        button3.setForeground(Color.BLACK);

        welcomingText.setFont(new Font("Font1", Font.PLAIN, 64));
        button1.setFont(Font1);
        button3.setFont(Font1);
        button2.setFont(Font1);
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
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

        add(panel1, BorderLayout.CENTER);


// Create a JPanel with GridBagLayout
//        JPanel panel = new JPanel(new GridBagLayout());
//
//        // Create GridBagConstraints for customizing the layout
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.insets = new Insets(5, 5, 5, 5);
//
//        // Create buttons
//        JButton button1 = new JButton("Easy");
//        JButton button2 = new JButton("Hard");
//        JButton button3 = new JButton("Hell");
//
//        // Add buttons to the panel
//        panel.add(button1, constraints);
//
//        constraints.gridx = 1;
//        panel.add(button2, constraints);
//
//        constraints.gridx = 2;
//        panel.add(button3, constraints);
//
//
//
//
//        // Create JLabel with an image
//        constraints.gridx = 0;
//        constraints.gridy = 2;
//        constraints.gridwidth = 3;
//        constraints.fill = GridBagConstraints.NONE;
//        constraints.weightx = 0.0;
//        constraints.weighty = 0.0;
//        constraints.anchor = GridBagConstraints.CENTER;
//
//        ImageIcon imageIcon = new ImageIcon("src/gameResources/nurikabe.png");
//        JLabel imageLabel = new JLabel(imageIcon);
//        panel.add(imageLabel, constraints);
//
//        add(panel);

    }
    public void typeInSize(){}
    public void pickDifficulty(){}
}
