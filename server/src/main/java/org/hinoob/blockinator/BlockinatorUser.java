package org.hinoob.blockinator;

public class BlockinatorUser {

    private int loomId;
    public int accountId;
    public boolean authenticated = false;

    public BlockinatorUser(int loomId) {
        this.loomId = loomId;
    }

    public int getLoomId() {
        return loomId;
    }
}
