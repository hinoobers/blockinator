package org.hinoob.blockinator.world;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.CompressUtils;
import org.hinoob.blockinator.Vector;
import org.hinoob.blockinator.entity.Entity;
import org.hinoob.blockinator.entity.Player;
import org.hinoob.loom.ByteReader;

import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private final List<World> worlds = new ArrayList<>();
    public World currentWorld;

    public World getWorld(String name) {
        for(World world : worlds) {
            if(world.getName().equals(name)) {
                return world;
            }
        }

        return null;
    }

    public void addOrUpdate(ByteReader reader, Callback callback) {
        String name = reader.readString();
        int playerCount = reader.readInt();
        System.out.println("Received player count: " + playerCount);
        List<Entity> entities = new ArrayList<>();
        for(int i = 0; i < playerCount; i++) {
            int entityId = reader.readInt();
            Player s = new Player(reader.readInt(), reader.readInt());
            s.entityId = entityId;
            s.setSection(reader.readInt());
            entities.add(s);
        }

        Vector spawn = reader.readVector();
        byte[] blockData = CompressUtils.decompress(reader.readBytes());

        World w = new World(name, new Gson().fromJson(new String(blockData), JsonArray.class), spawn);
        w.getEntities().addAll(entities);
        World finalW = w;
        w.getEntities().forEach(s -> s.setWorld(finalW));
        worlds.add(w);
        callback.onWorldReceived(w);
    }

    public interface Callback {
        void onWorldReceived(World world);
    }
}
