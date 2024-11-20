package org.hinoob.blockinator.screens;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.PacketIds;
import org.hinoob.blockinator.CompressUtils;
import org.hinoob.blockinator.Vector;
import org.hinoob.blockinator.entity.Player;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.gui.types.types.Button;
import org.hinoob.blockinator.gui.types.types.InputBox;
import org.hinoob.blockinator.world.World;
import org.hinoob.blockinator.world.WorldManager;
import org.hinoob.loom.ByteWriter;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WorldScreen extends Screen {


    public WorldScreen() {
        super(Color.black.getRGB());
    }


    @Override
    public void initScreen() {
        InputBox worldName = new InputBox(20, this.height / 2, this.width - 60, 40);
        worldName.setPlaceholder("World Name");
        Button createWorld = new Button(20, this.height / 2 + 50, this.width - 60, 40, "Create World", () -> {
            Blockinator.getInstance().getNetwork().send(new ByteWriter()
                    .writeInt(PacketIds.CLIENT_TO_SERVER.JOIN_WORLD)
                    .writeString(worldName.getValue())
                    .getBytes());
        });

        addElement(worldName);
        addElement(createWorld);

        Blockinator.getInstance().getNetwork().addNetworkHandler((reader) -> {
            int id = reader.readInt();
            if(id == PacketIds.SERVER_TO_CLIENT.WORLD_RESPONSE) {
                Blockinator.getInstance().worldManager.addOrUpdate(reader, new WorldManager.Callback() {
                    @Override
                    public void onWorldReceived(World world) {
                        Blockinator.getInstance().worldManager.currentWorld = world;
                        Blockinator.getInstance().showScreen(new MainScreen());
                    }
                });
            }
        });
    }

    @Override
    public void handleMouseClick(int x, int y, int button) {

    }

    @Override
    public void handleKey(KeyEvent event) {

    }

    @Override
    public void render(WrappedGraphics graphics) {
        super.render(graphics);
    }
}
