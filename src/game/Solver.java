package game;
import game.GUI.GamePanel.SqrOrButtonCell;
import game.GUI.GamePanel.SquareCell;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Solver {
    private final java.util.List<Cell> nurikabeBoardPanel;
    private int sizeIn2D;

    private List<List<SqrOrButtonCell>> sqrOrButtonCells2D = new ArrayList<>();
    public Solver(java.util.List<Cell> nurikabeBoardPanel, List<SqrOrButtonCell> sqrOrButtonCells) {
        this.nurikabeBoardPanel = nurikabeBoardPanel;
        sizeIn2D = (int) sqrt(nurikabeBoardPanel.size());
        for(int i = 0; i < sizeIn2D; i++){
            List<SqrOrButtonCell> row = new ArrayList<>();
            for (int j = 0; j < sizeIn2D; j++) {
                double index = i * sizeIn2D + j;
                row.add(sqrOrButtonCells.get((int) index));
            }
            sqrOrButtonCells2D.add(row);
        }
    }

    private boolean CheckPossibleBlackCells() {
        int numberOfPossibleBlackCells = 0;
        int blackCells = 0;
        int numberOfLand = 0;
        int size = nurikabeBoardPanel.size();

        for (int i = 0; i < size; i++) {
            if(nurikabeBoardPanel.get(i).getValue() != null){
                Integer value = parseInt(nurikabeBoardPanel.get(i).getValue());
                numberOfLand += value;
            }
            if (nurikabeBoardPanel.get(i).isBlack()) {
                blackCells++;
            }
        }
        numberOfPossibleBlackCells = size - numberOfLand;
        return numberOfPossibleBlackCells == blackCells;
    }
    private boolean CheckBlackConnected() {
        boolean[][] visited = new boolean[sizeIn2D][sizeIn2D];
        int startRow = -1;
        int startCol = -1;

        for (int i = 0; i < nurikabeBoardPanel.size(); i++) {
            if(nurikabeBoardPanel.get(i).isBlack()){
                startRow = nurikabeBoardPanel.get(i).getLoc().y;
                startCol = nurikabeBoardPanel.get(i).getLoc().x;
                break;
            }
        }

        if (startRow == -1) {
            return true;
        }

        exploreBlackCells(startRow, startCol, visited);

        for (int row = 0; row < sizeIn2D; row++) {
            for (int col = 0; col < sizeIn2D; col++) {
                Cell cell = nurikabeBoardPanel.get(sizeIn2D*row + col);
                if (cell.isBlack() && !visited[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }
    private void exploreBlackCells(int row, int col, boolean[][] visited) {
        if (row < 0 || row >= sizeIn2D || col < 0 || col >= sizeIn2D || !nurikabeBoardPanel.get(row * sizeIn2D + col).isBlack() || visited[row][col]) {
            return;
        }

        visited[row][col] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            exploreBlackCells(newRow, newCol, visited);
        }
    }
    private void exploreBlackCellsForPaths(int row, int col, boolean[][] visited, List<SquareCell> squareCells) {
        if (row < 0 || row >= sizeIn2D || col < 0 || col >= sizeIn2D || visited[row][col]) {
            return;
        }
        if(nurikabeBoardPanel.get(row * sizeIn2D + col).isBlank()) squareCells.add(sqrOrButtonCells2D.get(row).get(col).squareCell);
        if(!nurikabeBoardPanel.get(row * sizeIn2D + col).isBlack()) return;
        visited[row][col] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            exploreBlackCellsForPaths(newRow, newCol, visited, squareCells);
        }
    }
    private void exploreWhiteCellsForPaths(int row, int col, boolean[][] visited, List<SquareCell> squareCells, int[] count) {
        if (row < 0 || row >= sizeIn2D || col < 0 || col >= sizeIn2D || visited[row][col]) {
            return;
        }
        if(nurikabeBoardPanel.get(row * sizeIn2D + col).isBlank()) squareCells.add(sqrOrButtonCells2D.get(row).get(col).squareCell);
        if(!nurikabeBoardPanel.get(row * sizeIn2D + col).isWhite()) return;
        visited[row][col] = true;
        count[0] = ++count[0];

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            exploreWhiteCellsForPaths(newRow, newCol, visited, squareCells,count);
        }
    }

    private boolean CheckBlackSquare() {
        boolean[][] visited = new boolean[sizeIn2D][sizeIn2D];

        for (int row = 0; row < sizeIn2D - 1; row++) {
            for (int col = 0; col < sizeIn2D - 1; col++) {
                if (nurikabeBoardPanel.get(row * sizeIn2D + col).isBlack() && !visited[row][col]) {
                    if (isBlackSquare(row, col, visited)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    private boolean isBlackSquare(int row, int col, boolean[][] visited) {

        if (row < 0 || row >= sizeIn2D - 1 || col < 0 || col >= sizeIn2D - 1 || !nurikabeBoardPanel.get(row * sizeIn2D + col).isBlack()) {
            return false;
        }

        return nurikabeBoardPanel.get((row + 1) * sizeIn2D + col).isBlack() &&
                nurikabeBoardPanel.get(row * sizeIn2D + col + 1).isBlack() &&
                nurikabeBoardPanel.get((row + 1) * sizeIn2D + col + 1).isBlack();
    }

    private boolean CheckNeededLandCells() {
        for (int row = 0; row < sizeIn2D; row++) {
            for (int col = 0; col < sizeIn2D; col++) {
                if (nurikabeBoardPanel.get(row * sizeIn2D + col).getValue() != null) {
                    boolean[][] visited = new boolean[sizeIn2D][sizeIn2D];
                    int neededWhiteCells = parseInt(nurikabeBoardPanel.get(row * sizeIn2D + col).getValue()) - 1;
                    int count = dfsCountWhiteCells(row, col, visited, -1);
                    if (count != neededWhiteCells) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private int dfsCountWhiteCells(int row, int col, boolean[][] visited, int count) {
        if (row < 0 || row >= sizeIn2D || col < 0 || col >= sizeIn2D || !nurikabeBoardPanel.get(row * sizeIn2D + col).isWhite() || visited[row][col]) {
            return count;
        }

        visited[row][col] = true;
        count++;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            count = dfsCountWhiteCells(newRow, newCol, visited, count);
        }

        return count;
    }
    private void checkForAdjacency(){
        for (int i = 0; i < nurikabeBoardPanel.size(); i++){
            if(nurikabeBoardPanel.get(i).getValue() != null){
                for (int j = i+1;j < nurikabeBoardPanel.size(); j++){
                    if(nurikabeBoardPanel.get(j).getValue() != null){
                        if(checkForHorizontalOrVerticalAdjacency(nurikabeBoardPanel.get(i), nurikabeBoardPanel.get(j))){
                            handleHorizontalOrVerticalAdjacency(nurikabeBoardPanel.get(i), nurikabeBoardPanel.get(j));
                        } else if (checkForDiagonalAdjacency(nurikabeBoardPanel.get(i), nurikabeBoardPanel.get(j))) {
                            handleDiagonalAdjacency(nurikabeBoardPanel.get(i), nurikabeBoardPanel.get(j));
                        }
                    }
                }
            }
        }
    }
    private boolean checkForHorizontalOrVerticalAdjacency(Cell cell1, Cell cell2){
        return (cell1.getLoc().x == cell2.getLoc().x && abs(cell1.getLoc().y - cell2.getLoc().y) == 2 || cell1.getLoc().y == cell2.getLoc().y && abs(cell1.getLoc().x - cell2.getLoc().x) == 2 );
    }
    private boolean checkForDiagonalAdjacency(Cell cell1, Cell cell2){
        return (abs(cell1.getLoc().x - cell2.getLoc().x) == 1 && abs(cell1.getLoc().y - cell2.getLoc().y) == 1);
    }
    private void handleDiagonalAdjacency(Cell cell1, Cell cell2){
        SquareCell squareCell = sqrOrButtonCells2D.get(cell1.getLoc().y).get(cell2.getLoc().x).squareCell;
        if(squareCell != null && squareCell.getSquareClickListener().square.cell.isBlank()) {
            squareCell.getSquareClickListener().square.changeColorForwards();
        }
        squareCell = sqrOrButtonCells2D.get(cell2.getLoc().y).get(cell1.getLoc().x).squareCell;
        if(squareCell != null && squareCell.getSquareClickListener().square.cell.isBlank()) {
            squareCell.getSquareClickListener().square.changeColorForwards();
        }
    }
    private void handleHorizontalOrVerticalAdjacency(Cell cell1, Cell cell2){
        SquareCell squareCell = sqrOrButtonCells2D.get((cell1.getLoc().y+cell2.getLoc().y)/2).get((cell1.getLoc().x+cell2.getLoc().x)/2).squareCell;
        if(!squareCell.getSquareClickListener().square.cell.isBlack()) {
            squareCell.getSquareClickListener().square.changeColorForwards();
        }
    }
    private void checkForLShapes(){
        int[] dr = {0,0,1,1};
        int[] dc = {0,1,1,0};
        for(int i = 0; i < nurikabeBoardPanel.size(); i++){
                int row = nurikabeBoardPanel.get(i).getLoc().y;
                int col = nurikabeBoardPanel.get(i).getLoc().x;
                int counter = 0;
                List<SquareCell> cells = new ArrayList<>();
                for(int j = 0; j < 4; j++){
                    int newRow = row + dr[j];
                    int newCol = col + dc[j];
                    if(!(newRow >= 0 && newRow < sqrOrButtonCells2D.size() && newCol >= 0 && newCol < sqrOrButtonCells2D.size()) || sqrOrButtonCells2D.get(newRow).get(newCol).squareCell == null){
                        continue;
                    }
                    if(sqrOrButtonCells2D.get(newRow).get(newCol).squareCell.getSquareClickListener().square.cell.isBlank()) {
                        cells.add(sqrOrButtonCells2D.get(newRow).get(newCol).squareCell);
                    }
                    if(sqrOrButtonCells2D.get(newRow).get(newCol).squareCell.getSquareClickListener().square.cell.isBlack()) {
                        counter++;
                    }
                }
                if(cells.size() == 1 && counter == 3) {
                    cells.get(0).getSquareClickListener().square.changeColorBackwards();
                }
        }
    }
    private void checkForBlackOnlyMoves(){
        for (int i = 0; i < nurikabeBoardPanel.size(); i++){
            if(nurikabeBoardPanel.get(i).isBlack()){
                int row = nurikabeBoardPanel.get(i).getLoc().y;
                int col = nurikabeBoardPanel.get(i).getLoc().x;
                boolean[][] visited = new boolean[sizeIn2D][sizeIn2D];
                List<SquareCell> squareCells = new ArrayList<>();
                exploreBlackCellsForPaths(row, col, visited, squareCells);
                if(squareCells.size() == 1){
                    squareCells.get(0).getSquareClickListener().square.changeColorForwards();
                }
            }
        }
    }
    private void checkForWhiteOnlyMoves(){
        for (int i = 0; i < nurikabeBoardPanel.size(); i++){
            if(nurikabeBoardPanel.get(i).getValue() != null){
                int row = nurikabeBoardPanel.get(i).getLoc().y;
                int col = nurikabeBoardPanel.get(i).getLoc().x;
                boolean[][] visited = new boolean[sizeIn2D][sizeIn2D];
                List<SquareCell> squareCells = new ArrayList<>();
                int[] count = {-1};
                exploreWhiteCellsForPaths(row, col, visited, squareCells, count);
                if(squareCells.size() == 1){
                    int neededWhiteCells = parseInt(nurikabeBoardPanel.get(row * sizeIn2D + col).getValue()) - 1;
                    if (count[0] < neededWhiteCells) {
                        squareCells.get(0).getSquareClickListener().square.changeColorBackwards();
                    }else{
                        squareCells.get(0).getSquareClickListener().square.changeColorForwards();
                    }

                }
            }
        }
    }
    private void closeOffIslands(){
        for (int row = 0; row < sizeIn2D; row++) {
            for (int col = 0; col < sizeIn2D; col++) {
                if (nurikabeBoardPanel.get(row * sizeIn2D + col).getValue() != null) {
                    boolean[][] visited = new boolean[sizeIn2D][sizeIn2D];
                    List<SquareCell> squareCells = new ArrayList<>();
                    int[] count = {-1};
                    int neededWhiteCells = parseInt(nurikabeBoardPanel.get(row * sizeIn2D + col).getValue()) - 1;
                    exploreWhiteCellsForPaths(row, col, visited, squareCells, count);
                    if (count[0] == neededWhiteCells) {
                        for(int i = 0; i < squareCells.size(); i++){
                            squareCells.get(i).getSquareClickListener().square.changeColorForwards();
                        }
                    }
                }
            }
        }
    }
    private void checkIf2WaysForIslands(){
        for (int row = 0; row < sizeIn2D; row++) {
            for (int col = 0; col < sizeIn2D; col++) {
                if (nurikabeBoardPanel.get(row * sizeIn2D + col).getValue() != null) {
                    boolean[][] visited = new boolean[sizeIn2D][sizeIn2D];
                    List<SquareCell> squareCells = new ArrayList<>();
                    int[] count = {-1};
                    int neededWhiteCells = parseInt(nurikabeBoardPanel.get(row * sizeIn2D + col).getValue()) - 1;
                    exploreWhiteCellsForPaths(row, col, visited, squareCells, count);
                    if (count[0] == neededWhiteCells-1 && squareCells.size() == 2) {
                        if(checkForDiagonalAdjacency(squareCells.get(0).getSquareClickListener().square.cell, squareCells.get(1).getSquareClickListener().square.cell)){
                            handleDiagonalAdjacency(squareCells.get(0).getSquareClickListener().square.cell, squareCells.get(1).getSquareClickListener().square.cell);
                        }
                    }
                }
            }
        }
    }

    public String CheckSolved(){
        if(!CheckPossibleBlackCells()){
            return "Black cells missing";
        }
        else if(!CheckNeededLandCells()){
            return "Land cells missing";
        }
        else if (!CheckBlackConnected()) {
            return "Water should be connected";
        }
        else if(!CheckBlackSquare()){
            return "There cannot be the square in solution";
        }
        return "Well Done!";
    }
    public void solve(){
        checkForAdjacency();
        checkForLShapes();
        checkForBlackOnlyMoves();
        checkForWhiteOnlyMoves();
        closeOffIslands();
        checkIf2WaysForIslands();
    }

}