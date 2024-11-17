package org.hinoob.blockinator.screens;

import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.BlockinatorNetwork;
import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.player.Player;
import org.hinoob.blockinator.world.World;

import java.awt.*;

public class MainScreen extends Screen {
    public MainScreen() {
        super(Color.white.getRGB());

        Blockinator.getInstance().player = new Player(150, (12-10)*50);
    }

    private World world;

    @Override
    public void initScreen() {
        this.world = Blockinator.getInstance().currentWorld;
        Blockinator.getInstance().player.teleport(world.getSpawn().getX(), world.getSpawn().getY());
    }

    @Override
    public void render(WrappedGraphics graphics) {
        super.render(graphics);

        if(world != null) {
            world.getBlocks().forEach(block -> {
                if(block instanceof Renderer renderer) {
                    renderer.render(graphics);
                }
            });

            if(Blockinator.getInstance().player != null) {
                Blockinator.getInstance().player.render(graphics);
            }
        }
    }
}
