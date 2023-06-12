package game;

import game.Board;
import game.Cell;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BoardFilePrinterForHuman {
    private List<Cell> nurikabeBoard;

    public BoardFilePrinterForHuman() {
    }

    public void setBoard(List<Cell> nurikabeBoard) {
        this.nurikabeBoard = nurikabeBoard;
    }


    public void saveBoardToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            int rows = (int) Math.sqrt(nurikabeBoard.size());
            int k = 0;

            for (int i = 0; i < rows; i++) {
                // Write horizontal borders
                for (int j = 0; j < rows; j++) {
                    writer.write("+---");
                }
                writer.write("+\n");

                // Write values and vertical borders
                for (int j = 0; j < rows; j++) {
                    writer.write("| " + nurikabeBoard.get(k++).getState() + " ");
                }
                writer.write("|\n");
            }

            // Write the bottom border
            for (int j = 0; j < rows; j++) {
                writer.write("+---");
            }
            writer.write("+\n");

            System.out.println("Board saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
