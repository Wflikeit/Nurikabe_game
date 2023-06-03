package game;

import java.awt.*;

public class NumberCell extends Cell{
    public NumberCell(Point loc, int state, String value) {
        super(loc, state);
        this.value = value;
    }

    @Override
    public Point getLoc() {
        return super.getLoc();
    }
    public String getValue(){
        return value;
    }

    public String value;
    public void isAreaEqualToValue(){}
    public Point coordinates ;
}
