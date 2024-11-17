package org.hinoob.blockinator.screens;

import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.gui.types.InputBox;
import org.hinoob.blockinator.gui.types.types.Button;

import java.awt.*;

public class LoginScreen extends Screen {

    public LoginScreen() {
        super(Color.black.getRGB());
    }

    @Override
    public void initScreen() {
        InputBox username = new InputBox(20, this.height / 2, this.width - 60, 40);
        InputBox password = new InputBox(20, this.height / 2 + 40, this.width - 60, 40);
        username.setPlaceholder("Username");
        password.setPlaceholder("Password");

        Button login = new Button(20, this.height / 2 + 110, this.width - 60, 40, () -> {
            System.out.println("Login button clicked!");
        });

        addElement(username);
        addElement(password);
        addElement(login);
    }
}
