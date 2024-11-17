package org.hinoob.blockinator;

import org.hinoob.loom.ByteReader;

public interface NetworkHandler {

    void handle(ByteReader reader);
}
