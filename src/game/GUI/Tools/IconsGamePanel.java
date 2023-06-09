package game.GUI.Tools;

import javax.swing.*;
import java.awt.*;

public class IconsGamePanel {
    private static final int buttonWidth = 75;
    private static final int buttonHeight = 75;

    public static void prepareGamePanelButtonVisuals(JButton button) {
        button.setBorderPainted(false); // Remove border painting
        button.setContentAreaFilled(false); // Remove default content area fill
        button.setFocusPainted(false);
        button.setBackground(Color.BLACK);
    }

    public static ImageIcon prepareGameIcon(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
