package org.hinoob.blockinator.screens;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.NetworkHandler;
import org.hinoob.blockinator.PacketIds;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.gui.types.types.Button;
import org.hinoob.blockinator.gui.types.types.InputBox;
import org.hinoob.blockinator.gui.types.types.Label;
import org.hinoob.blockinator.world.World;
import org.hinoob.loom.ByteReader;
import org.hinoob.loom.ByteWriter;

import java.awt.*;

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
                    .writeInt(PacketIds.CLIENT_TO_SERVER.FETCH_WORLD)
                    .writeString(worldName.getValue())
                    .getBytes());
        });

        addElement(worldName);
        addElement(createWorld);

        Blockinator.getInstance().getNetwork().addNetworkHandler((reader) -> {
            int id = reader.readInt();
            if(id == PacketIds.SERVER_TO_CLIENT.WORLD_RESPONSE) {
                String name = reader.readString();
                String blockData = reader.readString();

                Blockinator.getInstance().currentWorld = new World(name, new Gson().fromJson(blockData, JsonArray.class));
                Blockinator.getInstance().showScreen(new MainScreen());
            }
        });
    }
    @Override
    public void render(WrappedGraphics graphics) {
        super.render(graphics);
    }
}
