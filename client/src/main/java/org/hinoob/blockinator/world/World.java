package org.hinoob.blockinator.world;

import com.google.gson.JsonArray;
import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.GlobalProperties;
import org.hinoob.blockinator.Vector;
import org.hinoob.blockinator.block.Block;
import org.hinoob.blockinator.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {

    private String name;
    private Map<Integer, Section> sections = new HashMap<>();
    private Vector spawn;

    private List<Entity> entities = new ArrayList<>();

    public World(String name, JsonArray array, Vector spawn) {
        this.name = name;

        for (int i = 0; i < array.size(); i++) {
            Section section = new Section();
            int sectionID = array.get(i).getAsJsonObject().get("id").getAsInt();

            JsonArray blocks = array.get(i).getAsJsonObject().get("blocks").getAsJsonArray();
            for (int j = 0; j < blocks.size(); j++) {
                Block block = Blockinator.getInstance().getBlockManager().getById(blocks.get(j).getAsJsonObject().get("type").getAsString());
                block.load(blocks.get(j).getAsJsonObject());
                section.addBlock(block);
            }

            sections.put(sectionID, section);
        }

        this.spawn = spawn;
    }

    public String getName() {
        return name;
    }



    public Block getBlockAt(int section, int x, int y) {
        for(Block block : sections.get(section).getBlocks()) {
            if(block.getX() == x && block.getY() == y) {
                return block;
            }
        }

        return null;
    }

    public Vector getSpawn() {
        return spawn;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Block> getBlocks(int section) {
        return sections.get(section).getBlocks();
    }

    public class Section {
        private List<Block> blocks = new ArrayList<>();

        public void addBlock(Block block) {
            blocks.add(block);
        }

        public List<Block> getBlocks() {
            return blocks;
        }
    }
}
