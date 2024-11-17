package org.hinoob.blockinator.gui.types.types;

import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.gui.types.Element;
import org.hinoob.blockinator.gui.types.Focusable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class InputBox extends Element implements Focusable {

    private String value, placeholder;

    public InputBox(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void _handleClick(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void _handleKeyTyped(KeyEvent event) {
        if(value == null)
            value = "";
        value += event.getKeyChar();
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void render(WrappedGraphics graphics) {
        graphics.drawRect(x, y, width, height, Color.white);
        if(value == null)
            graphics.drawString(placeholder, x + 5, y + height / 2, 20, Color.gray);
        else
            graphics.drawString(value, x + 5, y + height / 2, 20, Color.black);
    }

}
