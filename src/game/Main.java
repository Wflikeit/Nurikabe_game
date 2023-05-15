package game;

import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {
    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public Main() {
        setTitle("Nurikabe Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setSize(800, 600);

        setLocationRelativeTo(null);

//
//        NurikabeMenu menuPanel = new NurikabeMenu();
//        menuPanel.setMainInstance(this);
//
////        TablescorePanel tablescorePanel = new TablescorePanel();
//        NurikabeMainWindow gamePanel = new NurikabeMainWindow();
//
//
//        cardPanel.add(menuPanel, "menu");
//        cardPanel.add(tablescorePanel, "tablescore");
//        cardPanel.add(gamePanel, "game");

        add(cardPanel);
    }

    public void showMenuPanel() {
        cardLayout.show(cardPanel, "menu");
    }

    public void showTablescorePanel() {
//        cardLayout.show(tablescorePanel, "tablescore");
    }

    public void showGamePanel() {
//        cardLayout.show(gamePanel, "game");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}

