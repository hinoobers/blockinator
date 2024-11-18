package org.hinoob.blockinator.entity;

import org.hinoob.blockinator.block.Block;
import org.hinoob.blockinator.block.types.GrassBlock;
import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.WrappedGraphics;

import java.awt.*;

public class Player extends Entity {

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void preRender(WrappedGraphics graphics) {
        if(this.world == null) return; // TODO: Must remove

        boolean collision = false;
        for(Block block : this.world.getBlocks(this.section)) {
            if(block.getBoundingBox().intersects(x - (section * 800), this.y, this.width, this.height)) {
                collision = true;
                break;
            }
        }

        if(!collision) {
            move(0, 10);
        }
    }

    @Override
    public void render(WrappedGraphics graphics) {
        super.render(graphics);

        graphics.drawString("Player", x - (section * 800), y - 10, 20, Color.black);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
