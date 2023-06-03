package game;

public class Solver {
    private int[][] puzzle;
    public Solver(int[][] puzzle) {
        this.puzzle = puzzle;

    }

    //  First Check
    public boolean CheckPossibleBlackCells(int[][] puzzle) {
        int numberOfPossibleBlackCells = 0;
        int blackCells = 0;
        int numberOfLand = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                if (puzzle[i][j] != 0 & puzzle[i][j] != -1) {
                    numberOfLand += puzzle[i][j];
                }
                if (puzzle[i][j] == -1) {
                    blackCells++;
                }
            }
        }
        numberOfPossibleBlackCells = puzzle.length * puzzle.length - numberOfLand;
        return numberOfPossibleBlackCells == blackCells;
    }

    //  Second Check
    public boolean CheckBlackConnected(int[][] puzzle) {
        boolean[][] visited = new boolean[puzzle.length][puzzle[0].length];
        int startRow = -1;
        int startCol = -1;

        // first black cell (-1)
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] == -1) {
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

        exploreBlackCells(puzzle, startRow, startCol, visited);

        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] == -1 && !visited[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }
    private void exploreBlackCells(int[][] puzzle, int row, int col, boolean[][] visited) {
        if (row < 0 || row >= puzzle.length || col < 0 || col >= puzzle[0].length || puzzle[row][col] != -1 || visited[row][col]) {
            return;
        }

        visited[row][col] = true;

        // directions
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // neighbors
        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            exploreBlackCells(puzzle, newRow, newCol, visited);
        }
    }

    //  Third Check
    public boolean CheckBlackSquare(int[][] puzzle) {
        int n = puzzle.length;
        boolean[][] visited = new boolean[n][n];

        for (int row = 0; row < n - 1; row++) {
            for (int col = 0; col < n - 1; col++) {
                if (puzzle[row][col] == -1 && !visited[row][col]) {
                    if (isBlackSquare(puzzle, row, col, visited)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    private boolean isBlackSquare(int[][] puzzle, int row, int col, boolean[][] visited) {
        if (row < 0 || row >= puzzle.length - 1 || col < 0 || col >= puzzle[0].length - 1 || puzzle[row][col] != -1) {
            return false;
        }

        return puzzle[row + 1][col] == -1 &&
                puzzle[row][col + 1] == -1 &&
                puzzle[row + 1][col + 1] == -1;
    }

    // Fourth Check
    public boolean CheckNeededLandCells(int[][] puzzle) {
        int n = puzzle.length;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (puzzle[row][col] > 0) {
                    boolean[][] visited = new boolean[n][n];
                    int neededWhiteCells = puzzle[row][col] - 1;
                    int count = dfsCountWhiteCells(puzzle, row, col, visited, -1); // -1 because we count the land cell as well
                    if (count != neededWhiteCells) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    private int dfsCountWhiteCells(int[][] puzzle, int row, int col, boolean[][] visited, int count) {
        // Check out of bounds, cell is black, or cell already visited
        if (row < 0 || row >= puzzle.length || col < 0 || col >= puzzle[0].length || puzzle[row][col] == -1 || visited[row][col]) {
            return count;
        }

        visited[row][col] = true;
        count++;

        // Define the possible directions: up, down, left, right
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Check the neighboring cells
        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            count = dfsCountWhiteCells(puzzle, newRow, newCol, visited, count);
        }

        return count;
    }

    // Main Check
    private boolean Check(int[][] puzzle){
        if(CheckPossibleBlackCells(puzzle)){
            System.out.println("Possible Black Cells ---->  passed\n");
        } else {
            System.out.println("Possible Black Cells ---->  failed");
            System.out.println("Incorrect number of Black Cells\n");
        }
        if(CheckBlackConnected(puzzle)){
            System.out.println("Black Cells are connected ---->  passed\n");
        } else {
            System.out.println("Black Cells are connected ---->  failed");
            System.out.println("Black Cells are not connected\n");
        }
        if(CheckBlackSquare(puzzle)){
            System.out.println("Black Square ---->  passed\n");
        } else {
            System.out.println("Black Square ---->  failed");
            System.out.println("Contains Black Square\n");
        }
        if(CheckNeededLandCells(puzzle)){
            System.out.println("Needed Land ---->  passed\n");
        } else {
            System.out.println("Needed Land ---->  failed");
            System.out.println("Does not Contain Needed amount of Land\n");
        }

        return CheckBlackConnected(puzzle) & CheckPossibleBlackCells(puzzle) & CheckBlackSquare(puzzle);
    }



    public static void main(String[] args) {
        int i = -1;
        int[][] puzzle = {
                {i, 3, 0, 0, i},
                {i, i, i, i, i},
                {i, 2, i, 0, i},
                {i, 0, i, 2, i},
                {i, i, 1, i, i}
        };
//        int[][] puzzle = {
//                {i, 3, 0, 0, i},
//                {0, i, i, i, i},
//                {0, 2, i, i, i},
//                {i, 0, i, 2, i},
//                {i, i, 1, i, i}
//        };

        Solver solver = new Solver(puzzle);
        solver.Check(puzzle);
    }

}

/**
 * -1 - means that a cell is black
 * 0 - means that a cell is white
 * int - means number of land
 */

