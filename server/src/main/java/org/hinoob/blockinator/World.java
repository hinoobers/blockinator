package org.hinoob.blockinator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {

    private Map<Integer, Section> sections = new HashMap<>();
    private String name;
    private Vector spawnPoint = new Vector(GlobalProperties.WORLD_WIDTH / 2, 5);
    public final List<BlockinatorUser> players = new ArrayList<>();

    public World(String name, JsonArray sections) {
        for(int i = 0; i < sections.size(); i++) {
            JsonObject section = sections.get(i).getAsJsonObject();

            Section s = new Section();
            JsonArray blocks = section.getAsJsonArray("blocks");
            for(int j = 0; j < blocks.size(); j++) {
                JsonObject block = blocks.get(j).getAsJsonObject();
                s.addBlock(new Block(block.get("x").getAsInt(), block.get("y").getAsInt(), block.get("type").getAsString()));
            }


            this.sections.put(section.get("id").getAsInt(), s);
        }

        this.name = name;
    }

    public World(String name) {
        this.name = name;
    }

    public String toJSON() {
        JsonArray sections = new JsonArray();
        for(Map.Entry<Integer, Section> entry : this.sections.entrySet()) {
            JsonObject section = new JsonObject();
            section.addProperty("id", entry.getKey());
            JsonArray blocks = new JsonArray();
            for(Block block : entry.getValue().getBlocks()) {
                JsonObject b = new JsonObject();
                b.addProperty("x", block.getX());
                b.addProperty("y", block.getY());
                b.addProperty("type", block.type);
                blocks.add(b);
            }
            section.add("blocks", blocks);
            sections.add(section);
        }
        return sections.toString();
    }

    public void generateSection(int id) {
        Section section = new Section();
        int airHeight = 5;
        int grassHeight = GlobalProperties.WORLD_HEIGHT / 2;
        for(int blocksX = 0; blocksX < GlobalProperties.WORLD_WIDTH; blocksX++) {
            for (int blocksY = GlobalProperties.WORLD_HEIGHT; blocksY > 0; blocksY--) {
                if(blocksY < airHeight) {
                    continue;
                }
                Block block = new Block(blocksX, blocksY, blocksY <= grassHeight ? "grass" : "dirt");
                section.addBlock(block);
            }
        }
        sections.put(id, section);
    }

    public String getName() {
        return name;
    }

    public byte[] encode() {
        return CompressUtils.compress(toJSON().getBytes());
    }

    public Vector getSpawnPoint() {
        return spawnPoint;
    }

    public class Block {
        private int x, y;
        private String type;

        public Block(int x, int y, String type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
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
