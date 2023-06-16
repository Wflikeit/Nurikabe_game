package game;


import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Nurikabe Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
//        setMinimumSize(new Dimension(1000, 600));
        ImageIcon appLogo = new ImageIcon("src/gameResources/nurikabe.png");
        setIconImage(appLogo.getImage());
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            GameManager gameManager = new GameManager(app);
            app.setVisible(true);
        });
    }
}
