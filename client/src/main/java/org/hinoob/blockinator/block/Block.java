package org.hinoob.blockinator.block;

import com.google.gson.JsonObject;
import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.util.TwoBox;

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
    public abstract TwoBox getBoundingBox();

    public String getId() {
        return id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
