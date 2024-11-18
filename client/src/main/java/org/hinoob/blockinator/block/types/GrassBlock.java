package org.hinoob.blockinator.block.types;

import com.google.gson.JsonObject;
import org.hinoob.blockinator.GlobalProperties;
import org.hinoob.blockinator.block.Block;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.util.TwoBox;

import java.awt.*;

public class GrassBlock extends Block {

    public GrassBlock() {
        super("grass");
    }

    @Override
    public Block newInstance() {
        return new GrassBlock();
    }

    @Override
    public void render(WrappedGraphics graphics) {
        //System.out.print("Drawing x=" + getX() + " y=" + getY() + "\n");
        graphics.drawRect(getX() * 50, getY() * 50, 50, 50, Color.green);
    }

    @Override
    public TwoBox getBoundingBox() {
        return new TwoBox(getX() * 50, getY() * 50, GlobalProperties.BLOCK_SIZE, GlobalProperties.BLOCK_SIZE);
    }
}
