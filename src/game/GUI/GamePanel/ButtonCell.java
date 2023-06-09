package game.GUI.GamePanel;

import game.GUI.Tools.ColorsEnum;

import javax.swing.*;
import java.awt.*;

public class ButtonCell implements GameBoardCell {
    private final JButton button;

    public ButtonCell(String value) {
        button = new JButton(value);
        button.setBackground(ColorsEnum.BUTTON_COLOR_2.getColor());
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    @Override
    public Component getComponent() {
        return button;
    }
}