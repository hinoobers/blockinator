package org.hinoob.blockinator;

import org.hinoob.blockinator.database.DatabaseManager;
import org.hinoob.loom.ByteReader;
import org.hinoob.loom.ByteWriter;
import org.hinoob.loom.LoomServer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BServer {

    public static BServer instance;

    public static BServer getInstance() {
        return instance;
    }

    public static void setInstance(BServer instance) {
        BServer.instance = instance;
    }

    // ===================================================

    private LoomServer server;
    private List<BlockinatorUser> users = new ArrayList<>();
    private DatabaseManager databaseManager = new DatabaseManager();
    private SessionServer sessionServer = new SessionServer();

    public void start() {
        databaseManager.load();

        server = new LoomServer(3636, new LoomServer.ServerListener() {
            @Override
            public void clientConnected(int id) {
                users.add(new BlockinatorUser(id));
            }

            @Override
            public void clientDisconnected(int id) {
                users.removeIf(user -> user.getLoomId() == id);
            }

            @Override
            public void clientMessage(int clientId, byte[] bytes) {
                ByteReader reader = new ByteReader(bytes);
                BlockinatorUser user = users.stream().filter(u -> u.getLoomId() == clientId).findFirst().orElse(null);
                if(user.authenticated && sessionServer.getAccountId(reader.readString()) != user.accountId) {
                    return;
                }
                int id = reader.readInt();

                if(id == PacketIds.CLIENT_TO_SERVER.AUTH) {
                    String username = reader.readString();
                    String password = reader.readString();
                    if(username == null || password == null) {
                        server.sendToAll(new ByteWriter()
                                .writeInt(PacketIds.SERVER_TO_CLIENT.AUTH_RESPONSE)
                                .writeString("Received invalid auth packet")
                                .getBytes());
                        return;
                    }

                    if(databaseManager.accountExists(username, password)) {
                        int accountId = databaseManager.getAccountId(username, password);

                        server.sendToClient(clientId, new ByteWriter()
                                .writeInt(PacketIds.SERVER_TO_CLIENT.AUTH_RESPONSE)
                                .writeString("Successfully authenticated")
                                .writeString(sessionServer.generate(accountId))
                                .getBytes());
                        user.accountId = accountId;
                        user.authenticated = true;
                    } else {
                        server.sendToClient(clientId, new ByteWriter()
                                .writeInt(PacketIds.SERVER_TO_CLIENT.AUTH_RESPONSE)
                                .writeString("Invalid credentials")
                                .getBytes());
                    }
                } else if(id == PacketIds.CLIENT_TO_SERVER.FETCH_WORLD) {
                    String worldName = reader.readString();
                    if(worldName == null || worldName.isEmpty()) return;
                    System.out.println("Fetching world: " + worldName);

                    server.sendToClient(clientId, new ByteWriter()
                            .writeInt(PacketIds.SERVER_TO_CLIENT.WORLD_RESPONSE)
                            .writeString(worldName)
                            .writeString(WorldUtils.shorten(databaseManager.getWorld(worldName)))
                            .getBytes());
                }
            }

            @Override
            public void serverClosed() {

            }

            @Override
            public void error(String message) {

            }

            @Override
            public void serverStarted() {
                System.out.println("Server started on port 3636!");
            }
        });
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
