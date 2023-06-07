package game.GUI;

import javax.swing.*;
import java.awt.*;

public class IconsGamePanel {
    public static void prepareGameIcon(JButton button) {
        button.setBorderPainted(false); // Remove border painting
        button.setContentAreaFilled(false); // Remove default content area fill
        button.setFocusPainted(false);
        button.setBackground(Color.BLACK);
    }


}
