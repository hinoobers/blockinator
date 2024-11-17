package org.hinoob.loom;

import org.hinoob.loom.exception.LoomServerStartException;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoomServer {

    private int port;

    private ServerSocket serverSocket;
    private ServerListener listener;
    private boolean running = false;
    private int idCounter = 403;
    private ExecutorService clientHandler;
    private Map<Integer, Socket> clients = new HashMap<>();

    // ===========
    // Idea: Each client gets its own unique ID on connect, that's the pre handshake
    // ===========

    public LoomServer(int port, ServerListener listener) {
        this.port = port;
        this.listener = listener;
        this.clientHandler = Executors.newCachedThreadPool();
    }

    public void start() throws LoomServerStartException {
        try {
            serverSocket = new ServerSocket(port);
            listener.serverStarted();
            running = true;

            Thread thread = new Thread(this::acceptClients);
            thread.start();
        } catch (Exception e) {
            throw new LoomServerStartException(e.getMessage());
        }
    }

    private void acceptClients() {
        try {
            while(running) {
                Socket client = serverSocket.accept();
                int id = idCounter++;
                clients.put(id, client);
                sendMessage(client, id);
                listener.clientConnected(id);

                clientHandler.submit(() -> handleClient(client, id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket, int clientId) {
        try {
            InputStream inputStream = socket.getInputStream();
            while(running) {
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                if(bytesRead == -1) {
                    listener.clientDisconnected(clientId);
                    clients.remove(clientId);
                    break;
                }

                byte[] msg = new byte[bytesRead];
                System.arraycopy(buffer, 0, msg, 0, bytesRead);
                listener.clientMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket socket, int clientId) {
        try {
            byte[] bytes = new ByteWriter().writeInt(clientId).getBytes();
            socket.getOutputStream().write(bytes);
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(int id, byte[] bytes) {
        if(clients.containsKey(id)) {
            try {
                clients.get(id).getOutputStream().write(bytes);
                clients.get(id).getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            listener.error("Tried to send a message to a client that doesn't exist (id=" + id + ")");
        }
    }

    public void sendToAll(byte[] bytes) {
        clients.forEach((id, socket) -> {
            sendToClient(id, bytes);
        });
    }



    public void stop() {
        try {
            serverSocket.close();
            listener.serverClosed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ServerListener {
        void clientConnected(int id);
        void clientDisconnected(int id);

        void clientMessage(byte[] bytes);
        void serverClosed();
        void error(String message);
        void serverStarted();
    }
}
