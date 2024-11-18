package org.hinoob.blockinator.gui;

import java.awt.*;

public interface Renderer {

    default void render(WrappedGraphics graphics) {

    }

    default void preRender(WrappedGraphics graphics) {

    }
}
