package game.GUI.GamePanel;

import java.awt.*;

public class SquareCell implements GameBoardCell {
    private final SquareClickListener.Square square;

    public SquareCell() {
        square = new SquareClickListener.Square();
        square.setColor(Color.WHITE);
        square.addMouseListener(new SquareClickListener(square));
    }

    @Override
    public Component getComponent() {
        return square;
    }
}
