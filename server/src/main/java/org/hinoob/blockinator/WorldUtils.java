package org.hinoob.blockinator;

import com.google.gson.JsonArray;

public class WorldUtils {

    public static String shorten(JsonArray array) {
        // each block is 50x50
        // screen size is 800x600
        // which means, we only send 16x12 blocks

        return array.toString();
    }
}
