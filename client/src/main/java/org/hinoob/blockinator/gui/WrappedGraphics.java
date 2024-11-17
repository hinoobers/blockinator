package org.hinoob.blockinator.gui;

import java.awt.*;

public class WrappedGraphics {

    private final Graphics graphics;

    public WrappedGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public void drawRect(int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }

    public void drawString(String text, int x, int y, int size, Color color) {
        graphics.setColor(color);
        graphics.setFont(new Font("Arial", Font.PLAIN, size));
        graphics.drawString(text, x, y);
    }
}
