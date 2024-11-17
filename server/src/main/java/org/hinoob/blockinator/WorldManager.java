package org.hinoob.blockinator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private final List<World> worlds = new ArrayList<>();

    public World getByName(String name) {
        for(World world : worlds) {
            if(world.getName().equals(name)) {
                return world;
            }
        }

        if(BServer.getInstance().getDatabaseManager().getWorld(name) != null) {
            World world = new World(name, BServer.getInstance().getDatabaseManager().getWorld(name));
            worlds.add(world);
            return world;
        }

        return null;
    }
}
