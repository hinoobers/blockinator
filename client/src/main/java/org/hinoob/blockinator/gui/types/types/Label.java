package org.hinoob.blockinator.gui.types.types;

import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.gui.types.Element;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Label extends Element {

    private String text;

    public Label(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void _handleClick(int mouseX, int mouseY, int mouseButton) {

    }

    public void _handleKeyTyped(KeyEvent event) {

    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void render(WrappedGraphics graphics) {
        graphics.drawString(text, x, y, 20, Color.white);
    }
}
