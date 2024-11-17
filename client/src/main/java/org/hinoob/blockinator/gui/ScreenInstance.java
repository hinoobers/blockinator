package org.hinoob.blockinator.gui;

import org.hinoob.blockinator.gui.types.Element;
import org.hinoob.blockinator.gui.types.Focusable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenInstance implements ActionListener {

    private String title;
    private int width, height;
    private boolean resizable;

    private Timer timer = new Timer(1000 / 60, this);

    private JFrame frame;
    private JPanel panel;
    private List<Renderer> renderers = new ArrayList<>();

    public ScreenInstance(String title, int width, int height, boolean resizable) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = resizable;
    }

    public JFrame create() {
        this.frame = new JFrame(title);
        this.panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for(Renderer renderer : ScreenInstance.this.renderers) {
                    renderer.render(new WrappedGraphics(g));
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for(Renderer renderer : ScreenInstance.this.renderers) {
                    if(renderer instanceof Screen screen) {
                        for(Element element : screen.getElements()) {
                            element.handleClick(e.getX(), e.getY(), e.getButton());
                        }
                    }
                }
            }
        });
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                for(Renderer renderer : ScreenInstance.this.renderers) {
                    if(renderer instanceof Screen screen) {
                        for(Element element : screen.getElements()) {
                            if(element instanceof Focusable) {
                                element.handleKeyTyped(e);
                            }
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        panel.setFocusable(true);
        panel.requestFocus();
        frame.add(panel);

        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(resizable);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setLocationRelativeTo(null);


        // ===
        this.timer.start();

        return frame;
    }


    public void addRenderer(Renderer renderer) {
        renderers.add(renderer);
    }

    public void display() {
        frame.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.repaint();
    }
}
