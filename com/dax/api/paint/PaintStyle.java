package com.dax.api.paint;

import java.awt.Color;

public enum PaintStyle {

    DEFAULT(
            Color.WHITE,
            new Color(255, 197,0),
            Color.RED,
            Color.BLACK,
            new Color(0, 0, 0, 80),
            new Color(0, 0, 0, 180)
    ),
    BRIGHT(
            Color.WHITE,
            new Color(255, 57, 127),
            new Color(186, 161, 142),
            new Color(67, 215, 214),
            new Color(255, 255, 255, 40),
            new Color(51, 0, 80, 150)
    ),
    COLORFUL(
            Color.WHITE,
            Color.YELLOW,
            Color.RED,
            Color.BLACK,
            new Color(0, 0, 0, 80),
            new Color(0, 0, 0, 150)
    );

    private Color stringText;
    private Color numberText;
    private Color infoText;
    private Color border;
    private Color fill;
    private Color strongFill;

    PaintStyle(Color stringText, Color numberText, Color infoText, Color borders, Color fill, Color strongFill) {
        this.stringText = stringText;
        this.numberText = numberText;
        this.infoText = infoText;
        this.border = borders;
        this.fill = fill;
        this.strongFill = strongFill;
    }

    public Color getStringText() {
        return stringText;
    }

    public Color getNumberText() {
        return numberText;
    }

    public Color getInfoText() {
        return infoText;
    }

    public Color getBorder() {
        return border;
    }

    public Color getFill() {
        return fill;
    }

    public Color getStrongFill() {
        return strongFill;
    }
}
