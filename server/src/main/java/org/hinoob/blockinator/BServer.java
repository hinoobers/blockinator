package org.hinoob.blockinator;

import org.hinoob.blockinator.database.DatabaseManager;
import org.hinoob.loom.ByteReader;
import org.hinoob.loom.ByteWriter;
import org.hinoob.loom.LoomServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private WorldManager worldManager = new WorldManager();

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

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
                if(user.authenticated) {
                    String t = reader.readString();
                    System.out.println("User is authenticated, checking token, got=" + t);
                    if(sessionServer.getAccountId(t) != user.accountId) {
                        server.sendToClient(clientId, new ByteWriter()
                                .writeInt(PacketIds.SERVER_TO_CLIENT.AUTH_RESPONSE)
                                .writeString("Invalid token")
                                .getBytes());
                        return;
                    }
                }
                int id = reader.readInt();
                if(id == 12) {
                    System.out.println("Received packet: " + reader.readString());
                    return;
                }

                if(id == PacketIds.CLIENT_TO_SERVER.AUTH) {
                    String username = reader.readString();
                    String password = reader.readString();
                    if (username == null || password == null) {
                        server.sendToAll(new ByteWriter()
                                .writeInt(PacketIds.SERVER_TO_CLIENT.AUTH_RESPONSE)
                                .writeString("Received invalid auth packet")
                                .getBytes());
                        return;
                    }

                    if (databaseManager.accountExists(username, password)) {
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
                } else if(id == PacketIds.CLIENT_TO_SERVER.POSITION) {
                    user.x = reader.readInt();
                    user.y = reader.readInt();
                    user.section = reader.readInt();
                } else if(id == PacketIds.CLIENT_TO_SERVER.STATUS) {
                    server.sendToClient(clientId, new ByteWriter()
                            .writeInt(PacketIds.SERVER_TO_CLIENT.STATUS_RESPONSE)
                            .writeInt(users.size())
                            .getBytes());
                } else if(id == PacketIds.CLIENT_TO_SERVER.JOIN_WORLD) {
                    System.out.println("Received fetch world packet");
                    String worldName = reader.readString();
                    if(worldName == null || worldName.isEmpty()) return;
                    System.out.println("Fetching world: " + worldName);

                    World w = worldManager.getByName(worldName);
                    w.players.add(user);
                    byte[] compressed = w.encode();
                    System.out.println("Compressed world: " + compressed.length);

                    ByteWriter writer = new ByteWriter()
                            .writeInt(PacketIds.SERVER_TO_CLIENT.WORLD_RESPONSE)
                            .writeString(worldName);

                    if(user.entityId == -1) {
                        user.entityId = new Random().nextInt(32767);
                    }
                    if(w.players.stream().anyMatch(p -> user.accountId != p.accountId)) {
                        System.out.println("Sending players " + w.players.size());
                        int without = (int) w.players.stream().filter(p -> user.accountId != p.accountId).count();
                        writer.writeInt(without);
                        for(BlockinatorUser u : w.players.stream().filter(p -> user.accountId != p.accountId).toList()) {
                            writer.writeInt(u.entityId);
                            writer.writeInt(u.x);
                            writer.writeInt(u.y);
                            writer.writeInt(u.section);
                        }
                    } else {
                        writer.writeInt(0);
                    }

                    writer.writeVector(w.getSpawnPoint());
                    writer.writeBytes(compressed);

                    server.sendToClient(clientId, writer.getBytes());
                    user.world = w;
                } else if(id == PacketIds.CLIENT_TO_SERVER.DESTROY_BLOCK) {
                    int section = reader.readInt();
                    int x = reader.readInt();
                    int y = reader.readInt();
                    user.world.removeBlock(section, user.world.getBlockAt(section, x, y));
                    server.sendToAll(new ByteWriter()
                            .writeInt(PacketIds.SERVER_TO_CLIENT.UPDATE_BLOCK)
                            .writeString(user.world.getName())
                            .writeInt(section)
                            .writeInt(x)
                            .writeInt(y)
                            .writeString("air")
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

            new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(50L);

                        for(BlockinatorUser user : users) {
                            if(user.world != null) {
                                int without = (int) user.world.players.stream().filter(p -> user.accountId != p.accountId).count();

                                ByteWriter writer = new ByteWriter()
                                        .writeInt(PacketIds.SERVER_TO_CLIENT.PLAYERS)
                                        .writeInt(without);

                                for(BlockinatorUser u : user.world.players.stream().filter(p -> user.accountId != p.accountId).toList()) {
                                    writer.writeInt(u.entityId);
                                    writer.writeInt(u.x);
                                    writer.writeInt(u.y);
                                    writer.writeInt(u.section);
                                }

                                server.sendToClient(user.getLoomId(), writer.getBytes());
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
