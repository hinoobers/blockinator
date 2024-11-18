package org.hinoob.blockinator.entity;

import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.PacketIds;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.loom.ByteWriter;

public class SelfPlayer extends Player{

    public SelfPlayer(int x, int y) {
        super(x, y);
    }


    @Override
    public void render(WrappedGraphics graphics) {
        super.render(graphics);

        Blockinator.getInstance().getNetwork().send(new ByteWriter()
                .writeInt(PacketIds.CLIENT_TO_SERVER.POSITION)
                .writeInt(this.x)
                .writeInt(this.y)
                .writeInt(this.section)
                .getBytes());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
