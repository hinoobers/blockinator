package org.hinoob.blockinator.block;

import org.hinoob.blockinator.block.types.DirtBlock;
import org.hinoob.blockinator.block.types.GrassBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockManager {

    private final List<Block> registeredBlocks = new ArrayList<>();

    public void register() {
        registeredBlocks.add(new DirtBlock());
        registeredBlocks.add(new GrassBlock());
    }

    public Block getById(String id) {
        Block b = registeredBlocks.stream().filter(block -> block.getId().equals(id)).findFirst().orElse(null);
        if(b == null) {
            throw new IllegalArgumentException("Block with id " + id + " not found");
        }

        return b.newInstance();
    }
}
