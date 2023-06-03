package game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main extends JFrame {
    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public Main() {
        setTitle("Nurikabe Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setSize(800, 600);
        ImageIcon appLogo = new ImageIcon("src/gameResources/nurikabe.png");
        setIconImage(appLogo.getImage());
        this.setBackground(Color.BLACK);

        setLocationRelativeTo(null);
        Menu menuPanel = new Menu(this);

        menuPanel.setLayout(cardLayout);
        LevelChoosing levelChoosing = new LevelChoosing(this);
        Board board = new Board(6); // Create an instance of the Board class
        // Populate the board and retrieve the nurikabeBoardPanel list
        GamePanel gamePanel = new GamePanel(this, board);

        cardPanel.add(menuPanel, "menu");
        cardPanel.add(levelChoosing, "levelChoosing");
        cardPanel.add(gamePanel, "gamePanel");
        add(cardPanel);
        board.print();
    }

    public void showGamePanel() {
        cardLayout.show(cardPanel, "gamePanel");
    }

    public void showMenuPanel() {
        cardLayout.show(cardPanel, "menu");
    }

    public void showLevelChoosing() {
        cardLayout.show(cardPanel, "levelChoosing");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
