package game;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    public List<Cell> getNurikabeBoardPanel() {
        return nurikabeBoardPanel;
    }

    public final int size;
    private final List<Cell> nurikabeBoardPanel = new ArrayList<>();
    /**
     * limit of one sized "islands" - it is indicator of difficulty level
     */
    private double oneLimit;
    private final String level;
    /**
     * 0 - means that a cell is blank
     * 1 - means that a cell is black
     * 2 - means that a cell is white
     */
    public Board(int size, String level) {
        this.size = size;
        this.level = level;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                nurikabeBoardPanel.add(new Cell(new Point(j, i), 0));
            }
        }
        setLevel(level);
    }
    public void fillBoard() {
        int index = ThreadLocalRandom.current().nextInt(0, size * size);
        nurikabeBoardPanel.get(index).setState(1);
        Queue<Integer> q = new LinkedList<>();
        q.offer(index);
        while (!q.isEmpty()) {
            index = q.poll();
            expandToSides(index, q);
        }

        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isBlank()){nurikabeBoardPanel.get(i).setState(2);}
        }
        int counter =0;
        while (maxSize() > size || maxSize() > 13&&counter>5) {
            counter+=1;
            fixBigWhiteArea();
            index = findExpandable();
            if (index >= 0) {
                q.offer(index);
            }
            while (findExpandable() >= 0) {
                while (!q.isEmpty()) {
                    index = q.poll();
                    expandToSides(index, q);
                    if (q.isEmpty() && findExpandable() > 0) {
                        index = findExpandable();
                        q.offer(index);
                    }
                }
            }
            for (Cell cell : nurikabeBoardPanel) {
                if (cell.isBlank()) {
                    cell.setState(2);
                }
            }
            mergeSingleToOne();
            fixWhiteSquares();
        }
        if(counter>5) {
            flushBoard();
            fillBoard();
        }else {
            mergeSingleToOne();
            fixWhiteSquares();
            assignNumbers();
        }
    }
    private void flushBoard() {
        for(int i=0;i<size*size;i++){
            nurikabeBoardPanel.get(i).setState(0);
        }
    }
    /**
     * @param level a String object representing a level of difficulty of board
     */
    public void setLevel(String level) {
        switch (level) {
            case "Easy" -> oneLimit = 0;
            case "Hard" -> oneLimit = size;
            case "Hell" -> oneLimit = size * size;
        }
    }
    public String getLevel(){return level;}
    /**
     * @param index int that represents an index
     * @return an arrayList that contains characters representing possible directions
     */
    private List<Character> availableDir(int index) {
        List<Character> direction = new ArrayList<>();

        if ((index % size) - 1 >= 0 && nurikabeBoardPanel.get(index - 1).isBlank()) {
            direction.add('l');
        }
        if (index - size >= 0 && nurikabeBoardPanel.get(index - size).isBlank()) {
            direction.add('u');
        }
        if ((index % size) + 1 <= size - 1 && nurikabeBoardPanel.get(index + 1).isBlank()) {
            direction.add('r');
        }
        if (index + size <= size * size - 1 && nurikabeBoardPanel.get(index + size).isBlank()) {
            direction.add('d');
        }
        return direction;
    }
    public boolean willBeSquare(int index, int color) {
        int row = index / size;
        int col = index % size;
        boolean isLeftEdge = col > 0;
        boolean isRightEdge = col < size - 1;
        boolean isTopEdge = row > 0;
        boolean isBottomEdge = row < size - 1;
        if (isLeftEdge && isTopEdge &&
                nurikabeBoardPanel.get(index - size - 1).getState() == color &&
                nurikabeBoardPanel.get(index - size).getState() == color &&
                nurikabeBoardPanel.get(index - 1).getState() == color) {
            return true;
        } else if (isTopEdge && isRightEdge &&
                nurikabeBoardPanel.get(index - size).getState() == color &&
                nurikabeBoardPanel.get(index - size + 1).getState() == color &&
                nurikabeBoardPanel.get(index + 1).getState() == color) {
            return true;
        } else if (isRightEdge && isBottomEdge &&
                nurikabeBoardPanel.get(index + 1).getState() == color &&
                nurikabeBoardPanel.get(index + size + 1).getState() == color &&
                nurikabeBoardPanel.get(index + size).getState() == color) {
            return true;
        } else return isBottomEdge && isLeftEdge &&
                nurikabeBoardPanel.get(index + size).getState() == color &&
                nurikabeBoardPanel.get(index + size - 1).getState() == color &&
                nurikabeBoardPanel.get(index - 1).getState() == color;
    }


    private void expandToSides(int index, Queue<Integer> q) {
        int holder = index;
        List<Character> direction = availableDir(holder);
        for (Character character : direction) {
            int local_index = holder;
            switch (character) {
                case 'u' -> local_index = local_index - size;
                case 'l' -> local_index = local_index - 1;
                case 'd' -> local_index = local_index + size;
                case 'r' -> local_index = local_index + 1;
            }
            if (willBeSquare(local_index,1)) {
                nurikabeBoardPanel.get(local_index).setState(2);
            } else if (ThreadLocalRandom.current().nextInt(100) >= 75) {
                nurikabeBoardPanel.get(local_index).setState(2);
            } else {
                nurikabeBoardPanel.get(local_index).setState(1);
                index = local_index;
                q.offer(index);
            }
        }
    }
    private int findExpandable() {
        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isBlack()) {
                if (availableDir(i).size() > 0) {
                    return i;
                }
            }
        }
        return -1;
    }
    public List<AbstractMap.SimpleEntry<Integer, List<Integer>>> countWhiteAreaSizes() {
        int counter;
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> whiteAreas = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isWhite()) {
                List<Integer> listOfIndexes = new ArrayList<>(i);
                counter = 1;
                Set<Integer> neigh = new TreeSet<>();
                listOfIndexes.add(i);
                getNeighbour(i, nurikabeBoardPanel, neigh,2);
                nurikabeBoardPanel.get(i).setState(0);
                while (!neigh.isEmpty()) {
                    counter++;
                    int index = neigh.stream().toList().get(0);
                    listOfIndexes.add(index);
                    nurikabeBoardPanel.get(index).setState(0);
                    neigh.remove(index);
                    getNeighbour(index, nurikabeBoardPanel, neigh,2);
                }
                AbstractMap.SimpleEntry<Integer, List<Integer>> temp = new AbstractMap.SimpleEntry<>(counter, listOfIndexes);
                whiteAreas.add(temp);
            }
        }
        for (int i = 0; i < size * size; i++) {
            if (this.nurikabeBoardPanel.get(i).isBlank()) {
                this.nurikabeBoardPanel.get(i).setState(2);
            }
        }
        return whiteAreas;
    }
    private void fixBigWhiteArea() {
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = countWhiteAreaSizes();
        for (AbstractMap.SimpleEntry<Integer, List<Integer>> area : areas) {
            if (area.getKey() > 0.8 * size || area.getKey() > 13) {
                for (int j = 0; j < area.getValue().size(); j++) {
                    nurikabeBoardPanel.get(area.getValue().get(j)).setState(0);
                }
            }
        }
    }

    private int maxSize() {
        int max = 0;
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = countWhiteAreaSizes();
        for (AbstractMap.SimpleEntry<Integer, List<Integer>> area : areas) {
            if (area.getKey() > max) {
                max = area.getKey();
            }
        }
        return max;
    }
    private void assignNumbers() {
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = countWhiteAreaSizes();
        for (AbstractMap.SimpleEntry<Integer, List<Integer>> area : areas) {
            int index = area.getValue().get(ThreadLocalRandom.current().nextInt(area.getValue().size()));
            int value = area.getKey();
            nurikabeBoardPanel.get(index).setValue(Integer.toString(value));
        }
    }
    public boolean checkCohesion() {
        int counter;
        List<Integer> black = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isBlack()) {
                counter = 1;
                Set<Integer> neigh = new TreeSet<>();
                getNeighbour(i, nurikabeBoardPanel, neigh,1);
                nurikabeBoardPanel.get(i).setState(0);
                while (!neigh.isEmpty()) {
                    counter++;
                    int index = neigh.stream().toList().get(0);
                    nurikabeBoardPanel.get(index).setState(0);
                    neigh.remove(index);
                    getNeighbour(index, nurikabeBoardPanel, neigh,1);
                }
                black.add(counter);
            }
        }
        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isBlank()) {
                nurikabeBoardPanel.get(i).setState(1);
            }
        }
        return black.size() == 1;
    }
    private void getNeighbour(Integer index, List<Cell> arr, Set<Integer> neighbours, int color) {
        if (index % size - 1 >= 0 && arr.get(index - 1).getState()==color) {
            neighbours.add(index - 1);
        }
        if (index - size >= 0 && arr.get(index - size).getState()==color) {
            neighbours.add(index - size);
        }
        if (index % size + 1 <= size - 1 && arr.get(index + 1).getState()==color) {
            neighbours.add(index + 1);
        }
        if (index + size <= size * size - 1 && arr.get(index + size).getState()==color) {
            neighbours.add(index + size);
        }
    }
    private void mergeSingleToOne() {
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = countWhiteAreaSizes();
        int counter = 0;
        for (int i = 0; i < areas.size() && counter < oneLimit; i++) {
            for (int j = 0; j < areas.size() && counter < oneLimit; j++) {
                if (i != j && areas.get(i).getKey() == 1 && areas.get(j).getKey() == 1) {
                    int diff = Math.abs(areas.get(i).getValue().get(0) - areas.get(j).getValue().get(0));
                    int index = areas.get(i).getValue().get(0);
                    if (diff == 2) {
                        int adjacentIndex = areas.get(i).getValue().get(0) > areas.get(j).getValue().get(0) ? index - 1 : index + 1;
                        updateStateAndCounter(adjacentIndex, counter);
                    } else if (diff % size == 0) {
                        int adjacentIndex = areas.get(i).getValue().get(0) > areas.get(j).getValue().get(0) ? index - size : index + size;
                        updateStateAndCounter(adjacentIndex, counter);
                    }
                }
            }
        }
    }

    private void updateStateAndCounter(int index, int counter) {
        nurikabeBoardPanel.get(index).setState(2);
        counter++;
        if (!checkCohesion()) {
            nurikabeBoardPanel.get(index).setState(1);
            counter--;
        }
    }
    private void fixWhiteSquares(){
        for(int i=0;i<size*size;i++){
            if(nurikabeBoardPanel.get(i).isWhite() && willBeSquare(i,2) &&!willBeSquare(i,1)){
                nurikabeBoardPanel.get(i).setState(1);
                if(!checkCohesion()){nurikabeBoardPanel.get(i).setState(2);}
            }
        }
    }
    public void copyBoard(Board otherBoard){
        for(int i=0;i<size*size;i++){
            otherBoard.getNurikabeBoardPanel().get(i).setState(nurikabeBoardPanel.get(i).getState());
        }
    }
}