package game;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::createAndShowGUI);
    }
    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? " +
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250, 250);
        f.setVisible(true);
    }
    public void saveGameStatus(){}
    public void loadGameStatus(){}
    public void undoMove(){}
    public void uploadSolution(){}
    public void start(){

    }
    public void showMenuPanel(){

    }
    public void showGamePanel(){}
    public void showLevelChoosing(){}
    public void pause(){}
    public void showSolution(){}
    public void clearBoard(){}

}
