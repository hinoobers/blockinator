import org.hinoob.blockinator.Blockinator;

public class Start {

    public static void main(String[] args) {
        Blockinator.setInstance(new Blockinator());
        Blockinator.getInstance().start();
    }
}
