package org.hinoob.blockinator.gui.types;

import org.hinoob.blockinator.gui.Renderer;

import java.awt.event.KeyEvent;

public abstract class Element implements Renderer {

    protected int x, y, width, height;
    public boolean focused = false;

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        focused = isHovered(mouseX, mouseY);
        if(focused) {
            _handleClick(mouseX, mouseY, mouseButton);
        }
    }

    public void handleKeyTyped(KeyEvent event) {
        if(focused) {
            _handleKeyTyped(event);
        }
    }

    public abstract void _handleClick(int mouseX, int mouseY, int mouseButton);
    public abstract void _handleKeyTyped(KeyEvent event);

}
