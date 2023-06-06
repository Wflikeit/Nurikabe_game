package game.GUI;

import javax.swing.*;
import java.awt.*;
public class ButtonUtils {
    static Dimension buttonSize = new Dimension(500, 100);
    static Font Font1 = new Font("Font1", Font.BOLD, 28);

    public static void prepareButtons(JButton button) {
        button.setForeground(Color.BLACK);
        button.setBackground(ColorsEnum.BUTTON_COLOR.getColor());
        button.setPreferredSize(buttonSize);
        button.setFont(Font1);
        // Add any other common button color settings here
    }

    // Other utility methods for button colors can be added here if needed

}
