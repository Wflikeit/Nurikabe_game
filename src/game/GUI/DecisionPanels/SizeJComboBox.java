package game.GUI.DecisionPanels;

import javax.swing.*;
import java.awt.*;

public class SizeJComboBox extends JComboBox<Object> {
    private int sizeOfBoard;
    public SizeJComboBox(String[] items) {
        super(items);
        initialize();
    }

    public int getSizeOfBoard() {
        return sizeOfBoard;
    }

    public void initialize() {
        setOpaque(true);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        sizeOfBoard = 5;

        addActionListener(e -> {
            // Get the selected item from the JComboBox
            Object selectedSize = getSelectedItem();
            assert selectedSize != null;
            sizeOfBoard = Integer.parseInt(selectedSize.toString());
        });
    }
}