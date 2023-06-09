package game.GUI.DecisionPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SizeJComboBox extends JComboBox<Object> {
    public SizeJComboBox(String[] items) {
        super(items);
        initialize();
    }

    public void initialize() {
        setOpaque(true);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item from the JComboBox
                Object selectedSize = getSelectedItem();

                // Call the showGamePanel method with the selected size
            }
        });
    }
}