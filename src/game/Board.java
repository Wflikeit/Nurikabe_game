package game;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private final int size;
    private final String level;
    public List<NumberCell> numbList = new ArrayList<>();
    List<Cell> nurikabeBoardPanel = new ArrayList<>();
    /**
     * limit of one sized "islands" - it is indicator of difficulty level
     */
    private int oneLimit;

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
                nurikabeBoardPanel.add(new Cell(new Point(i, j), 0));
            }
        }
//        setLevel(level);
    }

    public static void main(String[] args) {
        Board board = new Board(15, "easy");
        board.setLevel("easy");
        board.createBoard();
    }

    public void createBoard() {
        int index = ThreadLocalRandom.current().nextInt(0, size * size);
        nurikabeBoardPanel.get(index).setState(1);
        Queue<Integer> q = new LinkedList<>();
        q.offer(index);
        while (!q.isEmpty()) {
            index = q.poll();
            expandToSides(index, q);
        }
        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isBlank()) {
                nurikabeBoardPanel.get(i).setState(2);
            }
        }
        while (maxSize() > size || maxSize() > 13) {
            fixBigWhiteArea();
            if (findExpandable() >= 0) {
                index = findExpandable();
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
            for (int i = 0; i < size * size; i++) {
                if (nurikabeBoardPanel.get(i).isBlank()) {
                    nurikabeBoardPanel.get(i).setState(2);
                }
            }
            mergeSigleToOne();
        }
        assignNumbers();

    }

    public void setLevel(String level) {
        switch (level) {
            case "easy": {
                oneLimit = 0;
                break;
            }
            case "medium": {
                oneLimit = size;
                break;
            }
            case "hard": {
                oneLimit = size * size;
                break;
            }
        }
    }

    private List<Character> availableDir(int index) {
        List<Character> direction = new ArrayList<>();

        if (index % size - 1 >= 0 && nurikabeBoardPanel.get(index - 1).isBlank()) {
            direction.add('l');
        }
        if (index - size >= 0 && nurikabeBoardPanel.get(index - size).isBlank()) {
            direction.add('u');
        }
        if (index % size + 1 <= size - 1 && nurikabeBoardPanel.get(index + 1).isBlank()) {
            direction.add('r');
        }
        if (index + size <= size * size - 1 && nurikabeBoardPanel.get(index + size).isBlank()) {
            direction.add('d');
        }

        return direction;
    }

    private boolean willBeBlackSquare(int index) {
        if (index % size - 1 >= 0 && nurikabeBoardPanel.get(index - 1).isBlack() &&
                index - size >= 0 && nurikabeBoardPanel.get(index - size).isBlack() && nurikabeBoardPanel.get(index - size - 1).isBlack()) {
            return true;
        } else if (index - size >= 0 && nurikabeBoardPanel.get(index - size).isBlack() &&
                index % size + 1 <= size - 1 && nurikabeBoardPanel.get(index + 1).isBlack() && nurikabeBoardPanel.get(index - size + 1).isBlack()) {
            return true;
        } else if (index % size + 1 <= size - 1 && nurikabeBoardPanel.get(index + 1).isBlack() &&
                index + size <= size * size - 1 && nurikabeBoardPanel.get(index + size).isBlack() && nurikabeBoardPanel.get(index + size + 1).isBlack()) {
            return true;
        } else return index + size <= size * size - 1 && nurikabeBoardPanel.get(index + size).isBlack() &&
                index % size - 1 >= 0 && nurikabeBoardPanel.get(index - 1).isBlack() && nurikabeBoardPanel.get(index + size - 1).isBlack();
    }

    private boolean willBeWhiteSquare(int index) {
        if (index % size - 1 >= 0 && nurikabeBoardPanel.get(index - 1).isWhite() &&
                index - size >= 0 && nurikabeBoardPanel.get(index - size).isWhite() && nurikabeBoardPanel.get(index - size - 1).isWhite()) {
            return true;
        } else if (index - size >= 0 && nurikabeBoardPanel.get(index - size).isWhite() &&
                index % size + 1 <= size - 1 && nurikabeBoardPanel.get(index + 1).isWhite() && nurikabeBoardPanel.get(index - size + 1).isWhite()) {
            return true;
        } else if (index % size + 1 <= size - 1 && nurikabeBoardPanel.get(index + 1).isWhite() &&
                index + size <= size * size - 1 && nurikabeBoardPanel.get(index + size).isWhite() && nurikabeBoardPanel.get(index + size + 1).isWhite()) {
            return true;
        } else return index + size <= size * size - 1 && nurikabeBoardPanel.get(index + size).isWhite() &&
                index % size - 1 >= 0 && nurikabeBoardPanel.get(index - 1).isWhite() && nurikabeBoardPanel.get(index + size - 1).isWhite();
    }

    private void expandToSides(int index, Queue<Integer> q) {
        int holder = index;
        List<Character> direction = availableDir(holder);
        for (Character character : direction) {
            int local_index = holder;
            switch (character) {
                case 'u': {
                    local_index = local_index - size;
                    break;
                }
                case 'l': {
                    local_index = local_index - 1;
                    break;
                }
                case 'd': {
                    local_index = local_index + size;
                    break;
                }
                case 'r': {
                    local_index = local_index + 1;
                    break;
                }
                default:
                    break;
            }
            if (willBeBlackSquare(local_index)) {
                nurikabeBoardPanel.get(local_index).setState(2);
            } else if (ThreadLocalRandom.current().nextInt(100) >= 75) {
                if (isExpandable(q) && !willBeWhiteSquare(local_index)) {
                    nurikabeBoardPanel.get(local_index).setState(2);
                } else {
                    nurikabeBoardPanel.get(local_index).setState(1);
                    index = local_index;
                    q.offer(index);
                }
            } else {
                nurikabeBoardPanel.get(local_index).setState(1);
                index = local_index;
                q.offer(index);
            }
        }
    }

    private boolean isConnected(int index) {
        if (index % size - 1 >= 0 && nurikabeBoardPanel.get(index - 1).isBlack()) {
            return true;
        }
        if (index - size >= 0 && nurikabeBoardPanel.get(index - size).isBlack()) {
            return true;
        }
        if (index % size + 1 <= size - 1 && nurikabeBoardPanel.get(index + 1).isBlack()) {
            return true;
        }
        return index + size <= size * size - 1 && nurikabeBoardPanel.get(index + size).isBlack();
    }

    private int findExpandable() {
        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isBlank()) {
                if (availableDir(i).size() > 0 && isConnected(i)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private List<AbstractMap.SimpleEntry<Integer, List<Integer>>> countWhiteAreaSizes() {
        int counter;
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> whiteAreas = new ArrayList<>();
        List<Cell> copy = nurikabeBoardPanel;
        for (int i = 0; i < size * size; i++) {
            if (copy.get(i).isWhite()) {
                List<Integer> listOfIndexes = new ArrayList<>(i);
                counter = 1;
                Set<Integer> neigh = new TreeSet<>();
                listOfIndexes.add(i);
                getWhiteNeigh(i, copy, neigh);
                copy.get(i).setState(0);
                while (!neigh.isEmpty()) {
                    counter++;
                    int index = neigh.stream().toList().get(0);
                    listOfIndexes.add(index);
                    copy.get(index).setState(0);
                    neigh.remove(index);
                    getWhiteNeigh(index, copy, neigh);
                }
                AbstractMap.SimpleEntry<Integer, List<Integer>> temp = new AbstractMap.SimpleEntry<>(counter, listOfIndexes);
                whiteAreas.add(temp);
            }
        }
        for (int i = 0; i < size * size; i++) {
            if (nurikabeBoardPanel.get(i).isBlank()) {
                nurikabeBoardPanel.get(i).setState(2);
            }
        }
        return whiteAreas;
    }

    private void getWhiteNeigh(Integer index, List<Cell> arr, Set<Integer> neigh) {
        if (index % size - 1 >= 0 && arr.get(index - 1).isWhite()) {
            neigh.add(index - 1);
        }
        if (index - size >= 0 && arr.get(index - size).isWhite()) {
            neigh.add(index - size);
        }
        if (index % size + 1 <= size - 1 && arr.get(index + 1).isWhite()) {
            neigh.add(index + 1);
        }
        if (index + size <= size * size - 1 && arr.get(index + size).isWhite()) {
            neigh.add(index + size);
        }
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

    private boolean isExpandable(Queue<Integer> q) {
        int counter = 0;
        for (int i = 0; i < q.size(); i++) {
            List<Character> direction = availableDir(q.peek());
            if (direction.size() > 0) {
                counter++;
            }
            if (counter > 1) {
                return true;
            }
        }
        return false;
    }

    private void assignNumbers() {
    List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = countWhiteAreaSizes();
        for (AbstractMap.SimpleEntry<Integer, List<Integer>> area : areas) {
            int index = area.getValue().get(ThreadLocalRandom.current().nextInt(area.getValue().size()));
            int value = area.getKey();
            nurikabeBoardPanel.get(index).setValue(Integer.toString(value));


        }
    }

    private boolean checkCohesion() {
        int counter;
        List<Integer> black = new ArrayList<>();
        List<Cell> copy = nurikabeBoardPanel;
        for (int i = 0; i < size * size; i++) {
            if (copy.get(i).isBlack()) {
                counter = 1;
                Set<Integer> neigh = new TreeSet<>();
                getBlackNeigh(i, copy, neigh);
                copy.get(i).setState(0);
                while (!neigh.isEmpty()) {
                    counter++;
                    int index = neigh.stream().toList().get(0);
                    copy.get(index).setState(0);
                    neigh.remove(index);
                    getBlackNeigh(index, copy, neigh);
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

    private void getBlackNeigh(Integer index, List<Cell> arr, Set<Integer> neigh) {
        if (index % size - 1 >= 0 && arr.get(index - 1).isBlack()) {
            neigh.add(index - 1);
        }
        if (index - size >= 0 && arr.get(index - size).isBlack()) {
            neigh.add(index - size);
        }
        if (index % size + 1 <= size - 1 && arr.get(index + 1).isBlack()) {
            neigh.add(index + 1);
        }
        if (index + size <= size * size - 1 && arr.get(index + size).isBlack()) {
            neigh.add(index + size);
        }
    }

    private void mergeSigleToOne() {
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = countWhiteAreaSizes();
        int counter = 0;
        for (int i = 0; i < areas.size(); i++) {
            if (counter >= oneLimit) {
                break;
            }
            for (int j = 0; j < areas.size(); j++) {
                if (counter >= oneLimit) {
                    break;
                }
                if (i != j && areas.get(i).getKey() == 1 && areas.get(j).getKey() == 1) {
                    if (Math.abs(areas.get(i).getValue().get(0) - areas.get(j).getValue().get(0)) == 2) {
                        if (areas.get(i).getValue().get(0) > areas.get(j).getValue().get(0)) {
                            nurikabeBoardPanel.get(areas.get(i).getValue().get(0) - 1).setState(2);
                            counter++;
                            if (!checkCohesion()) {
                                nurikabeBoardPanel.get(areas.get(i).getValue().get(0) - 1).setState(1);
                                counter--;
                            }
                        } else {
                            nurikabeBoardPanel.get(areas.get(i).getValue().get(0) + 1).setState(2);
                            counter++;
                            if (!checkCohesion()) {
                                nurikabeBoardPanel.get(areas.get(i).getValue().get(0) + 1).setState(1);
                                counter--;
                            }
                        }
                    }
                    if (Math.abs(areas.get(i).getValue().get(0) - areas.get(j).getValue().get(0)) % size == 0) {
                        if (areas.get(i).getValue().get(0) > areas.get(j).getValue().get(0)) {
                            nurikabeBoardPanel.get(areas.get(i).getValue().get(0) - size).setState(2);
                            counter++;
                            if (!checkCohesion()) {
                                nurikabeBoardPanel.get(areas.get(i).getValue().get(0) - size).setState(1);
                                counter--;
                            }
                        } else {
                            nurikabeBoardPanel.get(areas.get(i).getValue().get(0) + size).setState(2);
                            counter++;
                            if (!checkCohesion()) {
                                nurikabeBoardPanel.get(areas.get(i).getValue().get(0) + size).setState(1);
                                counter--;
                            }
                        }
                    }
                }
            }
        }
    }

    public void loadBoard() {

    }

    public void saveBoard() {
    }

    public void print() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(nurikabeBoardPanel.get(i * size + j).getState());
                System.out.print("  ");
            }
            System.out.print("\n");
        }
    }

    public int getOneLimit() {
        return oneLimit;
    }
}