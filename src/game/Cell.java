package game;

public class Cell {
    private final int k;
    private Integer state;
    public int[][] coordinates;

    public Cell(int k, int state) {
        this.k = k;
        this.state = state;
    }

    public boolean isWhite() {
        if (this.state.equals('0')) {
            return true;
        } else {
            return false;
        }
    }

    public void isBlack() {
    }

    public void isBlank() {

    }

    private void containNumber() {
    }

    public int getK() {
        return k;
    }

    public Integer getState() {
        isBlank();
        isBlack();
        isWhite();

        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
