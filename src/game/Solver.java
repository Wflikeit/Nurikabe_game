package game;

import javax.swing.*;
import java.util.AbstractMap;
import java.util.List;

public class Solver {
    private final Board solvedBoard;
    private final Board board;

    public Solver(Board board, Board solvedBoard) {
        this.board = board;
        this.solvedBoard = solvedBoard;
    }

    private boolean checkForBlackSquares(){
        for(int i=0;i<board.size*board.size;i++){
            if(board.getNurikabeBoardPanel().get(i).isBlack()&&board.willBeSquare(i,1)){
                return false;
            }
        }
        return true;
    }
    private boolean checkNumberOfWhiteAreas() {
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = board.countWhiteAreaSizes();
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areasSolved = solvedBoard.countWhiteAreaSizes();
        return (areas.size() == areasSolved.size());
    }

    private boolean checkSizeOfWhiteAreas() {
        int counter =0;
        List<AbstractMap.SimpleEntry<Integer, List<Integer>>> areas = board.countWhiteAreaSizes();
        for (AbstractMap.SimpleEntry<Integer, List<Integer>> area : areas) {
            for(int i=0;i<area.getValue().size();i++){
                if(board.getNurikabeBoardPanel().get(area.getValue().get(i)).getValue()!=null) {
                    int value = Integer.parseInt(board.getNurikabeBoardPanel().get(area.getValue().get(i)).getValue());
                    if (value != 0 && value == area.getKey()) {
                        counter++;
                    }
                }
            }
        }
        return counter == areas.size();
    }
    public void checkSolved(){
        if(!checkForBlackSquares()){
            JOptionPane.showMessageDialog(null, "There cannot be black squares in solution!");
        }else if(!board.checkCohesion()){
            JOptionPane.showMessageDialog(null, "Black field should be coherent!");
        }else if(!checkNumberOfWhiteAreas()){
            JOptionPane.showMessageDialog(null, "The number of white areas is wrong!");
        }else if(!checkSizeOfWhiteAreas()){
            JOptionPane.showMessageDialog(null, "The size of white areas is wrong!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Well done!");
        }
    }
}
