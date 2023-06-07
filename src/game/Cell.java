package game;

import java.awt.*;

public class Cell {
    private final Point loc;
    private Integer state;
    private String value;

    public Cell(Point loc, int state) {
        this.loc = loc;
        this.state = state;
    }

    public boolean isWhite() {
        return this.state.equals(2);
    }

    public boolean isBlack() {
        return this.state.equals(1);
    }

    public boolean isBlank() {
        return this.state.equals(0);
    }

    public Point getLoc() {
        return loc;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
