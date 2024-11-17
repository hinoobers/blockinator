package org.hinoob.blockinator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Block> blocks= new ArrayList<>();
    private String name;
    private Vector spawnPoint = new Vector(GlobalProperties.WORLD_WIDTH / 2, 5);

    public World(String name, JsonArray blocks) {
        System.out.println(blocks.toString());
        for(int i = 0; i < blocks.size(); i++) {
            Block block = new Block(blocks.get(i).getAsJsonObject().get("x").getAsInt(), blocks.get(i).getAsJsonObject().get("y").getAsInt(), blocks.get(i).getAsJsonObject().get("type").getAsString());

            this.blocks.add(block);
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public byte[] encode() {
        return CompressUtils.compress(new Gson().toJson(blocks).getBytes());
    }

    public Vector getSpawnPoint() {
        return spawnPoint;
    }

    public void display() {
        System.out.println("Blocks: " + blocks.size());
        System.out.println("====================================");
        for(int y = GlobalProperties.WORLD_HEIGHT; y > 0; y--) {
            StringBuilder line = new StringBuilder();

            for(int x = 0; x < GlobalProperties.WORLD_WIDTH; x++) {
                boolean found = false;
                for(Block block : this.blocks) {
                    if(block.getX() == x && block.getY() == y) {
                        line.append(block.type.charAt(0));
                        found = true;
                        break;
                    }
                }

                if(!found) {
                    line.append("X");
                }

            }

            System.out.println(line.toString());
        }
        System.out.println("====================================");
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
}
