package org.hinoob.blockinator.screens;

import org.hinoob.blockinator.Blockinator;
import org.hinoob.blockinator.PacketIds;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.types.types.InputBox;
import org.hinoob.blockinator.gui.types.types.Button;
import org.hinoob.blockinator.gui.types.types.Label;
import org.hinoob.loom.ByteWriter;

import java.awt.*;

public class LoginScreen extends Screen {

    public LoginScreen() {
        super(Color.black.getRGB());
    }

    @Override
    public void initScreen() {
        InputBox username = new InputBox(20, this.height / 2, this.width - 60, 40);
        InputBox password = new InputBox(20, this.height / 2 + 40, this.width - 60, 40);
        Label status = new Label(20, this.height /2 - 50, this.width - 60, 20);
        username.setPlaceholder("Username");
        password.setPlaceholder("Password");

        Button login = new Button(20, this.height / 2 + 110, this.width - 60, 40, "Login", () -> {
            Blockinator.getInstance().getNetwork().send(new ByteWriter()
                    .writeInt(PacketIds.CLIENT_TO_SERVER.AUTH)
                    .writeString(username.getValue())
                    .writeString(password.getValue())
                    .getBytes());
        });

        addElement(username);
        addElement(password);
        addElement(login);
        addElement(status);

        Blockinator.getInstance().getNetwork().addNetworkHandler((reader) -> {
            int packetId = reader.readInt();
            if(packetId == PacketIds.SERVER_TO_CLIENT.AUTH_RESPONSE) {
                status.setText(reader.readString());
                if (reader.available()) {
                    String token = reader.readString();
                    Blockinator.getInstance().getNetwork().setToken(token);
                    System.out.println("Received token: " + token);

                    Blockinator.getInstance().showScreen(new WorldScreen());
                }
            }
        });
    }
}
