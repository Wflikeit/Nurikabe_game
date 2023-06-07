package game.GUI.GamePanel;

import game.GUI.Visuals.ColorsEnum;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;

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