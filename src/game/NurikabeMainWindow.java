package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NurikabeMainWindow {
    private Solver Solver;

    public NurikabeMainWindow(Solver nurikabeSolver) {
        this.Solver = nurikabeSolver;
    }

    public void showBoard() {
        JFrame frame = new JFrame("Nurikabe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int k =4;
        JPanel panel = new JPanel(new GridLayout(k, k));

//        for (int i = 0; i < k; i++) {
//            for (int j = 0; j < k; j++) {
//                Cell cell = board.getCell(i, j);
//                JButton button = new JButton();
//                if (cell.isBlack()) {
//                    button.setBackground(Color.BLACK);
//                } else if (cell.isWhite()) {
//                    button.setBackground(Color.WHITE);
//                    if (cell.isIsland()) {
//                        button.setText(Integer.toString(cell.getIslandSize()));
//                    }
//                }
//                panel.add(button);
//            }
//        }

        frame.add(panel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem checkItem = new JMenuItem("Check");
        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");

//        checkItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                boolean isSolved = solver.checkSolution(board);
//                if (isSolved) {
//                    JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Sorry, your solution is incorrect.");
//                }
//            }
//        });
//
//        undoItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                board.undoMove();
//                showBoard(board);
//            }
//        });
//
//        saveItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Zapis stanu gry do pliku
//                JFileChooser fileChooser = new JFileChooser();
//                int option = fileChooser.showSaveDialog(frame);
//                if (option == JFileChooser.APPROVE_OPTION) {
//                    // Pobranie ścieżki do pliku
//                    String filePath = fileChooser.getSelectedFile().getPath();
//                    // Zapis stanu gry do pliku
//                    board.saveToFile(filePath);
//                }
//            }
//        });

//        loadItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Wczytanie stanu gry z pliku
//                JFileChooser fileChooser = new JFileChooser();
//                int option = fileChooser.showOpenDialog(frame);
//                if (option == JFileChooser.APPROVE_OPTION) {
//                    // Pobranie ścieżki do pliku
//                    String filePath = fileChooser.getSelectedFile().getPath();
//                    // Wczytanie stanu gry z pliku
//                    board.loadFromFile(filePath);
//                    showBoard(board);
//                }
//            }
//        });

//        menu.add(checkItem);
//        menu.add(undoItem);
//        menu.add(saveItem);
//        menu.add(loadItem);
//        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);
    }
}
