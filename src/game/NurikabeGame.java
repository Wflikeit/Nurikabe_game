package game;

import painting.Main;

import javax.swing.*;

public class NurikabeGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NurikabeGame::createAndShowGUI);
    }
    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? " +
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250, 250);
        f.setVisible(true);
    }
    private void saveGameStatus(){}
    private void loadGameStatus(){}
    private void stepBack(){}
    private void start(){

    }

}
