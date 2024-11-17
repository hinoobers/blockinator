package org.hinoob.blockinator.screens;

import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.BlockinatorNetwork;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.world.World;

import java.awt.*;

public class MainScreen extends Screen {
    public MainScreen() {
        super(Color.white.getRGB());
    }

    private World world;

    @Override
    public void initScreen() {
        this.world = Blockinator.getInstance().currentWorld;
    }

    @Override
    public void render(WrappedGraphics graphics) {
        super.render(graphics);

        if(world != null) {
            world.getBlocks().forEach(block -> {
                graphics.drawRect(block.getX(), block.getY(), 50, 50, Color.BLACK);
            });
        }
    }
}
