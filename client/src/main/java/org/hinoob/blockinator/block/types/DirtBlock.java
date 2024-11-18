package org.hinoob.blockinator.block.types;

import org.hinoob.blockinator.GlobalProperties;
import org.hinoob.blockinator.block.Block;
import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.util.TwoBox;

import java.awt.*;

public class DirtBlock extends Block {

    public DirtBlock() {
        super("dirt");
    }

    @Override
    public Block newInstance() {
        return new DirtBlock();
    }

    @Override
    public void render(WrappedGraphics graphics) {
        graphics.drawRect(getX() * 50, getY() * 50, GlobalProperties.BLOCK_SIZE, GlobalProperties.BLOCK_SIZE, Color.orange.darker().darker());
    }

    @Override
    public TwoBox getBoundingBox() {
        return new TwoBox(getX() * 50, getY() * 50, GlobalProperties.BLOCK_SIZE, GlobalProperties.BLOCK_SIZE);
    }
}
