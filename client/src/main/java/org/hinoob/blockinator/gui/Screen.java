package org.hinoob.blockinator.gui;

import org.hinoob.blockinator.entity.Player;
import org.hinoob.blockinator.gui.types.Element;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Screen implements Renderer{

    protected int width, height;
    protected Player player;
    private int backgroundColor;
    private final List<Renderer> elements = new ArrayList<>();

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

    public void add(Renderer renderer) {
        elements.add(renderer);
    }

    public List<Element> getElements() {
        return elements.stream().filter(element -> element instanceof Element).map(d -> (Element)d).toList();
    }

    public abstract void handleKey(KeyEvent event);

    @Override
    public void render(WrappedGraphics graphics) {
        graphics.drawRect(0, 0, 800, 600, new Color(backgroundColor));
        for(Renderer element : elements) {
            element.render(graphics);
        }
    }
}
