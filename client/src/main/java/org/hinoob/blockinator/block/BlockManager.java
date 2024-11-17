package org.hinoob.blockinator.block;

import org.hinoob.blockinator.block.types.DirtBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockManager {

    private final List<Block> registeredBlocks = new ArrayList<>();

    public void register() {
        registeredBlocks.add(new DirtBlock());
    }
}
