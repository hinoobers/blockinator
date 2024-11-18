package org.hinoob.blockinator;

public class BlockinatorUser {

    private int loomId;
    public int accountId;
    public boolean authenticated = false;
    public World world;

    public int x, y, section;
    public int entityId = -1;

    public BlockinatorUser(int loomId) {
        this.loomId = loomId;
    }

    public int getLoomId() {
        return loomId;
    }
}
