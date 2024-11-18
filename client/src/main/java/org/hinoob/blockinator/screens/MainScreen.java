package org.hinoob.blockinator.screens;

import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.GlobalProperties;
import org.hinoob.blockinator.PacketIds;
import org.hinoob.blockinator.entity.SelfPlayer;
import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.entity.Player;
import org.hinoob.blockinator.world.World;
import org.hinoob.blockinator.world.WorldManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainScreen extends Screen {
    public MainScreen() {
        super(Color.white.getRGB());

        Blockinator.getInstance().player = new SelfPlayer(150, (12-10)*50);
        this.player = Blockinator.getInstance().player;
    }

    private WorldManager worldManager;

    @Override
    public void initScreen() {
        this.worldManager = Blockinator.getInstance().worldManager;
        worldManager.currentWorld.getEntities().add(player);
        player.setWorld(worldManager.currentWorld);
        player.setSection(0);
        player.teleport(worldManager.currentWorld.getSpawn().getX(), worldManager.currentWorld.getSpawn().getY());

        Blockinator.getInstance().getNetwork().addNetworkHandler((reader) -> {
            int id = reader.readInt();
            if(id == PacketIds.SERVER_TO_CLIENT.PLAYERS) {
                worldManager.currentWorld.getEntities().removeIf(entity -> !(entity instanceof SelfPlayer));
                int playerCount = reader.readInt();
                for(int i = 0; i < playerCount; i++) {
                    int entityId = reader.readInt();
                    Player s = new Player(reader.readInt(), reader.readInt());
                    s.entityId = entityId;
                    s.setSection(reader.readInt());
                    if(s.getSection() != player.getSection()) continue; // TODO: SHOULD BE REMOVED FROM FUTURE
                    worldManager.currentWorld.getEntities().add(s);
                }
            }
        });
    }

    @Override
    public void handleKey(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_A)
            if(player != null) player.move(-GlobalProperties.BLOCK_SIZE, 0);

        if(event.getKeyCode() == KeyEvent.VK_D)
            if(player != null) player.move(GlobalProperties.BLOCK_SIZE, 0);

        if(event.getKeyCode() == KeyEvent.VK_W)
            if(player != null) player.move(0, -GlobalProperties.BLOCK_SIZE*3);

        if(event.getKeyCode() == KeyEvent.VK_S)
            if(player != null) player.move(0, GlobalProperties.BLOCK_SIZE);
    }

    @Override
    public void render(WrappedGraphics graphics) {
        super.render(graphics);

        if(worldManager.currentWorld != null) {
            worldManager.currentWorld.getBlocks(player.getSection()).forEach(block -> {
                if(block instanceof Renderer renderer) {
                    renderer.render(graphics);
                }
            });

            worldManager.currentWorld.getEntities().forEach(entity -> {
                if(entity instanceof Renderer renderer) {
                    renderer.render(graphics);
                }
            });
        }
    }

    @Override
    public void preRender(WrappedGraphics graphics) {
        super.preRender(graphics);

        if(worldManager.currentWorld != null) {
            worldManager.currentWorld.getBlocks(player.getSection()).forEach(block -> {
                if(block instanceof Renderer renderer) {
                    renderer.preRender(graphics);
                }
            });

            worldManager.currentWorld.getEntities().forEach(entity -> {
                if(entity instanceof Renderer renderer) {
                    renderer.preRender(graphics);
                }
            });
        }
    }
}
