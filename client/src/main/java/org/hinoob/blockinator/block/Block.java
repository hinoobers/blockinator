package org.hinoob.blockinator.block;

import com.google.gson.JsonObject;
import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.WrappedGraphics;

public abstract class Block implements Renderer {

    private final String id;

    private int x, y;

    public Block(String id) {
        this.id = id;
    }

    public Block(JsonObject o) {
        System.out.println(o);
        this.id = o.get("type").getAsString();
        this.x = o.get("x").getAsInt();
        this.y = o.get("y").getAsInt();
    }

    public void load(JsonObject o) {
        this.x = o.get("x").getAsInt();
        this.y = o.get("y").getAsInt();
    }

    public abstract Block newInstance();

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
