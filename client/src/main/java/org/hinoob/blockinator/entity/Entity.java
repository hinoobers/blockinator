package org.hinoob.blockinator.entity;

import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.world.World;

import java.awt.*;

public abstract class Entity implements Renderer {

    protected int x, y, width = 50, height = 50, section = 0;
    protected World world;
    public int entityId;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSection() {
        return section;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;

        // Width is 800
        // Check if they wont overlap, dont change x
        if(this.x < (section * 800)) {
            this.section--;

            System.out.println("Section: " + section);
        } else if(this.x + width > (section * 800) + 800) {
            this.section++;

            System.out.println("Section: " + section);
        }
    }

    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(WrappedGraphics graphics) {
        // Render dependent on section
        graphics.drawRect(x - (section * 800), y, width, height, Color.red);
    }
}
