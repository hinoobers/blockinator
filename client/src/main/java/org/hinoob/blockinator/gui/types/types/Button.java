package org.hinoob.blockinator.gui.types.types;

import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.gui.types.Element;
import org.hinoob.blockinator.gui.types.Focusable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Button extends Element implements Focusable {

    private ClickListener listener;

    public Button(int x, int y, int width, int height, ClickListener listener) {
        super(x, y, width, height);

        this.listener = listener;
    }


    @Override
    public void _handleClick(int mouseX, int mouseY, int mouseButton) {
        if(listener != null)
            listener.onClick();
    }

    @Override
    public void _handleKeyTyped(KeyEvent event) {

    }

    @Override
    public void render(WrappedGraphics graphics) {
        graphics.drawRect(x, y, width, height, focused ? Color.red : Color.white);
        graphics.drawString("Button", x + 5, y + height / 2, 20, Color.black);
    }

    public interface ClickListener {
        void onClick();
    }
}
