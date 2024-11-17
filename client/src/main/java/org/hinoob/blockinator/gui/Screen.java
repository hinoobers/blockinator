package org.hinoob.blockinator.gui;

import org.hinoob.blockinator.gui.types.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Screen implements Renderer{

    protected int width, height;
    private int backgroundColor;
    private final List<Element> elements = new ArrayList<>();

    public Screen(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void init(ScreenInstance instance) {
        this.width = instance.getWidth();
        this.height = instance.getHeight();
        initScreen();
    }

    public abstract void initScreen();

    public void addElement(Element element) {
        elements.add(element);
    }

    public Collection<Element> getElements() {
        return elements;
    }

    @Override
    public void render(WrappedGraphics graphics) {
        graphics.drawRect(0, 0, 800, 600, new Color(backgroundColor));
        for(Element element : elements) {
            element.render(graphics);
        }
    }
}
