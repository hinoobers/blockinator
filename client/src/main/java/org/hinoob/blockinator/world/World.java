package org.hinoob.blockinator.world;

import com.google.gson.JsonArray;
import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.Vector;
import org.hinoob.blockinator.block.Block;

import java.util.ArrayList;
import java.util.List;

public class World {

    private String name;
    private List<Block> blocks = new ArrayList<>();
    private Vector spawn;

    public World(String name, JsonArray array, Vector spawn) {
        this.name = name;

        for (int i = 0; i < array.size(); i++) {
            Block block = Blockinator.getInstance().getBlockManager().getById(array.get(i).getAsJsonObject().get("type").getAsString());
            block.load(array.get(i).getAsJsonObject());
            blocks.add(block);
        }

        this.spawn = spawn;
    }

    public Vector getSpawn() {
        return spawn;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
