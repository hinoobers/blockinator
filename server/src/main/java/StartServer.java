import org.hinoob.blockinator.BServer;

public class StartServer {

    public static void main(String[] args) {
        BServer.setInstance(new BServer());
        BServer.getInstance().start();
    }
}
