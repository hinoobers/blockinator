import org.hinoob.loom.LoomClient;
import org.hinoob.loom.LoomServer;
import org.hinoob.loom.exception.LoomServerStartException;

public class TestClass {

    public static void main(String[] args) {
        LoomServer server = new LoomServer(4040, new LoomServer.ServerListener() {
            @Override
            public void clientConnected(int id) {

            }

            @Override
            public void clientDisconnected(int id) {

            }

            @Override
            public void clientMessage(byte[] bytes) {
                System.out.println("Received message from client: " + new String(bytes));
            }

            @Override
            public void serverClosed() {

            }

            @Override
            public void serverStarted() {
                System.out.println("Server started on port 4040");
            }

            @Override
            public void error(String message) {

            }
        });
        try {
            server.start();
        } catch(LoomServerStartException e){
            e.printStackTrace();
        }

        LoomClient client = new LoomClient("localhost", 4040, new LoomClient.ClientListener() {
            @Override
            public void onConnect(int id) {
                System.out.println("Connected to server with client id: " + id);
            }

            @Override
            public void onMessage(byte[] bytes) {
                System.out.println("Received message from server: " + new String(bytes));
            }
        });

        client.sendToServer("Hello, server!".getBytes());
        server.sendToAll("Hello, clients!".getBytes());
    }
}
