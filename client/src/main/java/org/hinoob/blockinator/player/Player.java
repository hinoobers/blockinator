package org.hinoob.blockinator.player;

import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.WrappedGraphics;

import java.awt.*;

public class Player implements Renderer {

    public int x, y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void render(WrappedGraphics graphics) {
        graphics.drawRect(x, y, 50, 50, Color.black);
    }
}
