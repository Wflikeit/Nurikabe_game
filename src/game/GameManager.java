package game;

import game.GUI.DecisionPanels.LevelChoosing;
import game.GUI.DecisionPanels.Menu;
import game.GUI.DecisionPanels.PausePanel;
import game.GUI.DecisionPanels.FileChooser;
import game.GUI.GamePanel.GamePanel;
import game.Solver;
import game.GUI.Tools.ColorsEnum;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameManager {
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final Main app;
    private final GamePanel gamePanel; // Store a reference to the current GamePanel
    private final GamePanelManager gamePanelManager; // Store a reference to the current GamePanel
    private final LevelChoosing levelChoosing;
    private final BoardFilePrinterForHuman boardFilePrinterForHuman;
    private Board board; // Store a reference to the current GamePanel
    private int fileForHumanCounter = 0;
    private Board solvedBoard;
    private final Menu menuPanel;
    private Solver solver;

    public GameManager(Main app) {
        this.app = app;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        menuPanel = new Menu(this);
        menuPanel.setLayout(cardLayout);
        levelChoosing = new LevelChoosing(this);
        PausePanel pausePanel = new PausePanel(this);
        boardFilePrinterForHuman = new BoardFilePrinterForHuman();
        gamePanel = new GamePanel(this);
        gamePanelManager = new GamePanelManager(gamePanel);
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(levelChoosing, "levelChoosing");
        cardPanel.add(gamePanel, "gamePanel");
        cardPanel.add(pausePanel, "pausePanel");
        app.add(cardPanel);
        gamePanel.timerListener.startTimer();
    }

    public void showGamePanel() {
        int size = levelChoosing.sizeJComboBox.getSizeOfBoard();
        String level = levelChoosing.getLevel();
        board = new Board(size, level); // Create an instance of the Board class
        board.fillBoard();
        solvedBoard = new Board(board.size, board.getLevel());
        board.copyBoard(solvedBoard);
        solver = new Solver(board,solvedBoard);
//        Solver solver = new Solver(board.getNurikabeBoardPanel());
        gamePanelManager.setUpGameBoard(board.getNurikabeBoardPanel(), board.size, false);
        // Populate the board and retrieve the nurikabeBoardPanel list
        startGame(); // Access the stored GamePanel instance
        cardLayout.show(cardPanel, "gamePanel");
        app.pack();
    }

    public void showMenuPanel() {
        cardLayout.show(cardPanel, "menu");
        app.pack();
    }

    public void showLevelChoosing() {
        cardLayout.show(cardPanel, "levelChoosing");
        app.pack();
    }

    public void showPausePanel() {
        cardLayout.show(cardPanel, "pausePanel");
        gamePanel.timerListener.stopTimer();
        app.pack();
    }

    public void startGame() {
        gamePanel.timerListener.resetTimer(); // Reset the timer in the GamePanel
        gamePanel.timerListener.startTimer(); // Start the timer from the initial value
        app.pack();
    }

    public void backToGame() {
        gamePanel.timerListener.startTimer();
        cardLayout.show(cardPanel, "gamePanel");
    }

    public void saveBoardForHuman() {
        fileForHumanCounter += 1;
        String filenameForHuman = "board_for_human" + fileForHumanCounter;
        boardFilePrinterForHuman.setBoard(board.getNurikabeBoardPanel());
        boardFilePrinterForHuman.saveBoardToFile(filenameForHuman);
        JOptionPane optionPane = new JOptionPane("No records of game found", JOptionPane.INFORMATION_MESSAGE);
        optionPane.setBorder(null);
        UIManager.put("Button.background", ColorsEnum.BUTTON_COLOR.getColor());
        JOptionPane.showMessageDialog(optionPane, "File saved under the name: " + filenameForHuman,
                "Select Size", JOptionPane.PLAIN_MESSAGE);

    }

    public void saveGame() throws IOException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String fileName = "./Data/" + currentDateTime.format(formatter) + ".save";

        if (!new File("./Data").exists())
            Files.createDirectory(Path.of("./Data"));

        File file = new File(fileName);
        FileWriter writer = new FileWriter(fileName);
        writeToFile(writer, board);
        writer.write(gamePanel.timerListener.getTime());
        writer.write("\n");
        for (int i = 0; i < board.size * board.size; i++) {
            writer.write(solvedBoard.getNurikabeBoardPanel().get(i).getState().toString() + "\n");
        }
        writer.close();
        JOptionPane.showMessageDialog(null, "File created: " + file.getAbsolutePath(),
                "Save game", JOptionPane.PLAIN_MESSAGE);
    }

    private void writeToFile(FileWriter writer, Board savedBoard) throws IOException {
        if (savedBoard.getNurikabeBoardPanel() == null) {
            System.out.println("x");
        }
        writer.write(savedBoard.size + "\n");
        writer.write(savedBoard.getLevel() + "\n");
        for (int i = 0; i < savedBoard.size * savedBoard.size; i++) {
            writer.write(savedBoard.getNurikabeBoardPanel().get(i).getState().toString());
            if (savedBoard.getNurikabeBoardPanel().get(i).getValue() != null) {
                writer.write(" " + savedBoard.getNurikabeBoardPanel().get(i).getValue());
            }
            writer.write("\n");
        }
    }

    public void loadGame(FileReader reader) throws IOException {
        BufferedReader saveReader = new BufferedReader(reader);
        int size = Integer.parseInt(saveReader.readLine());
        String level = saveReader.readLine().strip();
        board = new Board(size, level);
        for (int i = 0; i < size * size; i++) {
            String line = saveReader.readLine();
            if (line.length() > 1) {
                board.getNurikabeBoardPanel().get(i).setState(2);
                board.getNurikabeBoardPanel().get(i).setValue(line.substring(2));
            } else {
                board.getNurikabeBoardPanel().get(i).setState(Integer.parseInt(line.substring(0, 1)));
            }
        }
        int[] time = new int[12];
        for (int i = 0; i < 12; i++) {
            time[i] = saveReader.read() - '0';
        }
        saveReader.skip(1);

        solvedBoard = new Board(size, level);
        for (int i = 0; i < size * size; i++) {
            solvedBoard.getNurikabeBoardPanel().get(i).setState(Integer.parseInt(saveReader.readLine()));
        }
        gamePanel.timerListener.setTimer(time);
        gamePanelManager.setUpGameBoard(board.getNurikabeBoardPanel(), board.size, true);
        gamePanel.timerListener.startTimer();
        cardLayout.show(cardPanel, "gamePanel");
        app.pack();
    }

    public void showSolution() {
        for (int i = 0; i < board.size * board.size; i++) {
            board.getNurikabeBoardPanel().get(i).setState(solvedBoard.getNurikabeBoardPanel().get(i).getState());
        }
        gamePanel.timerListener.stopTimer();
        gamePanel.timerListener.resetTimer();
        gamePanelManager.setUpGameBoard(board.getNurikabeBoardPanel(), board.size, true);
        cardLayout.show(cardPanel, "gamePanel");
        app.pack();
    }
    public void loadGameFile() {
        FileChooser fileChooser = new FileChooser(menuPanel);
        fileChooser.showFileChooser();
        try(FileReader reader = new FileReader(fileChooser.getSelectedFilePath())) {
            this.loadGame(reader);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File loading failed");
        }
    }
    public void checkGame() {
        solver.checkSolved();
    }
}