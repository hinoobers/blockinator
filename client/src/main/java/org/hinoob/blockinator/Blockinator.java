package org.hinoob.blockinator;

import org.hinoob.blockinator.gui.Renderer;
import org.hinoob.blockinator.gui.Screen;
import org.hinoob.blockinator.gui.ScreenInstance;
import org.hinoob.blockinator.gui.WrappedGraphics;
import org.hinoob.blockinator.screens.LoginScreen;

import java.awt.*;

public class Blockinator {

    public static Blockinator instance;

    public static Blockinator getInstance() {
        return instance;
    }

    public static void setInstance(Blockinator instance) {
        Blockinator.instance = instance;
    }

    // ============================

    private ScreenInstance mainScreen;


    public void start() {
        ScreenInstance screen = mainScreen = new ScreenInstance("Blockinator", 800, 600, false);
        screen.create();

        screen.addRenderer(new Renderer() {
            @Override
            public void render(WrappedGraphics graphics) {
                graphics.drawRect(100, 100, 100, 100, Color.RED);
            }
        });

        showScreen(new LoginScreen());
        screen.display();
    }

    public void showScreen(Screen screen) {
        screen.init(mainScreen);
        mainScreen.addRenderer(screen);
    }
}