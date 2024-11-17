package org.hinoob.blockinator;

import org.hinoob.loom.ByteReader;
import org.hinoob.loom.ByteWriter;
import org.hinoob.loom.LoomClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockinatorNetwork implements LoomClient.ClientListener {

    private LoomClient client;
    private List<NetworkHandler> networkHandlers = new CopyOnWriteArrayList<>();
    private String token;

    public BlockinatorNetwork() {
        client = new LoomClient("localhost", 3636, this);
    }

    @Override
        public void onConnect(int id) {

    }

    @Override
    public void onMessage(byte[] bytes) {
        Iterator<NetworkHandler> iterator = networkHandlers.iterator();
        while(iterator.hasNext()) {
            NetworkHandler handler = iterator.next();
            handler.handle(new ByteReader(bytes));
        }
    }

    public void send(byte[] bytes) {
        if(token != null){

            ByteWriter writer = new ByteWriter();
            writer.writeString(token);
            writer.appendBytes(bytes);
            bytes = writer.getBytes();
        }
        client.sendToServer(bytes);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addNetworkHandler(NetworkHandler handler) {
        networkHandlers.add(handler);
    }
}
