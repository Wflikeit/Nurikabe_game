package game.GUI.DecisionPanels;

import game.GUI.Tools.ColorsEnum;

import javax.swing.*;
import java.io.File;

public class FileChooser {
    private final Menu menu;
    private File selectedFile;

    public FileChooser(Menu menu) {
        this.menu = menu;
    }

    public void showFileChooser() {
        String directoryPath = "Data";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            // Get the list of files in the directory
            File[] files = directory.listFiles();
            assert files != null;
            System.out.println(files.length);
            if (files.length != 0) {
                JFileChooser fileChooser = new JFileChooser(directoryPath);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(null, "Selected file: " + selectedFile.getAbsolutePath());
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(null, "File selection cancelled");
                }
            } else {
                JOptionPane optionPane = new JOptionPane("No records of game found", JOptionPane.INFORMATION_MESSAGE);
                optionPane.setBorder(null);
                UIManager.put("Button.background", ColorsEnum.BUTTON_COLOR.getColor());
                JOptionPane.showMessageDialog(optionPane, "No records of game found");
            }
        }
    }
    public String getSelectedFilePath(){
        if(selectedFile!=null) {
            return selectedFile.getAbsolutePath();
        }
        return "";
    }
}
