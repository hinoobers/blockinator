package org.hinoob.loom;

import java.io.InputStream;
import java.net.Socket;

public class LoomClient {

    private Socket socket;
    private ClientListener listener;

    public LoomClient(String host, int port, ClientListener listener) {
        this.listener = listener;
        try {
            socket = new Socket(host, port);

            Thread thread = new Thread(this::listen);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        try {
            byte[] buffer = new byte[Integer.MAX_VALUE - 5]; // TODO: Might change later for better performance
            int bytesRead;
            boolean gotId = false;

            while((bytesRead = socket.getInputStream().read(buffer)) != -1) {
                if(!gotId) {
                    byte[] msg = new byte[bytesRead];
                    System.arraycopy(buffer, 0, msg, 0, bytesRead);

                    ByteReader reader = new ByteReader(msg);
                    int id = reader.readInt();
                    listener.onConnect(id);

                    gotId = true;
                } else {
                    byte[] msg = new byte[bytesRead];
                    System.arraycopy(buffer, 0, msg, 0, bytesRead);
                    listener.onMessage(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(byte[] bytes) {
        try {
            socket.getOutputStream().write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ClientListener {
        void onConnect(int id);
        void onMessage(byte[] bytes);
    }
}
