package org.hinoob.blockinator.world;

import com.google.gson.JsonArray;
import org.hinoob.blockinator.block.Block;

import java.util.ArrayList;
import java.util.List;

public class World {

    private String name;
    private List<Block> blocks = new ArrayList<>();

    public World(String name, JsonArray array) {
        this.name = name;

        for (int i = 0; i < array.size(); i++) {
            Block block = new Block(array.get(i).getAsJsonObject());
            blocks.add(block);
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
