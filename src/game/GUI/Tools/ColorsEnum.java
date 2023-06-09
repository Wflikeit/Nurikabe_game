package game.GUI.Tools;

import java.awt.*;

public enum ColorsEnum {
    BUTTON_COLOR_2(0x86B38F),
    COLOR_3(0xC2CDFF),
    BUTTON_COLOR(0xD2D1FF),
    COLOR_4(0xFFF7EF),
    COLOR_5(0x8E8AB3);

    private final int rgb;

    ColorsEnum(int rgb) {
        this.rgb = rgb;
    }

    public Color getColor() {
        return new Color(rgb);
    }
}