package game.GUI;
import game.Cell;

import java.lang.Math;

import static java.lang.Integer.parseInt;

public class Solver {
    private final java.util.List<Cell> nurikabeBoardPanel;
    public Solver(java.util.List<Cell> nurikabeBoardPanel) {
        this.nurikabeBoardPanel = nurikabeBoardPanel;

    }

    public boolean CheckPossibleBlackCells(java.util.List<Cell> nurikabeBoardPanel) {
        int numberOfPossibleBlackCells = 0;
        int blackCells = 0;
        int numberOfLand = 0;
        int size = nurikabeBoardPanel.size();

        for (int i = 0; i < size; i++) {
            if(nurikabeBoardPanel.get(i).getValue() != null){
                Integer value = parseInt(nurikabeBoardPanel.get(i).getValue());
                numberOfLand += value;
            }
            if (nurikabeBoardPanel.get(i).getState() == -1) {
                blackCells++;
            }
        }
        numberOfPossibleBlackCells = size - numberOfLand;
        return numberOfPossibleBlackCells == blackCells;
    }
    public boolean CheckBlackConnected(java.util.List<Cell> nurikabeBoardPanel) {
        int size = (int) Math.sqrt(nurikabeBoardPanel.size());
        boolean[][] visited = new boolean[size][size];
        int startRow = -1;
        int startCol = -1;

        for (int row = 0; row < nurikabeBoardPanel.size(); row++) {
            for (int col = 0; col < nurikabeBoardPanel.size(); col++) {
                if (nurikabeBoardPanel.get(row).isBlack()) {
                    startRow = row;
                    startCol = col;
                    break;
                }
            }
            if (startRow != -1) {
                break;
            }
        }

        if (startRow == -1) {
            return true;
        }

        exploreBlackCells(nurikabeBoardPanel, startRow, startCol, visited);

        for (int row = 0; row < nurikabeBoardPanel.size(); row++) {
            for (int col = 0; col < nurikabeBoardPanel.size(); col++) {
                Cell cell = nurikabeBoardPanel.get(row);
                if (cell.isBlack() && !visited[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }
    private void exploreBlackCells(java.util.List<Cell> nurikabeBoardPanel, int row, int col, boolean[][] visited) {
        int size = (int) Math.sqrt(nurikabeBoardPanel.size());

        if (row < 0 || row >= size || col < 0 || col >= size || nurikabeBoardPanel.get(row * size + col).isBlack() || visited[row][col]) {
            return;
        }

        visited[row][col] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            exploreBlackCells(nurikabeBoardPanel, newRow, newCol, visited);
        }
    }

    public boolean CheckBlackSquare(java.util.List<Cell> nurikabeBoardPanel) {
        int n = (int) Math.sqrt(nurikabeBoardPanel.size());
        boolean[][] visited = new boolean[n][n];

        for (int row = 0; row < n - 1; row++) {
            for (int col = 0; col < n - 1; col++) {
                if (nurikabeBoardPanel.get(row * n + col).isBlack() && !visited[row][col]) {
                    if (isBlackSquare(nurikabeBoardPanel, row, col, visited)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    private boolean isBlackSquare(java.util.List<Cell> nurikabeBoardPanel, int row, int col, boolean[][] visited) {
        int n = (int) Math.sqrt(nurikabeBoardPanel.size());

        if (row < 0 || row >= n - 1 || col < 0 || col >= n - 1 || !nurikabeBoardPanel.get(row * n + col).isBlack()) {
            return false;
        }

        return nurikabeBoardPanel.get((row + 1) * n + col).isBlack() &&
                nurikabeBoardPanel.get(row * n + col + 1).isBlack() &&
                nurikabeBoardPanel.get((row + 1) * n + col + 1).isBlack();
    }

    public boolean CheckNeededLandCells(java.util.List<Cell> nurikabeBoardPanel) {
        int n = (int) Math.sqrt(nurikabeBoardPanel.size());

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (parseInt(nurikabeBoardPanel.get(row * n + col).getValue()) > 0) {
                    boolean[][] visited = new boolean[n][n];
                    int neededWhiteCells = parseInt(nurikabeBoardPanel.get(row * n + col).getValue()) - 1;
                    int count = dfsCountWhiteCells(nurikabeBoardPanel, row, col, visited, -1);
                    if (count != neededWhiteCells) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private int dfsCountWhiteCells(java.util.List<Cell> nurikabeBoardPanel, int row, int col, boolean[][] visited, int count) {
        int n = (int) Math.sqrt(nurikabeBoardPanel.size());

        if (row < 0 || row >= n || col < 0 || col >= n || nurikabeBoardPanel.get(row * n + col).isBlack() || visited[row][col]) {
            return count;
        }

        visited[row][col] = true;
        count++;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            count = dfsCountWhiteCells(nurikabeBoardPanel, newRow, newCol, visited, count);
        }

        return count;
    }

    public String CheckSolved(java.util.List<Cell> nurikabeBoardPanel){
        if(!CheckPossibleBlackCells(nurikabeBoardPanel)){
            return "Solution is incorrect";
        }
        else if(!CheckNeededLandCells(nurikabeBoardPanel)){
            return "Solution is incorrect";
        }
        else if (!CheckBlackConnected(nurikabeBoardPanel)) {
            return "Water should be connected";
        }
        else if(!CheckBlackSquare(nurikabeBoardPanel)){
            return "There cannot be the square in solution";
        }
        return "Well Done!";
    }

}