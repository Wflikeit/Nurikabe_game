package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel{
    private void start(){

    }
    private void chooseSettings(){}
    private void chooseFile(){}

    private Main mainInstance;


    public Menu() {
        setLayout(new BorderLayout());


        Container container = new Container();

        JButton newGameButton = new JButton("Nowa gra");
        JButton loadGameButton = new JButton("Wczytaj grę");
        JButton exitButton = new JButton("Wyjdź");
        JLabel welcomingText = new JLabel("NURIKABE GAME");

        Color buttonColor2 = new Color(0x86B38F);
        Color buttonColor = new Color(0x8694B3);
        Color Color3 = new Color(0xC2CDFF);
        Color Color4 = new Color(0xFFF7EF);
        Color Color5 = new Color(0x8E8AB3);
        Dimension buttonSize = new Dimension(500, 100);
        Font Font1 = new Font("Font1", Font.BOLD, 28);

        setBackground(Color4);

        newGameButton.setPreferredSize(buttonSize);
        loadGameButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        newGameButton.setBackground(buttonColor);
        loadGameButton.setBackground(buttonColor);
        exitButton.setBackground(buttonColor);

        welcomingText.setHorizontalAlignment(SwingConstants.CENTER);
        newGameButton.setForeground(Color.BLACK);
        loadGameButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        welcomingText.setFont(new Font("Font1", Font.PLAIN, 64));
        newGameButton.setFont(Font1);
        exitButton.setFont(Font1);
        loadGameButton.setFont(Font1);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Rozpoczęcie nowej gry...");
                mainInstance.showMenuPanel();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Wczytywanie gry...");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });




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
    public void setMainInstance(Main mainInstance) {
        this.mainInstance = mainInstance;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Nurikabe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menuPanel = new Menu();
        frame.getContentPane().add(menuPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}


