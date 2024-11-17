package org.hinoob.blockinator.block;

import com.google.gson.JsonObject;
import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.WrappedGraphics;

public class Block implements Renderer {

    private final String id;

    private int x, y;

    public Block(String id) {
        this.id = id;
    }

    public Block(JsonObject o) {
        this.id = o.get("id").getAsString();
        this.x = o.get("x").getAsInt();
        this.y = o.get("y").getAsInt();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
